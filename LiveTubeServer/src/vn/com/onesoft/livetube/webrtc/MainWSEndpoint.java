/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.onesoft.livetube.webrtc;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
//import com.onesoft.one2many.CallHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
//import com.google.gson.JsonObject;
import io.netty.channel.Channel;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
import org.kurento.client.KurentoClient;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import vn.com.onesoft.livetube.io.session.BFSessionManager;
import vn.com.onesoft.livetube.main.LiveTubeContext;
//import org.kurento.client.factory.KurentoClient;
/**
 *
 * @author HuongNS at OneSoft
 */
//@ServerEndpoint("/call")
public class MainWSEndpoint {

//    public MainWSEndpoint() {
//        System.out.println("MainWSEndpoint call");
//    }
//    
    private static MainWSEndpoint instance = null;

    protected MainWSEndpoint() {
        // Exists only to defeat instantiation.

    }

    public static MainWSEndpoint getInstance() {
        if (instance == null) {
            instance = new MainWSEndpoint();
        }
        return instance;
    }

    final static String DEFAULT_KMS_WS_URI = "ws://192.168.1.135:8888/kurento";

    private KurentoClient kurento = KurentoClient.create(System.getProperty("kms.ws.uri",
            DEFAULT_KMS_WS_URI));

    public static UserRegistry registry = new UserRegistry();

//    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private int callId;
//    private static final org.slf4j.Logger log = LoggerFactory
//            .getLogger(CallHandler.class);
//    private static final Gson gson = new GsonBuilder().create();

    public static ConcurrentHashMap<Channel, CallMediaPipeline> pipelines = new ConcurrentHashMap<Channel, CallMediaPipeline>();
    public static ConcurrentHashMap<Channel, UserSession> guestBySessionId = new ConcurrentHashMap<Channel, UserSession>();
    public static ConcurrentHashMap<String, CallMediaPipeline> listMasterIdVideoByMaster = new ConcurrentHashMap<String, CallMediaPipeline>();
//    @OnMessage
//    public String onMessage(String message) {
//        System.out.print("onMessage=" + message);
//        return "huongns";
//    }
    public final Gson gson = new GsonBuilder().create();

//    @OnMessage
    public void onMessage(String data, Channel channel) throws IOException, JSONException {
//        MediaPipeline createMediaPipeline = kurento.createMediaPipeline();
        System.out.println("broadcastString: " + data);
//        for (Session peer : peers) {
//            if (!peer.equals(session)) {
//                peer.getBasicRemote().sendText("du lieu tu server = " + data);
//            }
//        }
//        javax.json.JsonObject jsonMessage = Json.createReader(new StringReader(data)).readObject();
//       JsonObject jsonMessage = gson.fromJson(data,
//                JsonObject.class);
        JSONObject jsonMessage = new JSONObject(data); // json
//JSONObject jsonMessage = jObject.getJSONObject("data"); // get data object
        String id = jsonMessage.getString("id"); // get the name from data.
//        JsonElement jelem = gson.fromJson(data, JsonElement.class);
//        JsonObject jsonMessage = jelem.getAsJsonObject();

        UserSession user = registry.getByChannel(channel);
//        System.out.println("");
        System.out.println("\n onMessage handleTextMessage: BigFoxContext.mapChannelToPlayer.get(channel).getdBUser().getId() = " + BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId());
        if (user != null) {
            System.out.print("\n onMessage Incoming message from user " + user.getName() + " | "
                    + jsonMessage);
        } else {
            System.out.print("\n onMessage Incoming message from new user: " + jsonMessage);
        }
//        System.out.println("jsonMessage.get(\"id\").getAsString() = " + jsonMessage.get("id").getAsString());
//
        switch (jsonMessage.getString("id")) {
            case "register":
                try {
                    register(channel, jsonMessage);
                } catch (Throwable t) {
                    System.out.println("Exception = " + t.getMessage());
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "resgisterResponse");
                    response.addProperty("response", "rejected");
                    response.addProperty("message", t.getMessage());
//                    session.getAsyncRemote().sendText(response.toString());
//                    MessageSender.getInstance().writeMessage(channel, new SCEChat(response.toString()));
                }
                break;
            case "onLoad":
                onLoad(channel, jsonMessage);
                break;

            case "master":
                try {
                    master(channel, jsonMessage);
                } catch (Throwable t) {
//                    stop(channel, jsonMessage);
//                    log.error(t.getMessage(), t);
                    System.out.println("Exception = " + t.getMessage());
                    t.printStackTrace();
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "masterResponse");
                    response.addProperty("response", "rejected");
                    response.addProperty("message", t.getMessage());
//                    session.sendMessage(new TextMessage(response.toString()));
//                    session.getAsyncRemote().sendText(response.toString());
//                    MessageSender.getInstance().writeMessage(channel, new SCEChat(response.toString()));
                }
                break;
            case "viewer":
                try {
                    viewer(channel, jsonMessage);
                } catch (Throwable t) {
                    stop(channel, jsonMessage);
//                    log.error(t.getMessage(), t);
                    System.out.println("Exception = " + t.getMessage());
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "viewerResponse");
                    response.addProperty("response", "rejected");
                    response.addProperty("message", t.getMessage());
//                    session.getAsyncRemote().sendText(response.toString());
//                    MessageSender.getInstance().writeMessage(channel, new SCEChat(response.toString()));
                }
                break;
            case "stop":
                stop(channel, jsonMessage);
                break;
            case "play":
                play(channel, jsonMessage);
                break;
            case "stopPlay":
                releasePipeline(channel);
            default:
                break;
        }
    }

//    @OnMessage
//    public void onMessage(ByteBuffer data, Session session) throws IOException {
//        System.out.println("broadcastBinary: " + data);
////        for (Session peer : peers) {
////            if (!peer.equals(session)) {
////                peer.getBasicRemote().sendBinary(data);
////            }
////        }
//    }
//
//    @OnOpen
//    public void onOpen(Session t) {
////        peers.add(t);
//    }
//
//    @OnClose
//    public void onClose(Session t) {
////        peers.remove(t);
//        try {
//            onMessage("mat ket noi = " + t.getId(), t);
//        } catch (IOException ex) {
//            Logger.getLogger(MainWSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("");
//        System.out.println("afterConnectionClosed: session id =  " + t.getId());
////        System.out.println("afterConnectionClosed: status =  " + status.getReason());
//
//        registry.removeBySession(t);
//
//        if (pipelines.contains(t.getId())) {
//            System.out.println("pipelines.remove(session.getId())=" + pipelines.remove(t.getId()));
//        }
//
//        if (guestBySessionId.contains(t.getId())) {
//            System.out.println(" guestBySessionId.remove(session.getId())=" + guestBySessionId.remove(t.getId()));
////        guestBySessionId.remove(session.getId());
//        }
//    }
//
//    @OnError
//    public void onError(Throwable t) {
//        System.out.println("onError = " + t.getMessage());
//    }
//@OnError
//    public void error(Session session, Throwable t) {
//        /* Remove this connection from the queue */
////        queue.remove(session);
////        logger.log(Level.INFO, t.toString());
////        logger.log(Level.INFO, "Connection error.");
//    }
    public synchronized void master(Channel channel,
            JSONObject jsonMessage) throws IOException, JSONException, Exception {
//        if (masterUserSession == null) {

//        UserSession masterUserSession = new UserSession(session);
        UserSession masterUserSession = registry.getByChannel(channel);

        MediaPipeline pipeline = kurento.createMediaPipeline();

        masterUserSession.setWebRtcEndpoint(new WebRtcEndpoint.Builder(
                pipeline).build());

        WebRtcEndpoint masterWebRtc = masterUserSession.getWebRtcEndpoint();
        String sdpOffer = jsonMessage.getString("sdpOffer");

        String masterTitle = jsonMessage.getString("masterTitle");

        String masterName = masterUserSession.getName();

        String sdpAnswer = masterWebRtc.processOffer(sdpOffer);

        JsonObject response = new JsonObject();
        response.addProperty("id", "masterResponse");
        response.addProperty("response", "accepted");
        //response.addProperty("masterName", masterUserSession.getName());
        response.addProperty("sdpAnswer", sdpAnswer);

        masterUserSession.sendMessage(response);

        Collection<UserSession> userSessions = guestBySessionId.values();
        System.out.println("userSessions size = " + userSessions.size());
        JsonObject masterNameResponse = new JsonObject();
        masterNameResponse.addProperty("id", "listMasterResponse");
        masterNameResponse.addProperty("masterName", masterName);
        masterNameResponse.addProperty("masterTitle", masterTitle);
        for (UserSession userSession : userSessions) {
            if (userSession.getPage().equals("viewLive")) {
                userSession.sendMessage(masterNameResponse);
            }
        }
        CallMediaPipeline callMediaPipeline = new CallMediaPipeline(pipeline, masterWebRtc, masterUserSession);
        callMediaPipeline.setMasterTitle(masterTitle);
        callMediaPipeline.record();
        pipelines.put(channel, callMediaPipeline);

//        } else {
//            JsonObject response = new JsonObject();
//            response.addProperty("id", "masterResponse");
//            response.addProperty("response", "rejected");
//            response.addProperty("message",
//                    "Another user is currently acting as sender. Try again later ...");
//            session.sendMessage(new TextMessage(response.toString()));
//        }
    }

    public void play(Channel channel, JSONObject jsonMessage)
            throws IOException, JSONException {
        String masterId = jsonMessage.getString("masterId");
//        log.debug("Playing recorded call of masterId '{}'", masterId);

        String recording_path = listMasterIdVideoByMaster.get(masterId).getRECORDING_PATH();
        String name = listMasterIdVideoByMaster.get(masterId).getMasterUserSession().getName();

        JsonObject response = new JsonObject();
        response.addProperty("id", "playResponse");

//        if (registry.getByName(user) != null
//                && registry.getBySession(session) != null) {
        PlayMediaPipeline playMediaPipeline = new PlayMediaPipeline(
                kurento, recording_path + name + CallMediaPipeline.RECORDING_EXT, channel);
        String sdpOffer = jsonMessage.getString("sdpOffer");
        String sdpAnswer = playMediaPipeline.generateSdpAnswer(sdpOffer);

        response.addProperty("response", "accepted");
        response.addProperty("sdpAnswer", sdpAnswer);

        playMediaPipeline.play();

//            pipelines.put(session.getId(), playMediaPipeline.getPipeline());
//        } else {
//            response.addProperty("response", "rejected");
//            response.addProperty("error", "No recording for user '" + user
//                    + "'. Please type a correct user in the 'Peer' field.");
//        }
//        session.sendMessage(new TextMessage(response.toString()));
//        session.getBasicRemote().sendText(response.toString());
//        MessageSender.getInstance().writeMessage(channel, new SCEChat(response.toString()));
    }

    public void releasePipeline(Channel channel) throws IOException {
//        String sessionId = session.getId();
        if (pipelines.containsKey(channel)) {
            pipelines.get(channel).release();
            pipelines.remove(channel);
        }
    }

    public synchronized void viewer(Channel channel,
            JSONObject jsonMessage) throws IOException, JSONException {

        UserSession viewer = new UserSession(channel);
        String to = jsonMessage.getString("to");
        UserSession master = registry.getByName(to);

        CallMediaPipeline getPipelineViewer = pipelines.get(master.getChannel());

        getPipelineViewer.getViewers().put(channel, viewer);

//        String sdpOffer = jsonMessage.getJSONObject("sdpOffer").toString();
        String sdpOffer = jsonMessage.getString("sdpOffer");

        WebRtcEndpoint nextWebRtc = new WebRtcEndpoint.Builder(getPipelineViewer.getPipeline()).build();

        viewer.setWebRtcEndpoint(nextWebRtc);

        getPipelineViewer.getCallerWebRtcEP().connect(nextWebRtc);

        String sdpAnswer = nextWebRtc.processOffer(sdpOffer);

        JsonObject response = new JsonObject();
        response.addProperty("id", "viewerResponse");
        response.addProperty("response", "accepted");
        response.addProperty("sdpAnswer", sdpAnswer);
        viewer.sendMessage(response);

    }

    public void register(Channel channel, JSONObject jsonMessage)
            throws IOException, JSONException {
        String name = jsonMessage.getString("name");
        System.out.println("register name = " + name);
        UserSession caller = new UserSession(channel, name);
        String responseMsg = "accepted";
        if (name.isEmpty()) {
            responseMsg = "rejected: empty user name";
        } else if (registry.exists(name)) {
            responseMsg = "rejected: user '" + name + "' already registered";
        } else {
            registry.register(caller);
//            guestBySessionId.put(session.getId(), caller);
        }

        JsonObject response = new JsonObject();
        response.addProperty("id", "resgisterResponse");
        response.addProperty("response", responseMsg);
        caller.sendMessage(response);
    }

    public synchronized void stop(Channel channel, JSONObject jsonMessage) throws IOException, JSONException {
//        String sessionId = session.getId();
        CallMediaPipeline callMediaPipeline = pipelines.get(channel);
//chua stop play
        if (callMediaPipeline != null) {
            for (UserSession viewer : callMediaPipeline.getViewers().values()) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "stopCommunication");
                viewer.sendMessage(response);
                System.out.println("stopCommunication");
            }
            for (UserSession userSession : guestBySessionId.values()) {
                if ("viewLive".equals(userSession.getPage())) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "removeMaster");
                    response.addProperty("masterName", callMediaPipeline.getMasterUserSession().getName());
                    userSession.sendMessage(response);
                    System.out.println("removeMaster");
                } else if ("viewVideo".equals(userSession.getPage())) {
                    JsonObject listMasterResponse;
                    listMasterResponse = new JsonObject();
                    listMasterResponse.addProperty("id", "listMasterResponse");
                    listMasterResponse.addProperty("masterName", callMediaPipeline.getMasterUserSession().getName());
                    listMasterResponse.addProperty("masterTitle", callMediaPipeline.getMasterTitle());
                    listMasterResponse.addProperty("masterId", callMediaPipeline.getId());
                    userSession.sendMessage(listMasterResponse);
                    System.out.println("listMasterResponse");
                }
            }

            System.out.println("listMasterIdVideoByMaster.put(callMediaPipeline.getId(), callMediaPipeline);");

            listMasterIdVideoByMaster.put(callMediaPipeline.getId(), callMediaPipeline);

            System.out.println("Releasing media pipeline");

            if (callMediaPipeline != null) {
//                callMediaPipeline.getRecorderCaller().release();
//                callMediaPipeline.getCallerWebRtcEP().release();
                callMediaPipeline.release();
            }

            pipelines.remove(channel);

        } else {
            String to = jsonMessage.getString("to");
            UserSession masterUserSession = registry.getByName(to);
            if (masterUserSession != null) {
                callMediaPipeline = pipelines.get(masterUserSession.getChannel());
                if (callMediaPipeline != null && callMediaPipeline.getViewers().containsKey(channel)) {
                    if (callMediaPipeline.getViewers().get(channel).getWebRtcEndpoint() != null) {
                        callMediaPipeline.getViewers().get(channel).getWebRtcEndpoint().release();
                    }
                    callMediaPipeline.getViewers().remove(channel);
                }

            }
        }
    }
//
//    @Override
//    public void afterConnectionClosed(Session session,
//            CloseStatus status) throws Exception {
////        log.info("afterConnectionClosed: session id =  " + session.getId());
////        log.info("afterConnectionClosed: status =  " + status.getReason());
//
//        registry.removeBySession(session);
//
//        if (pipelines.contains(session.getId())) {
////            log.info("pipelines.remove(session.getId())=" + pipelines.remove(session.getId()));
//        }
//
//        if (guestBySessionId.contains(session.getId())) {
////            log.info(" guestBySessionId.remove(session.getId())=" + guestBySessionId.remove(session.getId()));
////        guestBySessionId.remove(session.getId());
//        }
//    }

    public void onLoad(Channel channel, JSONObject jsonMessage) throws IOException, JSONException {
        UserSession initialUserSession = new UserSession(channel);
        String page = jsonMessage.getString("page");
//        System.out.println("onLoad: session.getId() = " + session.getId());
        System.out.println("handleTextMessage: BigFoxContext.mapChannelToPlayer.get(channel).getdBUser().getId() = " + BFSessionManager.getInstance().getSessionByChannel(channel).getdBUser().getId());

        System.out.println("onLoad: page = " + page);
        if (page != null) {
            initialUserSession.setPage(page);
            if ("viewVideo".equals(page)) {
                JsonObject listMasterResponse;
                for (CallMediaPipeline callMediaPipeline : listMasterIdVideoByMaster.values()) {
                    listMasterResponse = new JsonObject();
                    listMasterResponse.addProperty("id", "listMasterResponse");
                    listMasterResponse.addProperty("masterName", callMediaPipeline.getMasterUserSession().getName());
                    listMasterResponse.addProperty("masterTitle", callMediaPipeline.getMasterTitle());
                    listMasterResponse.addProperty("masterId", callMediaPipeline.getId());
//                    initialUserSession.sendMessage(listMasterResponse);
//                    MessageSender.getInstance().writeMessage(channel, new SCEChat(listMasterResponse.toString()));
                    System.out.println("viewVideo: " + initialUserSession.getName());
                }
            } else if ("viewLive".equals(page)) {
                Collection<CallMediaPipeline> callMediaPipelines = pipelines.values();
                JsonObject listMasterResponse;
                for (CallMediaPipeline callMediaPipeline : callMediaPipelines) {
                    String masterTitle = callMediaPipeline.getMasterTitle();
                    String masterName = callMediaPipeline.getMasterUserSession().getName();
                    listMasterResponse = new JsonObject();
                    listMasterResponse.addProperty("id", "listMasterResponse");
                    listMasterResponse.addProperty("masterName", masterName);
                    listMasterResponse.addProperty("masterTitle", masterTitle);
//                    initialUserSession.sendMessage(listMasterResponse);
//                    MessageSender.getInstance().writeMessage(channel, new SCEChat(listMasterResponse.toString()));
                    System.out.println("viewLive: " + initialUserSession.getName());
                }
            }
        }

        guestBySessionId.put(initialUserSession.getChannel(), initialUserSession);

    }

}
