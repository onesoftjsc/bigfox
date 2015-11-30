/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.session;

import com.google.common.collect.MapMaker;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Map;
import vn.com.onesoft.bigfox.io.core.compress.BFCompressManager;
import vn.com.onesoft.bigfox.io.core.encrypt.BFEncryptManager;
import vn.com.onesoft.bigfox.io.core.socket.SocketChannelDecoder;
import vn.com.onesoft.bigfox.io.core.socket.SocketClientHandler;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.base.BFConfig;
import vn.com.onesoft.bigfox.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.io.message.core.sc.SCInitSession;
import vn.com.onesoft.bigfox.io.message.core.sc.SCValidationCode;

/**
 *
 * @author QuanPH
 */
public class ConnectionManager {

    public final static int MAX_TIMEOUT = 20; //20 s
    private static ConnectionManager _instance;
    private int validationCode;
    private Channel channel;
    public int curSSequence;
    public int curMSequence;
    private int mSequenceFromServer;
    public int timeRetriesToReconnect = 4;
    
    Map<Integer, MessageOut> queueOutMessage = new MapMaker().makeMap();
    public long lastPingReceivedTime;
    public String sessionId = "";

    private ISessionControl sessionControl = new DefaultSessionControl();

    public static ConnectionManager getInstance() {
        if (_instance == null) {
            _instance = new ConnectionManager();
        }
        return _instance;
    }

    public void init() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("decoder", new SocketChannelDecoder());
                    ch.pipeline().addLast("handler", new SocketClientHandler());
                }
            });

            ChannelFuture f = b.connect(BFConfig.getInstance().getIp(), BFConfig.getInstance().getPort()).sync();
            channel = f.channel();
            f.channel().closeFuture().sync();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void onMessage(Channel channel, MessageIn mIn) {
        BFLogger.getInstance().info(mIn);
        if (!(mIn instanceof SCValidationCode) && !(mIn instanceof SCInitSession)) {
            if (mIn.getSSequence() <= curSSequence) {
                return; // Không thực thi bản tin đã thực thi rồi
            }
            curSSequence = mIn.getSSequence();
            for (int i = mSequenceFromServer; i <= mIn.getMSequence(); i++) {
                queueOutMessage.remove(i);
            }
            mSequenceFromServer = mIn.getMSequence();
        }
        mIn.execute(channel);
    }

    public void write(MessageOut mOut) {
        BFLogger.getInstance().info(mOut);
        Message m = mOut.getClass().getAnnotation(Message.class);
        
        curMSequence++;
        mOut.setMSequence(curMSequence);
        if (!m.isCore()) {
            queueOutMessage.put(curMSequence, mOut);
        }
        flush(mOut);
    }

    private void flush(MessageOut mOut) {
        byte[] data = mOut.toBytes();

        data = BFEncryptManager.crypt(data);
        data = BFCompressManager.getInstance().compress(data);

        channel.writeAndFlush(Unpooled.wrappedBuffer(data));
    }

    public void onStartNewSession() {
        sessionControl.onStartSession();
    }

    public void resendOldMessages() {
        try {
            // Resend messages
            for (int i = mSequenceFromServer + 1; i < curMSequence; i++) {
                MessageOut bm = queueOutMessage.get(i);
                if (bm != null) {
                    flush(bm);
                }
            }
        } catch (Exception ex) {

        }
    }

    public void onContinueOldSession() {
        sessionControl.onReconnectedSession();
    }

    /**
     * @param sessionControl the sessionControl to set
     */
    public void setSessionControl(ISessionControl sessionControl) {
        this.sessionControl = sessionControl;
    }

    /**
     * @return the validationCode
     */
    public int getValidationCode() {
        return validationCode;
    }

    /**
     * @param validationCode the validationCode to set
     */
    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    /**
     * @return the channel
     */
    public Channel getChannel() {
        return channel;
    }

}
