/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.channel.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.File;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLException;
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
                    try {
                        _instance.init();
                    } catch (Exception ex) {
                        BFLogger.getInstance().error(ex.getMessage(), ex);
                    }
                }
            }).start();
        }

        return _instance;
    }

    private void init() throws CertificateException, SSLException {

        SslContext sslCtx = null;
        try {
            sslCtx = SslContextBuilder.forServer(new File(BFConfig.getInstance().getCertificateFile()), new File(BFConfig.getInstance().getPrivateFile())).build();
        } catch (Exception ex) {
             BFLogger.getInstance().error(ex.getMessage(), ex);
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer(sslCtx));
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
