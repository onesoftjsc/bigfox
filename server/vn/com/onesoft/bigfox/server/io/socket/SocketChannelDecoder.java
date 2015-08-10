/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.livetube.io.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import vn.com.onesoft.livetube.main.Main;

/**
 *
 * @author Quan
 */
public class SocketChannelDecoder extends ByteToMessageDecoder {

    public static int MAX_PACKET_LENGTH = 5000000; //5M

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf cb, List<Object> out) throws Exception {
        int numRead = cb.readableBytes();// Số lượng byte đã đọc được
        if (numRead < 4) {
            return;
        }
        cb.markReaderIndex();
        int length = cb.getInt(cb.readerIndex());
        if (length > MAX_PACKET_LENGTH) {
            Main.logger.error("PACKET LENGTH OVER!");
            chc.close();
            return;
        }

        if (numRead < length) {
            cb.resetReaderIndex();
            return;
        }

        out.add(cb.readBytes(length));
    }
}
