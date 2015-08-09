package vn.com.onesoft.livetube.webrtc;

import io.netty.channel.Channel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.client.RecorderEndpoint;
import vn.com.onesoft.livetube.io.message.objects.Category;
import vn.com.onesoft.livetube.io.message.objects.Video;
import vn.com.onesoft.livetube.util.LiveTubeUtil;

/**
 * Media Pipeline (WebRTC endpoints, i.e. Kurento Media Elements) and
 * connections for the 1 to n video communication.
 *
 * @author huongns
 */
public class CallMediaPipeline {

    private String id;
    private MediaPipeline pipeline;
    private WebRtcEndpoint callerWebRtcEP;
    private UserSession masterUserSession;
    private String masterTitle;
    private Video video;

    private ConcurrentHashMap<Channel, UserSession> viewers = new ConcurrentHashMap<Channel, UserSession>();

    private static final SimpleDateFormat df = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss-S");
    private String RECORDING_PATH;
    public static final String RECORDING_EXT = ".webm";

    private RecorderEndpoint recorderCaller;

    public CallMediaPipeline(MediaPipeline pipeline, WebRtcEndpoint callerWebRtcEP, UserSession masterUserSession) {
        this.pipeline = pipeline;
        this.callerWebRtcEP = callerWebRtcEP;
        this.masterUserSession = masterUserSession;

        RECORDING_PATH = "file:///tmp/"
                + df.format(new Date())
                + "-";

        recorderCaller
                = new RecorderEndpoint.Builder(pipeline, RECORDING_PATH + masterUserSession.getName()
                        + RECORDING_EXT).build();
        callerWebRtcEP.connect(recorderCaller);

        id = LiveTubeUtil.getRandomId(6);

    }

    public void record() {
        recorderCaller.record();
    }

    public void release() {
        if (pipeline != null) {
            pipeline.release();
        }
    }

    public ConcurrentHashMap<Channel, UserSession> getViewers() {
        return viewers;
    }

    public void setViewers(ConcurrentHashMap<Channel, UserSession> viewers) {
        this.viewers = viewers;
    }

    public MediaPipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(MediaPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public WebRtcEndpoint getCallerWebRtcEP() {
        return callerWebRtcEP;
    }

    public void setCallerWebRtcEP(WebRtcEndpoint callerWebRtcEP) {
        this.callerWebRtcEP = callerWebRtcEP;
    }

    public UserSession getMasterUserSession() {
        return masterUserSession;
    }

    public void setMasterUserSession(UserSession masterUserSession) {
        this.masterUserSession = masterUserSession;
    }

    public String getMasterTitle() {
        return masterTitle;
    }

    public void setMasterTitle(String masterTitle) {
        this.masterTitle = masterTitle;
    }

    public RecorderEndpoint getRecorderCaller() {
        return recorderCaller;
    }

    public void setRecorderCaller(RecorderEndpoint recorderCaller) {
        this.recorderCaller = recorderCaller;
    }

    public String getId() {
        return id;
    }

    public String getRECORDING_PATH() {
        return RECORDING_PATH;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

}
