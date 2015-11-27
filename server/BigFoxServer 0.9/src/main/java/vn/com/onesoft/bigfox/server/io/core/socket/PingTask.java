/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.socket;

import io.netty.channel.Channel;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import vn.com.onesoft.bigfox.server.io.core.session.BFSessionManager;
import static vn.com.onesoft.bigfox.server.io.core.socket.SocketServerHandler.scheduleThread;
import vn.com.onesoft.bigfox.server.io.message.core.sc.SCPing;

/**
 *
 * @author QuanPH
 */
public class PingTask extends TimerTask {


    private Channel channel;

    public PingTask(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        BFSessionManager.getInstance().sendMessage(channel, new SCPing());
        SocketServerHandler.scheduleThread.schedule(new PingTask(channel), 5, TimeUnit.SECONDS);
    }

}
