/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.session;

import com.google.common.collect.MapMaker;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import vn.com.onesoft.bigfox.server.io.message.core.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.core.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.Tags;
import vn.com.onesoft.bigfox.server.io.message.core.annotations.Message;
import vn.com.onesoft.bigfox.server.io.message.cs.CSClientInfo;
import vn.com.onesoft.bigfox.server.io.message.objects.ClientInfo;
import vn.com.onesoft.bigfox.server.io.message.sc.SCInitSession;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH
 */
public class BFSessionManager {

    private final Map<Channel, IBFSession> mapChannelToSession;
    private final Map<String, IBFSession> mapSessionIdToSession;

    public final static int SESSION_TIMEOUT = 300; //300 s
    private static BFSessionManager _instance;

    public static BFSessionManager getInstance() {
        if (_instance == null) {
            _instance = new BFSessionManager();
            _instance.startControlThread();
        }
        return _instance;
    }

    public BFSessionManager() {
        this.mapSessionIdToSession = new MapMaker().makeMap();
        this.mapChannelToSession = new MapMaker().makeMap();
    }

    public IBFSession createSession(Channel channel, ClientInfo clientInfo) {
        IBFSession session = new BFSession();
        session.setChannel(channel);
        session.setClientInfo(clientInfo);
        session.setLastTimeReceive(System.currentTimeMillis());
        mapChannelToSession.put(channel, session);
        mapSessionIdToSession.put(clientInfo.sessionId, session);

        return session;
    }

    public IBFSession getSessionByChannel(Channel channel) {
        return mapChannelToSession.get(channel);
    }

    public IBFSession getSessionById(String id) {
        return mapSessionIdToSession.get(id);
    }

    public void removeSession(IBFSession session) {
        mapChannelToSession.remove(session.getChannel());
        mapSessionIdToSession.remove(session.getClientInfo().sessionId);
    }

    public boolean containsSession(IBFSession session) {
        if (mapChannelToSession.get(session.getChannel()) != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<IBFSession> getAllSessions() {
        return (List<IBFSession>) mapChannelToSession.values();
    }

    public void sendMessage(Channel channel, MessageOut mOut) {
        if (channel == null) {
            return;
        }

        try {

            Message m = mOut.getClass().getAnnotation(Message.class);
            mOut.setTag(m.tag());
            if (mOut.getTag() == Tags.SC_VALIDATION_CODE) {
                mOut.setMSequence(0);
                mOut.setSSequence(1);
            } else {
                IBFSession session = getSessionByChannel(channel);
                mOut.setMSequence(session.getMSequence());
                session.setSSequence(session.getSSequence() + 1);
                mOut.setSSequence(session.getSSequence());
            }
            byte[] data = mOut.toBytes();
            if (mOut.getTag() != Tags.SC_VALIDATION_CODE) {
                IBFSession session = getSessionByChannel(channel);
                for (int i = 4; i < data.length; i++) {
                    data[i] = (byte) ((data[i] ^ session.getValidationCode()) & 0x00ff);
                }
            }

            IBFSession session = this.getSessionByChannel(channel);
            if (session != null) {
                session.putOutMessageOnQueue(mOut);
            }
            Main.logger.info(mOut);

            if (Main.mapChannelWebSocket.get(channel) != null) {
                channel.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
            } else {
                channel.writeAndFlush(Unpooled.wrappedBuffer(data));
            }
        } catch (Exception ex) {
            Main.logger.error(ex.getMessage(), ex);
            if (channel.isActive()) {
                channel.close();
            }
        }
    }

    private void write(Channel channel, MessageOut mOut) {
        if (channel == null) {
            return;
        }

        Main.logger.info(mOut);
        byte[] data = mOut.toBytes();
        if (mOut.getTag() != Tags.SC_VALIDATION_CODE) {
            IBFSession session = getSessionByChannel(channel);
            for (int i = 4; i < data.length; i++) {
                data[i] = (byte) ((data[i] ^ session.getValidationCode()) & 0x00ff);
            }
        }

        if (Main.mapChannelWebSocket.get(channel) != null) {
            channel.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
        } else {
            channel.writeAndFlush(Unpooled.wrappedBuffer(data));
        }
    }

    public void onMessage(Channel channel, MessageIn mIn) {
        Main.logger.info(mIn);
        if (mIn instanceof CSClientInfo) {
            CSClientInfo csClientInfo = (CSClientInfo) mIn;
            String sessionId = csClientInfo.getClientInfo().sessionId;
            IBFSession bfSession = this.getSessionById(sessionId);
            if (bfSession == null) { //Kết nối mới
                createSession(channel, csClientInfo.getClientInfo());
                write(channel, new SCInitSession(SCInitSession.START_NEW_SESSION));
            } else { // Kết nối lại
                bfSession.setChannel(channel);
                bfSession.setClientInfo(csClientInfo.getClientInfo());
                write(channel, new SCInitSession(SCInitSession.CONTINUE_OLD_SESSION));
                bfSession.reSendMessageFromQueue();
            }
        }
        IBFSession session = this.getSessionByChannel(channel);
        if (session != null) {
            if (mIn.getMSequence() <= session.getMSequence()) {
                return; //Không thực thi bản tin đã thực thi rồi
            }
            session.setLastTimeReceive(System.currentTimeMillis());
            session.cleanOutMessageQueue(mIn);
        }
        mIn.execute(channel);
    }

    /**
     * Liên tục quét mỗi giây một lần toàn bộ các session xem session nào bị
     * timeout thì xoá
     */
    private void startControlThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Iterator it = mapSessionIdToSession.keySet().iterator();
                    while (it.hasNext()) {
                        String sessionId = (String) it.next();
                        IBFSession session = mapSessionIdToSession.get(sessionId);
                        if (System.currentTimeMillis() - session.getLastTimeReceive() >= BFSessionManager.SESSION_TIMEOUT * 1000) {
                            session.onTimeout();
                            removeSession(session);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception tex) {

                    }
                }
            }
        }).start();
    }

    public void onChannelClose(Channel channel) {
        IBFSession session = this.getSessionByChannel(channel);
        if (session != null) {
            session.onChannelClose(channel);
        }
        mapChannelToSession.remove(channel);
    }
    
    public void sendToAll(MessageOut mOut){
        Iterator it = mapChannelToSession.keySet().iterator();
        while (it.hasNext()){
            Channel channel = (Channel) it.next();
            sendMessage(channel, mOut.clone());
        }
    }

}
