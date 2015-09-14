/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javafx.application.Application;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.BFConfig;

/**
 *
 * @author QuanPH
 */
public class WebSocketManager {

    private static WebSocketManager _instance = null;

    public static WebSocketManager getInstance() {
        if (_instance == null) {
            _instance = new WebSocketManager();
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

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer());
            BFLogger.getInstance().info("WebSocket start at port " + BFConfig.getInstance().getPortWebSocket());
            Channel ch = b.bind(BFConfig.getInstance().getPortWebSocket()).sync().channel();

            ch.closeFuture().sync();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            System.exit(0);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
