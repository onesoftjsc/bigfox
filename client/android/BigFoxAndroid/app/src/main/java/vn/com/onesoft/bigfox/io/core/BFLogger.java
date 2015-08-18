package vn.com.onesoft.bigfox.io.core;

import android.util.Log;

import vn.com.onesoft.bigfox.io.message.BaseMessage;

/**
 * Created by phamquan on 8/16/15.
 */
public class BFLogger {private static BFLogger _instance = null;

    public static BFLogger getInstance() {
        if (_instance == null) {
            _instance = new BFLogger();
        }
        return _instance;
    }

    public void debug(Object obj){
        if (obj instanceof BaseMessage &&  ((BaseMessage) obj).isCore())
            return;
        Log.d("BigFox", obj.toString());
    }

    public void info(Object obj) {
        if (obj instanceof BaseMessage &&  ((BaseMessage) obj).isCore())
            return;
        Log.i("BigFox", obj.toString());
    }

    public void error(Object obj, Throwable t) {
        Log.e("BigFox", obj.toString(), t);
    }

    public void error(Object obj) {
        Log.e("BigFox", obj.toString());
    }
}