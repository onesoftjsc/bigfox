/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Random;
import vn.com.onesoft.bigfox.server.io.message.core.MessageExecute;
import vn.com.onesoft.bigfox.server.io.message.sc.SCValidationCode;
import vn.com.onesoft.bigfox.server.io.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author Quan
 */
public class SocketServerHandler extends ChannelInboundHandlerAdapter {

    MessageExecute mf = MessageExecute.getInstance();

    public SocketServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.getInt(buf.readerIndex());

        byte[] data = new byte[length];
        buf.readBytes(data);
        try {
            //Giai ma
            int validationCode = Main.mapChannelToValidationCode.get(ctx.channel());
            for (int i = 4; i < data.length; i++) {
                data[i] = (byte) ((data[i] ^ validationCode) & 0x00ff);
            }
            //Ma hoa
            mf.onMessage(ctx.channel(), data); //Thực thi yêu cầu từ Client
        } catch (Exception ex) {
            ctx.channel().close();
            Main.logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Main.logger.info("Client connected!: " + ctx.channel());
        Main.allChannels.add(ctx.channel());
        Random r = new Random();
        int validationCode = r.nextInt();
        BFSessionManager.getInstance().sendMessage(ctx.channel(), new SCValidationCode(validationCode));
        Main.mapChannelToValidationCode.put(ctx.channel(), validationCode);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Main.logger.info("ChannelClosed: " + ctx.channel());
        BFSessionManager.getInstance().onChannelClose(ctx.channel());
        Main.allChannels.remove(ctx.channel());
        Main.mapChannelToValidationCode.remove(ctx.channel());

    }
}
