/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.io.core.session;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import vn.com.onesoft.bigfox.io.core.socket.SocketChannelDecoder;
import vn.com.onesoft.bigfox.io.core.socket.SocketClientHandler;
import vn.com.onesoft.bigfox.io.message.base.BFConfig;
import vn.com.onesoft.bigfox.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.io.message.base.MessageIn;

/**
 *
 * @author QuanPH
 */
public class BFConnectionManager {

    public final static int MAX_TIMEOUT = 20; //20 s
    private static BFConnectionManager _instance;

    public static BFConnectionManager getInstance() {
        if (_instance == null) {
            _instance = new BFConnectionManager();
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
            f.channel().closeFuture().sync();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void onMessage(Channel channel, MessageIn message) {

    }

}
