/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
 */
public class SocketManager {

    private static SocketManager _instance = null;

    public static SocketManager getInstance() {
        if (_instance == null) {
            _instance = new SocketManager();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    _instance.init();
                }
            }).start();
        }
        return _instance;
    }

    private void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new SocketChannelDecoder());
                            ch.pipeline().addLast("handler", new SocketServerHandler());

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            BFLogger.getInstance().info("Socket start at port " + BFConfig.getInstance().getPortSocket());
            ChannelFuture f = b.bind(BFConfig.getInstance().getPortSocket()).sync();

            f.channel().closeFuture().sync();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            System.exit(0);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
