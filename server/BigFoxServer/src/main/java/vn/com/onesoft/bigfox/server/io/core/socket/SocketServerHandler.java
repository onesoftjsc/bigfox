/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.core.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Random;
import vn.com.onesoft.bigfox.server.io.core.compress.BFCompressManager;
import vn.com.onesoft.bigfox.server.io.core.encrypt.BFEncryptManager;
import vn.com.onesoft.bigfox.server.io.core.pack.BFPacker;
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
        data = BFPacker.pack(ctx.channel(), data);
        if (data != null) {
            try {
                
                data = BFCompressManager.getInstance().decompress(data);
                data = BFEncryptManager.crypt(ctx.channel(), data);
                
                mf.onMessage(ctx.channel(), data);
            } catch (Exception ex) {
                ctx.channel().close();
                BFLogger.getInstance().error(ex.getMessage(), ex);
            }
        }
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("channelActive : " + ctx.channel());
        Main.allChannels.add(ctx.channel());
        Random r = new Random();
        int validationCode = 0; // r.nextInt();
        BFSessionManager.getInstance().sendMessage(ctx.channel(), new SCValidationCode(validationCode));
        BFEncryptManager.mapChannelToValidationCode.put(ctx.channel(), validationCode);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        BFLogger.getInstance().info("channelInactive!: " + ctx.channel());
        BFLogger.getInstance().info("ChannelClosed: " + ctx.channel());
        BFSessionManager.getInstance().onChannelClose(ctx.channel());
        Main.allChannels.remove(ctx.channel());
        BFEncryptManager.mapChannelToValidationCode.remove(ctx.channel());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        BFLogger.getInstance().error("Channel exceptionCaught", cause);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        BFLogger.getInstance().info("Channel unregistered");
    }
    
    
}
