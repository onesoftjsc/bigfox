/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.io.core.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.onesoft.bigfox.io.core.compress.BFCompressManager;
import vn.com.onesoft.bigfox.io.core.encrypt.BFEncryptManager;
import vn.com.onesoft.bigfox.io.core.pack.BFPacker;
import vn.com.onesoft.bigfox.io.core.session.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.io.message.base.MessageExecute;

/**
 *
 * @author Quan
 */
public class SocketClientHandler extends ChannelInboundHandlerAdapter {

    MessageExecute mf = MessageExecute.getInstance();

    public SocketClientHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int length = buf.getInt(buf.readerIndex());
        byte[] data = new byte[length];
        buf.readBytes(data);
        data = BFPacker.pack(ctx.channel(), data);
        if (data != null) {
            try {

                data = BFCompressManager.getInstance().decompress(data);
                data = BFEncryptManager.crypt(data);

                mf.onMessage(ctx.channel(), data); //Thực thi yêu cầu từ Client
            } catch (Exception ex) {
                ctx.channel().close();
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("Client connected!: " + ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("ChannelClosed: " + ctx.channel());
        ctx.close();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            
        }
        ConnectionManager.getInstance().init();
    }
}
