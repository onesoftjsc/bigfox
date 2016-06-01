/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.session;

import com.google.common.collect.MapMaker;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import vn.com.onesoft.bigfox.server.io.core.data.encrypt.BFEncryptManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.IBFZone;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageIn;
import vn.com.onesoft.bigfox.server.io.message.base.MessageOut;
import vn.com.onesoft.bigfox.server.io.message.core.objects.ClientInfo;
import vn.com.onesoft.bigfox.server.io.message.core.sc.SCPing;
import vn.com.onesoft.bigfox.server.io.message.core.tags.CoreTags;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author QuanPH Session lưu giữ phiên làm việc của client. - Một phiên làm
 * việc của Client ứng với 1 session. - Khi người dùng bắt đầu mở ứng dụng,
 * client kết nối đến máy chủ, gửi lên bản tin CSClientInfo, đây là lúc bắt đầu
 * cho 1 session. - Session kết thúc khi người dùng tắt ứng dụng. Đến đây phát
 * sinh 1 vấn đề, phía trên máy chủ không có cách nào biết được sự kiện tắt ứng
 * dụng của người dùng. Có 2 cách giải quyết vấn đề: 1. Ngắt kết nối socket đồng
 * nghĩa với đóng session. Cách này có nhược điểm là khi kết nối mạng yếu, người
 * dùng vẫn mở ứng dụng nhưng đã bị ngắt kết nối đến máy chủ, máy chủ hiểu nhầm
 * là người dùng đã tắt ứng dụng. Cách này không thể dùng. 2. Đặt timeout cho
 * mỗi session, nếu quá thời gian timeout mà không nhận bản tin mới từ client
 * thì session sẽ kết thúc. Cách này cho phép phiên làm việc thông suốt dù mạng
 * kết nối yếu. Khi kết nối mạnh trở lại, session tiếp tục công việc xử lý bản
 * tin. Trong trường hợp kết nối mạng bị ngắt, session sẽ vẫn không bị xoá, mà
 * tồn tại cho đến hết timeout, chờ client kết nối trở lại. Nguyên tắc là không
 * tồn tại 2 session của cùng một người dùng trên hệ thống
 */
public class BFSession implements IBFSession {

    private Channel channel;
    private Map<Integer, MessageOut> queueOutMessage = new MapMaker().makeMap(); //Hàng đợi bản tin được gửi đi nhưng chưa được confirm từ client
    private String sessionId;
    private BFSessionType sessionType;
    private int validationCode;
    private ClientInfo clientInfo;
    private long lastTimeReceive;
    private int curMSequence;
    private int curSSequence;
    private int sSequenceFromClient;
    private IBFZone zone;

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(Channel channel) {
        this.validationCode = BFEncryptManager.mapChannelToValidationCode.get(channel);
        this.channel = channel;
        IBFZone zone = BFZoneManager.getInstance().getZone(channel);
        zone.addSession(this);
    }

    @Override
    public String getSessionId() {
        return clientInfo.sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public BFSessionType getSessionType() {
        return sessionType;
    }

    @Override
    public void setSessionType(BFSessionType sessionType) {
        this.sessionType = sessionType;
    }

    @Override
    public String getIp() {
        return channel.remoteAddress().toString();
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getValidationCode() {
        return validationCode;
    }

    @Override
    public int getSSequence() {
        return curSSequence;
    }

    @Override
    public void setSSequence(int sSequence) {
        this.curSSequence = sSequence;
    }

    @Override
    public int getMSequence() {
        return curMSequence;
    }

    @Override
    public void setMSequence(int mSequence) {
        this.curMSequence = mSequence;
    }

    @Override
    public void setNewChannel(Channel channel, ClientInfo clientInfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the clientInfo
     */
    @Override
    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    /**
     * @param clientInfo the clientInfo to set
     */
    @Override
    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Override
    public void putOutMessageOnQueue(MessageOut mOut) {
        if (!(mOut instanceof SCPing)) {
            queueOutMessage.put(mOut.getSSequence(), mOut);
        }
    }

    @Override
    public void cleanOutMessageQueue(MessageIn mIn) {
        int sSequenceNew = mIn.getSSequence();
        for (int i = sSequenceFromClient; i <= sSequenceNew; i++) {
            queueOutMessage.remove(i);
        }

        sSequenceFromClient = sSequenceNew;
    }

    @Override
    public void reSendMessageFromQueue() {
        if (sSequenceFromClient < curSSequence) {
            for (int i = sSequenceFromClient + 1; i <= curSSequence; i++) {
                MessageOut mOut = queueOutMessage.get(i);
                if (mOut == null) {
                    continue;
                }
                byte[] data = mOut.toBytes();
                if (mOut.getTag() != CoreTags.SC_VALIDATION_CODE) {
                    for (int k = 4; k < data.length; k++) {
                        data[k] = (byte) ((data[k] ^ this.getValidationCode()) & 0x00ff);
                    }
                }
                if (Main.mapChannelWebSocket.get(channel) != null) {
                    channel.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
                } else {
                    channel.writeAndFlush(Unpooled.wrappedBuffer(data));
                }
            }
        }
    }

    @Override
    public long getLastTimeReceive() {
        return this.lastTimeReceive;
    }

    @Override
    public void setLastTimeReceive(long lastTimeReceive) {
        this.lastTimeReceive = lastTimeReceive;
    }

    /**
     * Khi timeout thì xoá session
     */
    @Override
    public void onTimeout() {
        BFLogger.getInstance().info("onTimeout");
        close();
    }

    @Override
    public IBFZone getZone() {
        return zone;
    }

    @Override
    public void setZone(IBFZone zone) {
        this.zone = zone;
    }
    
ScheduledFuture scheduledFuture = null;
    
    @Override
    public void start() {
        this.setLastTimeReceive(System.currentTimeMillis());
         scheduledFuture = BFSessionManager.getInstance().scheduledExecutorService.scheduleAtFixedRate(new BFSessionTick(this), 1, 1, TimeUnit.SECONDS);
    }
    
    @Override
    public void close(){
        scheduledFuture.cancel(true);
        BFSessionManager.getInstance().removeSession(this);
    }
    
    public void sendMessage(MessageOut mOut){
        BFSessionManager.getInstance().sendMessage(channel, mOut);
    }

}
