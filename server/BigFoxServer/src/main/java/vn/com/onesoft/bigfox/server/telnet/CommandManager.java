/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.telnet;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFClassLoaderZone;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZone;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.message.base.BFLogger;
import vn.com.onesoft.bigfox.server.main.Main;

/**
 *
 * @author Quan
 */
public class CommandManager {

    Channel channel;
    String request = "";
    public static final String COMMAND_PREFIX = "CMD";

    static {
        try {
            String mainPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            mainPath = mainPath.substring(0, mainPath.lastIndexOf('/') + 1);

        } catch (Exception ex) {
BFLogger.getInstance().error(ex.getMessage(), ex);
        }
    }

    public CommandManager(Channel channel, String request) {
        this.channel = channel;
        this.request = request;
    }

    public void runCommands() {
        String response = "";
        boolean close = false;
        if (request.length() == 0) {
            response = "Please type something.";
        } else if (request.toLowerCase().equals("bye")) {
            response = "Have a good day!";
            close = true;
        } else {
            String[] args = request.split(";");
            String commandName = args[0];
            String commandClassName = CommandManager.COMMAND_PREFIX + commandName;
            ArrayList<String> argList = new ArrayList<String>();
            String strArg = "";
            for (int i = 1; i < args.length; i++) {
                try {
                    if (args[i].startsWith("['")) {
                        strArg = args[i].substring(2);
                    } else if (strArg.isEmpty()) {
                        argList.add(args[i]);
                    } else if (!strArg.isEmpty() && !args[i].endsWith("']")) {
                        strArg += " " + args[i];
                    } else if (!strArg.isEmpty() && args[i].endsWith("']")) {
                        strArg += " " + args[i].substring(0, args[i].length() - 2);
                        argList.add(strArg);
                        strArg = "";
                    }
                } catch (Exception ex) {
                    BFLogger.getInstance().error(ex.getMessage(), ex);
                    argList.add(args[i]);
                }
            }
            try {
                String classPath = commandClassName;
                //HuongNS
                BFZone zone = (BFZone) BFZoneManager.getInstance().mapTelnetPathToZone.get(classPath);
                Class cls = zone.getTelnetClass(classPath);
//                Class cls = zone.getClassLoader().getMapPathToClass().get(classPath);
//                Class cls = BFClassLoaderZone.getMapPathToClass().get(commandClassName);
                if (cls == null) {
                    response = "Unknown Command";
                } else {
                    Command command = (Command) cls.newInstance();
                    command.setArgs(argList);
                    response = command.execute();
                }
            } catch (InstantiationException | IllegalAccessException ex) {
BFLogger.getInstance().error(ex.getMessage(), ex);            }
        }
        ChannelFuture future = channel.writeAndFlush(response + "\r\n");
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
