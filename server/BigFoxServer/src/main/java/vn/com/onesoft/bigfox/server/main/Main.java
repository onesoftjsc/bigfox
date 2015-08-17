/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.main;

import com.google.common.collect.MapMaker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.security.cert.CertificateException;
import java.util.Map;
import javax.net.ssl.SSLException;
import vn.com.onesoft.bigfox.server.io.message.core.BFLogger;
import vn.com.onesoft.bigfox.server.io.message.core.MessageExecute;
import vn.com.onesoft.bigfox.server.io.core.socket.SocketChannelDecoder;
import vn.com.onesoft.bigfox.server.io.core.socket.SocketServerHandler;
import vn.com.onesoft.bigfox.server.io.core.websocket.WebSocketServerInitializer;

/**
 *
 * @author phamquan
 */
public class Main {


    public static ServerBootstrap bootstrap;//Help class để khởi tạo server socket

    public static ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//Netty, lưu trữ tất cả channels để đóng lại khi tắt ứng dụng
    public static Map<Channel, Integer> mapChannelToValidationCode = new MapMaker().makeMap();
    public static Map<Channel, Boolean> mapChannelWebSocket = new MapMaker().makeMap();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        MessageExecute.getInstance();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    init();
                } catch (Exception ex) {

                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    initWebSocket();
                } catch (Exception ex) {

                }
            }
        }).start();

    }

//    static void changeClassLoader() {
//        try {
//            ClassLoader currentThreadClassLoader
//                    = Thread.currentThread().getContextClassLoader();
//            String pathToJar = "/Users/phamquan/Desktop/sdk/1.0/BigFoxServer/dist/BigFox.jar";
//            URL[] urls = {new URL("jar:file:" + pathToJar + "!/")};
//            URLClassLoader urlClassLoader
//                    = new URLClassLoader(urls,
//                            currentThreadClassLoader);
//
//            Thread.currentThread().setContextClassLoader(urlClassLoader);
//        } catch (MalformedURLException ex) {
//
//        }
//    }

    static void init() throws Exception {
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
                    .option(ChannelOption.SO_BACKLOG, 128) // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            BFLogger.getInstance().info("Socket start at port " + Config.PORT_SOCKET);
            ChannelFuture f = b.bind(Config.PORT_SOCKET).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void initWebSocket() throws InterruptedException, CertificateException, SSLException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new WebSocketServerInitializer());
            BFLogger.getInstance().info("WebSocket start at port " + Config.PORT_WEBSOCKET);
            Channel ch = b.bind(Config.PORT_WEBSOCKET).sync().channel();

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
