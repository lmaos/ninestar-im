package org.ninestar.im.msgcoder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface BodyOutput {
	OutputStream out();

	void clearBody();

	default void putBytes(byte[] v) {
		try {
			out().write(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putByte(byte v) {
		try {
			out().write(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putShort(short v) {
		try {
			out().write(ByteBuffer.allocate(2).putShort(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putInt(int v) {
		try {
			out().write(ByteBuffer.allocate(4).putInt(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putLong(long v) {
		try {
			out().write(ByteBuffer.allocate(8).putLong(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putFloat(long v) {
		try {
			out().write(ByteBuffer.allocate(4).putFloat(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putDouble(long v) {
		try {
			out().write(ByteBuffer.allocate(8).putDouble(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putBoolean(boolean v) {
		try {
			out().write(ByteBuffer.allocate(1).putDouble(v ? 1 : 0).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	default void putChar(char v) {
		try {
			out().write(ByteBuffer.allocate(2).putChar(v).array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
