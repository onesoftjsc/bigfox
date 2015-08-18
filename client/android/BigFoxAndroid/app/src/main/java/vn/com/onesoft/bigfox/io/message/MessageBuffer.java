package vn.com.onesoft.bigfox.io.message;

import vn.com.onesoft.bigfox.io.core.ByteUtils;
import vn.com.onesoft.bigfox.io.core.ConnectionManager;
import vn.com.onesoft.bigfox.io.message.core.tags.CoreTags;

public class MessageBuffer {

	byte[] buff;
	int size = 0;
	int beginIndex = 0;
	int realDataLength = 0;
	boolean overLoad = false;

	public MessageBuffer(int size) {
		this.size = size;
		buff = new byte[size];
	}

	public void Reset() {
		beginIndex = 0;
		realDataLength = 0;
		overLoad = false;
	}

	public void add(byte[] bytes, int len) {
		if (len == 0) {
			return;
		}
		ConnectionManager.getInstance().lastPingReceivedTime = System
				.currentTimeMillis();

		if (realDataLength + len > size) {
			overLoad = true;
		} else {
			for (int i = 0; i < len; i++) {
				buff[(beginIndex + realDataLength + i) % size] = bytes[i];
			}
			realDataLength += len;
		}
		scan();
	}

	public void add(byte[] bytes) {
		add(bytes, bytes.length);
	}

	void scan() {
		while (true) {
			if (realDataLength >= 4) {
				int len = ByteUtils.getInt(buff[beginIndex],
						buff[beginIndex + 1], buff[beginIndex + 2],
						buff[beginIndex + 3]);
				int tag = ByteUtils.getInt(buff[beginIndex + 4],
						buff[beginIndex + 5], buff[beginIndex + 6],
						buff[beginIndex + 7]);
				if (realDataLength >= len) { // lấy đủ dữ liệu
					byte[] data = new byte[len];
					for (int i = 0; i < len; i++) {

						// Giai ma
						data[i] = buff[(beginIndex + i) % size];

						if (i >= 4 && tag != CoreTags.SC_VALIDATION_CODE) {
							data[i] = (byte) ((data[i] ^ ConnectionManager
									.getInstance().validationCode) & 0x00ff);
						}

					}

					MessageExecutor.getInstance().execute(data);

					beginIndex = (beginIndex + len) % size;
					realDataLength = realDataLength - len;

				} else {
					break;
				}
			} else {
				break;
			}
		}
	}
}
