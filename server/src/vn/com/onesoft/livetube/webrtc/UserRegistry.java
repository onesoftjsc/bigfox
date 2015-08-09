package vn.com.onesoft.livetube.webrtc;

import io.netty.channel.Channel;
import java.util.concurrent.ConcurrentHashMap;
//import javax.websocket.Session;

/**
 * Map of users registered in the system. This class has a concurrent hash map
 * to store users, using its name as key in the map.
 *
 * @author huongns
 */
public class UserRegistry {

    private static ConcurrentHashMap<String, UserSession> usersByName = new ConcurrentHashMap<String, UserSession>();
    private static ConcurrentHashMap<Channel, UserSession> usersBySessionId = new ConcurrentHashMap<Channel, UserSession>();

    public void register(UserSession user) {
        usersByName.put(user.getName(), user);
        usersBySessionId.put(user.getChannel(), user);
    }

    public UserSession getByName(String name) {
        return usersByName.get(name);
    }

    public UserSession getByChannel(Channel channel) {
        return usersBySessionId.get(channel);
    }

    public boolean exists(String name) {
        return usersByName.keySet().contains(name);
    }

    public UserSession removeBySession(Channel channel) {
        final UserSession user = getByChannel(channel);
        if (user != null) {
            usersByName.remove(user.getName());
            usersBySessionId.remove(channel);
        }
        return user;
    }

    public ConcurrentHashMap<String, UserSession> getUsersByName() {
        return usersByName;
    }

    public ConcurrentHashMap<Channel, UserSession> getUsersBySessionId() {
        return usersBySessionId;
    }

}
