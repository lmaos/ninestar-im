package org.ninestar.im.msgcoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface BodyInput {
	InputStream in();

	default Byte readByte() {
		try {
			InputStream in = in();
			if (in.available() == 0) {
				return null;
			}
			return (byte) in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default int readBytes(byte[] bs) {
		try {
			InputStream in = in();
			return in.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	default Short readShort() {
		try {
			InputStream in = in();
			if (in.available() < 2) {
				return null;
			}
			byte[] val = new byte[2];
			in.read(val);
			return ByteBuffer.wrap(val).getShort();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Integer readInt() {
		try {
			InputStream in = in();
			if (in.available() < 4) {
				return null;
			}
			byte[] val = new byte[4];
			in.read(val);
			return ByteBuffer.wrap(val).getInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Long readLong() {
		try {
			InputStream in = in();
			if (in.available() < 8) {
				return null;
			}
			byte[] val = new byte[8];
			in.read(val);
			return ByteBuffer.wrap(val).getLong();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Float readFloat() {
		try {
			InputStream in = in();
			if (in.available() < 4) {
				return null;
			}
			byte[] val = new byte[4];
			in.read(val);
			return ByteBuffer.wrap(val).getFloat();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Double readDouble() {
		try {
			InputStream in = in();
			if (in.available() < 8) {
				return null;
			}
			byte[] val = new byte[8];
			in.read(val);
			return ByteBuffer.wrap(val).getDouble();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Boolean readBoolean() {
		try {
			InputStream in = in();
			if (in.available() == 0) {
				return null;
			}

			int val = in.read();
			return val == 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Character readChar() {
		try {
			InputStream in = in();
			if (in.available() < 2) {
				return null;
			}
			byte[] val = new byte[2];
			in.read(val);
			return ByteBuffer.wrap(val).getChar();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	default Integer canReadLength() {
		try {
			return in().available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void resetInput();

	void resetReadIndex();

	void markReadIndex();
}
