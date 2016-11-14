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

    // The parent classloader
//    public static ClassLoader parentLoader;
//    public static URLClassLoader myLoader;
    static {
        try {
            String mainPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            mainPath = mainPath.substring(0, mainPath.lastIndexOf('/') + 1);
            File classesDir = new File(mainPath);
//            parentLoader = Main.class.getClassLoader();
//
//            myLoader = new URLClassLoader(
//                    new URL[]{classesDir.toURL()}, parentLoader);
        } catch (Exception ex) {
            ex.printStackTrace();
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
                    argList.add(args[i]);
                }
            }
            try {
                String classPath = "vn.com.onesoft.bigfox.server.telnet." + commandClassName;
                //HuongNS
//                BFZone zone = (BFZone)BFZoneManager.getInstance().mapTelnetPathToZone.get(classPath);                
//                Class cls = zone.getTelnetClass(classPath);

                Class cls = Class.forName(classPath);
                Command command = (Command) cls.newInstance();
                command.setArgs(argList);
                response = command.execute();
            } catch (ClassNotFoundException ex) {
                response = "Unknown command";
                BFLogger.getInstance().error(ex.getMessage(), ex);
            } catch (IllegalAccessException iex) {
            } catch (InstantiationException inex) {
            }
        }
        ChannelFuture future = channel.writeAndFlush(response + "\r\n");
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
