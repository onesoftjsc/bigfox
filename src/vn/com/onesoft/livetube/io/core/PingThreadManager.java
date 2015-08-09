package vn.com.onesoft.livetube.io.core;

import vn.com.onesoft.livetube.io.message.cs.CSPing;

public class PingThreadManager {

	private long idConnection = 0;

	private static PingThreadManager _instance;

	public static PingThreadManager getInstance() {
		if (_instance == null) {
			_instance = new PingThreadManager();
			_instance.start();
		}
		return _instance;
	}

	private void start() {
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					ConnectionManager.getInstance().write(new CSPing());

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				}
			}
		}).start();
	}

}
