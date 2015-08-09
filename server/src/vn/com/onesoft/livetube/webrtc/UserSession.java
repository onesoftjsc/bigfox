package vn.com.onesoft.livetube.webrtc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;
//import javax.websocket.Session;
import org.kurento.client.WebRtcEndpoint;

/**
 * User session.
 *
 * @author huongns
 */
public class UserSession {

    private static final Logger log = LoggerFactory
            .getLogger(UserSession.class);

    private String name;
    private String page;
//    private Session session;
    private Channel channel;
    private WebRtcEndpoint webRtcEndpoint;

    public UserSession(Channel channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    public UserSession(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(JsonObject message) throws IOException {
        log.debug("Sending message from user '{}': {}", name, message);

//        session.getBasicRemote().sendText(message.toString());
//        MessageSender.getInstance().writeMessage(channel, new SCEChat(message.toString()));
    }

    public WebRtcEndpoint getWebRtcEndpoint() {
        return webRtcEndpoint;
    }

    public void setWebRtcEndpoint(WebRtcEndpoint webRtcEndpoint) {
        this.webRtcEndpoint = webRtcEndpoint;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

}
