package vn.com.onesoft.livetube;

import android.util.Log;

public class Logger {
	public static final boolean ENABLE = true;

	public static void log(Object obj) {
		if (ENABLE) {
			Log.d("BigFox", obj != null ? obj.toString() : "object null");
		}
	}
}
