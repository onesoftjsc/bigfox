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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import org.bouncycastle.openssl.PEMReader;
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

            //HuongNS
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        _instance.initSSL();
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
                    .childHandler(new WebSocketServerInitializer(null));
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

//    //HuongNS
    private void initSSL() throws CertificateException, SSLException {

        SslContext sslCtx = null;
        try {
            SSLContext serverContext = SSLContext.getInstance("TLS");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            
//            X509Certificate certRoot = (X509Certificate) cf.generateCertificate(new FileInputStream("/Users/phamquan/Desktop/livetube/livetube-server/LiveTube/ssl/AddTrustExternalCARoot.crt"));
//            X509Certificate certStar = (X509Certificate) cf.generateCertificate(new FileInputStream("/Users/phamquan/Desktop/livetube/livetube-server/LiveTube/ssl/STAR_vivulive_com.crt"));
//            X509Certificate certStrust = (X509Certificate) cf.generateCertificate(new FileInputStream("/Users/phamquan/Desktop/livetube/livetube-server/LiveTube/ssl/COMODORSAAddTrustCA.crt"));
//            X509Certificate certDomain = (X509Certificate) cf.generateCertificate(new FileInputStream("/Users/phamquan/Desktop/livetube/livetube-server/LiveTube/ssl/COMODORSADomainValidationSecureServerCA.crt"));
            
            X509Certificate certRoot = (X509Certificate) cf.generateCertificate(new FileInputStream("/home/onesoft/vivulive_service/bin/STAR_vivulive_com/AddTrustExternalCARoot.crt"));
            X509Certificate certStar = (X509Certificate) cf.generateCertificate(new FileInputStream("/home/onesoft/vivulive_service/bin/STAR_vivulive_com/STAR_vivulive_com.crt"));
            X509Certificate certStrust = (X509Certificate) cf.generateCertificate(new FileInputStream("/home/onesoft/vivulive_service/bin/STAR_vivulive_com/COMODORSAAddTrustCA.crt"));
            X509Certificate certDomain = (X509Certificate) cf.generateCertificate(new FileInputStream("/home/onesoft/vivulive_service/bin/STAR_vivulive_com/COMODORSADomainValidationSecureServerCA.crt"));

            PrivateKey privateKeyFromString = getPrivateKeyFromString("-----BEGIN RSA PRIVATE KEY-----\n"
                    + "MIIEpAIBAAKCAQEAsKadjzLUOUPL84cH0HyYN3zo+8Z8qyhY8YiO5qgMl6XLB8PD\n"
                    + "hYTgNNyodBTZs94o+5ePyFPxiZe6IfS8wtTA4Ilvuz3WCQXtMt4YmiWIxHAc/kEF\n"
                    + "Sd68NtZjjZ/EoWuppCP5Cw1fITK9Z9ImGa4R1UVdDif3raKkZzAL/9kwH+WuSBs9\n"
                    + "QEdapKLyv3LJ+Co/YReEHi4SeYRoIDt1EGW9DL1Jy7GHgI9jwXQtwq1enrKH927b\n"
                    + "MeJaba/f/CzW/epHczg9Mz8Nia+vYfGoGVvXbWTbAl/u0tAdgupmVmcZJKCg35KK\n"
                    + "uXHvrDouDpJFv/JCBeJwStWt548Um6IMvD7wjQIDAQABAoIBAQCoiO39tyLe1533\n"
                    + "47ZC5PxcxCGMRJygt0fUeNLNoTsSW61O5SjfdBxskrFsvo/NmYFEOB+Hov7opQeC\n"
                    + "afrFbzHth3JAFAjmZu8c/uQ9/N11fPlMYkBnb8fmEbi9X9a7ovogjemG7+T4yW4H\n"
                    + "ZWnQSlDdRuY3IU6eLHPptt9b45yUcHmE5T2ZYNOxGz1iUBUdQy9NSUvEdrFJqnm0\n"
                    + "Pz+4q3vuXPJ5QyJM25/V7UnEJnBrd1j3Cvgcu2ijVR36OI344b7py9CcqAqzqZYO\n"
                    + "Y4o6Li2Igt/ZVz8Ni7949b6b0XcmhFVJsnMbSkNiNi2A8BLGnVehm9dbbttwdVOW\n"
                    + "CKYCsMXBAoGBANU8ThcsJhaQY4tEt/e+NoO/cnmQGxvEpSAfMKFMtEhSLG1FzM1a\n"
                    + "HZlc+vv0fMhH5FjpftqGj94mROJboj/3yGF+Nd6d63TU9iOO9pfRgZE4gmdE2/NH\n"
                    + "fwGDhK78DYjvMb/w3kmiqgkivvmH6FB+Ej03oFnAIza+r1hbRZ1zkYqJAoGBANQU\n"
                    + "BQR6cdZaKo/u0ldXw/mJBv8/XQnqXobCLK/qQ8Rt0ZrueLrFxTBG295M6maGudui\n"
                    + "uXptOMzotaxsbU6GQq+tP+ezCk+sUF1nMidoClN6O2txwiYWwdI5RPJ+JFNHCFNT\n"
                    + "tAvW8fZrxEPVicUUvTMqZfue46rKovFsLWIDTOTlAoGBAMtSVzutMxwszV4MRkaK\n"
                    + "LrztHaBgEhkmJjU8yEflNboH8Ki495GPLc4AZKk99IWcV1WYPwbra97tKp2Ttl+8\n"
                    + "Smr7xy3f8pVSQuig9uzILf1knqMNe5S7RZUR7MBVQiqhuRRF/g7EddGEOgkA2Dl4\n"
                    + "HLgWhGVP2cPnipZ7YSPDALHRAoGAO+ykNk262MyFdkIPISrlbx7u4TSwP1r/9qfr\n"
                    + "JJHXMCuvrE1xw6SaQTfkbH3jRbEP1yW1rwgpooymuN+ySxvYv/b+NaP7Z+PSmXHN\n"
                    + "VCErucax/3wXVdfcUGBqQPKvpPBPQ+xE1YuYtv5gaKJv2UYw58I1KtIMlnfHbtwY\n"
                    + "PrecBJkCgYAXRcsd7b0jxKKvaIky+719jdAUjcs23aSvK/KFZOjtKYpL+y1XUOF8\n"
                    + "wzZai61U+/+ia325P6+Xi/RI+BvGKFs6UdRkwQN2fT+QpVj62Uvb7yq9z/W/pcod\n"
                    + "xKEtAMI8/zATyn0TVSeu5MV+SnUF5crKeOL+3TkemCjVitOJZ3k/4Q==\n"
                    + "-----END RSA PRIVATE KEY-----");

            //certStar truyền trước certRoot
            sslCtx = SslContextBuilder.forServer(privateKeyFromString, certStar, certRoot, certStrust, certDomain).build();
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
            BFLogger.getInstance().info("WebSocket start at port " + BFConfig.getInstance().getPortWebSocketSSL());
            Channel ch = b.bind(BFConfig.getInstance().getPortWebSocketSSL()).sync().channel();

            ch.closeFuture().sync();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
            System.exit(0);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
    public static PrivateKey getPrivateKeyFromString(String key) throws Exception {
        PrivateKey privateKey = null;
        try {
            PEMReader reader = new PEMReader(new StringReader(key), null, "SunRsaSign");
            KeyPair pemPair = (KeyPair) reader.readObject();

            reader.close();

            privateKey = (PrivateKey) pemPair.getPrivate();
        } catch (Exception ex) {
            BFLogger.getInstance().error(ex.getMessage(), ex);
        }
        return privateKey;
    }
//
//    private void initSSL() throws CertificateException, SSLException {
//
//        SslContext sslCtx = null;
//        try {
//            sslCtx = SslContextBuilder.forServer(new File(BFConfig.getInstance().getCertificateFile()), new File(BFConfig.getInstance().getPrivateFile())).build();
//        } catch (Exception ex) {
//            BFLogger.getInstance().error(ex.getMessage(), ex);
//        }
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(bossGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .childHandler(new WebSocketServerInitializer(sslCtx));
//            BFLogger.getInstance().info("WebSocket start at port " + BFConfig.getInstance().getPortWebSocketSSL());
//            Channel ch = b.bind(BFConfig.getInstance().getPortWebSocketSSL()).sync().channel();
//
//            ch.closeFuture().sync();
//        } catch (Exception ex) {
//            BFLogger.getInstance().error(ex.getMessage(), ex);
//            System.exit(0);
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
}
