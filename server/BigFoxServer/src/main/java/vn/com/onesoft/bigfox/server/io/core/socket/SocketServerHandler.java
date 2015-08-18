/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.core.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Random;
import vn.com.onesoft.bigfox.server.io.core.compress.CompressManager;
import vn.com.onesoft.bigfox.server.io.core.encrypt.EncryptManager;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.io.message.core.sc.SCValidationCode;
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

            data = CompressManager.getInstance().decompress(data);
            data = EncryptManager.crypt(ctx.channel(), data);

            mf.onMessage(ctx.channel(), data); //Thực thi yêu cầu từ Client
        } catch (Exception ex) {
            ctx.channel().close();
            BFLogger.getInstance().error(ex.getMessage(), ex);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("Client connected!: " + ctx.channel());
        Main.allChannels.add(ctx.channel());
        Random r = new Random();
        int validationCode = r.nextInt();
        BFSessionManager.getInstance().sendMessage(ctx.channel(), new SCValidationCode(validationCode));
        EncryptManager.mapChannelToValidationCode.put(ctx.channel(), validationCode);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("ChannelClosed: " + ctx.channel());
        BFSessionManager.getInstance().onChannelClose(ctx.channel());
        Main.allChannels.remove(ctx.channel());
        EncryptManager.mapChannelToValidationCode.remove(ctx.channel());

    }
}
