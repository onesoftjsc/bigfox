package vn.com.onesoft.livetube.webrtc;


import org.kurento.client.EndOfStreamEvent;
import org.kurento.client.ErrorEvent;
import org.kurento.client.EventListener;
import org.kurento.client.MediaPipeline;
import org.kurento.client.PlayerEndpoint;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.client.KurentoClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import io.netty.channel.Channel;

//import javax.websocket.Session;

/**
 * Media Pipeline (connection of Media Elements) for playing the recorded one to
 * one video communication.
 *
 * @author huongns
 *
 */
public class PlayMediaPipeline {

    private static final Logger log = LoggerFactory
            .getLogger(PlayMediaPipeline.class);

    private MediaPipeline pipeline;
    private WebRtcEndpoint webRtc;
    private PlayerEndpoint player;

    public PlayMediaPipeline(KurentoClient kurento, String pathUser,
            final Channel channel) {
        // Media pipeline
        pipeline = kurento.createMediaPipeline();

        // Media Elements (WebRtcEndpoint, PlayerEndpoint)
        webRtc = new WebRtcEndpoint.Builder(pipeline).build();
        player = new PlayerEndpoint.Builder(pipeline, pathUser).build();

        // Connection
        player.connect(webRtc);

        // Player listeners
        player.addErrorListener(new EventListener<ErrorEvent>() {
            @Override
            public void onEvent(ErrorEvent event) {
                log.info("ErrorEvent: {}", event.getDescription());
                sendPlayEnd(channel);
            }
        });
        player.addEndOfStreamListener(new EventListener<EndOfStreamEvent>() {
            @Override
            public void onEvent(EndOfStreamEvent event) {
                sendPlayEnd(channel);
            }
        });
    }

    public void sendPlayEnd(Channel channel) {
        JsonObject response = new JsonObject();
        response.addProperty("id", "playEnd");
//        session.getBasicRemote().sendText(response.toString());
//        MessageSender.getInstance().writeMessage(channel, new SCEChat(response.toString()));
        // Release pipeline
        pipeline.release();
    }

    public void play() {
        player.play();
    }

    public String generateSdpAnswer(String sdpOffer) {
        return webRtc.processOffer(sdpOffer);
    }

    public MediaPipeline getPipeline() {
        return pipeline;
    }

}
