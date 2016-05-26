/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.main;

import com.google.common.collect.MapMaker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.Map;
import vn.com.onesoft.bigfox.server.helper.classmanager.Extracter;
import vn.com.onesoft.bigfox.server.io.core.channel.socket.SocketManager;
import vn.com.onesoft.bigfox.server.io.core.channel.websocket.WebSocketManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.MessageExecute;
import vn.com.onesoft.bigfox.server.telnet.TelnetManager;

/**
 *
 * @author phamquan
 */
public class Main {

    public static ServerBootstrap bootstrap;//Help class để khởi tạo server socket

    public static ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//Netty, lưu trữ tất cả channels để đóng lại khi tắt ứng dụng
    public static Map<Channel, Boolean> mapChannelWebSocket = new MapMaker().makeMap();

        public static boolean isDebug = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        isDebug = false;
        BFZoneManager.getInstance();

        Extracter.getInstance();
        MessageExecute.getInstance();
//        Scanner.getInstance();
        BFConfig.getInstance();
        SocketManager.getInstance();
        WebSocketManager.getInstance();
        TelnetManager.getInstance();

     }


    public static void main1() {

        BFZoneManager.getInstance();
//        Extracter.getInstance();
        MessageExecute.getInstance();
//        Scanner.getInstance();
        BFConfig.getInstance();
        SocketManager.getInstance();
        WebSocketManager.getInstance();
        TelnetManager.getInstance();
    }

}
