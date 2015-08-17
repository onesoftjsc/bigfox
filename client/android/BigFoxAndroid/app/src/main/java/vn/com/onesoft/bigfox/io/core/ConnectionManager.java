package vn.com.onesoft.bigfox.io.core;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import vn.com.onesoft.bigfox.MainActivity;
import vn.com.onesoft.bigfox.io.message.base.BaseMessage;
import vn.com.onesoft.bigfox.io.message.base.MessageBuffer;
import vn.com.onesoft.bigfox.io.message.annotations.Message;
import vn.com.onesoft.bigfox.io.message.core.sc.SCInitSession;
import vn.com.onesoft.bigfox.io.message.core.sc.SCValidationCode;

import android.util.SparseArray;

public class ConnectionManager implements Runnable {

	Thread t = null;
	Socket clientSocket;
	DataOutputStream outS;
	DataInputStream inS;

	MessageBuffer buf = new MessageBuffer(65000);

	public int validationCode;
	public int curSSequence;
	public int curMSequence;
	private int mSequenceFromServer;

	SparseArray<BaseMessage> queueOutMessage;
	public long lastPingReceivedTime;
	public String sessionId = "";

	private static ConnectionManager _instance = null;

	private boolean isOnline = false;
	public boolean isValidationReceived = false;
	private ISessionControl sessionControl = new DefaultSessionControl();

	public static ConnectionManager getInstance() {
		if (_instance == null) {
			_instance = new ConnectionManager();
			_instance.queueOutMessage = new SparseArray<BaseMessage>();
			_instance.start();
		}
		return _instance;
	}

	private void start() {
		t = new Thread(this);
		t.start();
	}

	public void run() {

		byte[] buffer = new byte[10000];

		while (true) {
			if (isOnline && (System.currentTimeMillis() - lastPingReceivedTime) < 20000) {
				try {
					int readSize = inS.read(buffer);
					if (readSize >= 0) {
						buf.add(buffer, readSize);
					} else {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				reConnect();
			}
		}

	}

	private void reConnect() {
		try {
			buf.Reset();
			String server = BigFoxContext.SERVER;
			clientSocket = new Socket(server, BigFoxContext.PORT);
			outS = new DataOutputStream(clientSocket.getOutputStream());
			inS = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			isOnline = true;
			lastPingReceivedTime = System.currentTimeMillis();

		} catch (IOException ex) {
			isOnline = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean write(BaseMessage mOut) {

		Message m = mOut.getClass().getAnnotation(Message.class);

		BFLogger.getInstance().debug(mOut);
		curMSequence++;
		mOut.setmSequence(curMSequence);
		if (!m.isCore())
		    queueOutMessage.put(curMSequence, mOut);

		boolean result = false;
		try {
			flush(mOut);
			result = true;

		} catch (Exception ex) {
			isOnline = false;
		}
		return result;
	}

	private void flush(BaseMessage mOut) throws IOException {

		byte[] data = mOut.toBytes();
		for (int i = 4; i < data.length; i++) {
			data[i] = (byte) ((data[i] ^ validationCode) & 0x00ff);
		}
		outS.write(data);
		outS.flush();

	}

	public void onMessage(final BaseMessage mIn) {
		if (!(mIn instanceof SCValidationCode) && !(mIn instanceof SCInitSession)) {
			if (mIn.getsSequence() <= curSSequence) {
				return; // Không thực thi bản tin đã thực thi rồi
			}
			curSSequence = mIn.getsSequence();
			for (int i = mSequenceFromServer; i <= mIn.getmSequence(); i++) {
				queueOutMessage.remove(i);
			}
			mSequenceFromServer = mIn.getmSequence();
		}

		MainActivity.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mIn.execute();
			}
		});

	}

	public void onContinueOldSession() {
		// TODO Auto-generated method stub
		sessionControl.onReconnectedSession();
	}

	public void onStartNewSession() {
		// TODO Auto-generated method stub
        sessionControl.onStartSession();
	}

	public void resendOldMessages() {
		// TODO Auto-generated method stub
		try {
			// Resend messages
			for (int i = mSequenceFromServer + 1; i < curMSequence; i++) {
				BaseMessage bm = queueOutMessage.get(i);
				if (bm != null)
				    flush(bm);
			}
		} catch (Exception ex) {

		}
	}

	public ISessionControl getSessionControl() {
		return sessionControl;
	}

	public void setSessionControl(ISessionControl sessionControl) {
		this.sessionControl = sessionControl;
	}
}
