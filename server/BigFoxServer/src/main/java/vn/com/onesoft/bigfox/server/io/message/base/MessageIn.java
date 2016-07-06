/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.bigfox.server.io.message.base;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSession;
import vn.com.onesoft.bigfox.server.io.core.business.session.BFSessionManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.BFZoneManager;
import vn.com.onesoft.bigfox.server.io.core.business.zone.IBFZone;

/**
 *
 * @author Quan
 */
public abstract class MessageIn extends BaseMessage {

    private static short TAG = 0;
    ByteArrayInputStream byteArrayInput;
    byte[] data = new byte[0]; 
    DataInputStream in;
    public final static int _HEADER_LENGTH = 17;

    BFSession session;

    public MessageIn() {
    }

    public BFSession getBFSession() {
        return session;
    }

    public void setBFSession(BFSession session) {
        this.session = session;
    }

    public void sendMessage(MessageOut mOut) {
        BFSessionManager.getInstance().sendMessage(this.getBFSession().getChannel(), mOut);
    }

    public void sendMessageToAll(MessageOut mOut) {
        IBFZone zone = BFZoneManager.getInstance().getZone(this.getBFSession().getChannel());

        zone.sendMessageToAll(mOut);
    }

    public abstract void execute();
}
