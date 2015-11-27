/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.telnet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author QuanPH
 */
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext chc, String request) throws Exception {
        request = request.trim();
        CommandManager commandManager = new CommandManager(chc.channel(), request);
        commandManager.runCommands();
    }
}
