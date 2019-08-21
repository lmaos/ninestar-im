package org.ninestar.im.msgcoder;

import java.nio.ByteBuffer;

public class MsgPackage {

	public final static byte HEARTBEAT_REQ_PACK = 0; // 心跳请求包
	public final static byte HEARTBEAT_RESP_PACK = (byte) 255; // 心跳应答包
	public byte[] ZERO_BYTES = {};

	private short version; // 2 消息版本，也是起始位置分割线
	private byte signs; // 1 标记消息取值 0 心跳 ,255 心跳应答。 byte占 8位，将此byte 分割成 高低位，高位 0000 低位0000.
						// 高位保存头长字段占位，低位保存体长字段占位。
	private long msgId; // 8 由客户端请求过来的消息包ID，必须穿，应答时原样返回
	private long timestamp; // 8 此参数只有当 signs == 0xFF （255）时有效
	private int headLength;// (signs >> 4) & 0xFF
	private int bodyLength; // signs & 0xFF
	private byte[] headBytes = ZERO_BYTES;
	private byte[] bodyBytes = ZERO_BYTES;

	/**
	 * 创建一个心跳数据包
	 * 
	 * @param version
	 */
	public static MsgPackage createHeartbeatRespPack(short version) {
		MsgPackage msgpack = new MsgPackage(version);
		msgpack.signs = HEARTBEAT_RESP_PACK;
		msgpack.timestamp = System.currentTimeMillis();
		return msgpack;
	}

	/**
	 * 创建一个心跳数据包
	 * 
	 * @param version
	 */
	public static MsgPackage createHeartbeatRespPack(short version, long timestamp) {
		MsgPackage msgpack = new MsgPackage(version);
		msgpack.signs = HEARTBEAT_RESP_PACK;
		msgpack.timestamp = timestamp;
		return msgpack;
	}

	/**
	 * 创建一个心跳数据包 请求包
	 * 
	 * @param version
	 */
	public static MsgPackage createHeartbeatReqPack(short version) {
		MsgPackage msgpack = new MsgPackage(version);
		msgpack.signs = HEARTBEAT_REQ_PACK;
		return msgpack;
	}

	/**
	 * 创建一个消息包
	 * 
	 * @param version
	 */
	public static MsgPackage createMsgReqPack(short version, long msgId, byte[] headBytes, byte[] bodyBytes) {
		return new MsgPackage(version, msgId, headBytes, bodyBytes);
	}

	private MsgPackage(short version) {
		this.version = version;
	}

	/**
	 * 创建一个基础数据包
	 * 
	 * @param version
	 * @param msgId
	 * @param headBytes
	 * @param bodyBytes
	 */
	private MsgPackage(short version, long msgId, byte[] headBytes, byte[] bodyBytes) {
		this.version = version;
		this.msgId = msgId;
		this.headBytes = headBytes == null ? ZERO_BYTES : headBytes;
		this.bodyBytes = bodyBytes == null ? ZERO_BYTES : bodyBytes;
		this.headLength = headBytes.length;
		this.bodyLength = bodyBytes.length;
		byte b1 = lenToSign(headLength);
		byte b2 = lenToSign(bodyLength);
		signs = (byte) ((b1 << 4) | b2);
	}

	/**
	 * 获得顶部的数据包
	 * 
	 * @return
	 */
	public byte[] getTopBytes() {
		if (signs == HEARTBEAT_REQ_PACK) { // 心跳包
			return ByteBuffer.allocate(3).putShort(version).put(signs).array();
		}
		if (signs == HEARTBEAT_RESP_PACK) {
			long time = timestamp;
			return ByteBuffer.allocate(19).putShort(version).put(signs).putLong(time).array();
		}
		int b1 = lenToSign(headLength);
		int b2 = lenToSign(bodyLength);

		int hbsize = b1 + b2;
		int totalsize = 1 + 2 + 8 + hbsize;
		ByteBuffer buf = ByteBuffer.allocate(totalsize);
		buf.putShort(version);
		buf.put(signs);
		buf.putLong(msgId);
		putByteBuffer(b1, headLength, buf);
		putByteBuffer(b2, bodyLength, buf);
		return buf.array();

	}

	private void putByteBuffer(int bm, int val, ByteBuffer buf) {
		if (bm == 1) {
			buf.put((byte) val);
		} else if (bm == 2) {
			buf.putShort((short) val);
		} else if (bm == 4) {
			buf.putInt(val);
		} else {

		}
	}

	private byte lenToSign(int len) {
		if (headLength == 0) {
			return 0;
		} else if (headLength <= Byte.MAX_VALUE) {
			return 1;
		} else if (headLength <= Short.MAX_VALUE) {
			return 2;
		} else if (headLength <= Integer.MAX_VALUE) {
			return 4;
		} else {
			throw new RuntimeException("不支持当前长度大小");
		}
	}

	/**
	 * 获得头数据
	 * 
	 * @return
	 */
	public byte[] getHeadBytes() {
		return headBytes;
	}

	/**
	 * 获得体数据
	 * 
	 * @return
	 */
	public byte[] getBodyBytes() {
		return bodyBytes;
	}

	public byte[] toBytes() {
		byte[] topsBytes = getTopBytes();
		int totalSize = topsBytes.length + headLength + bodyLength;
		return ByteBuffer.allocate(totalSize).put(topsBytes).put(headBytes).put(bodyBytes).array();
	}

	/**
	 * 是否是心跳请求包
	 * 
	 * @return
	 */
	public boolean isHeartbeatReqPack() {
		return signs == HEARTBEAT_REQ_PACK;
	}

	/**
	 * 是否是心跳应答包
	 * 
	 * @return
	 */
	public boolean isHeartbeatRespPack() {
		return signs == HEARTBEAT_RESP_PACK;
	}

	public short getVersion() {
		return version;
	}

	public byte getSigns() {
		return signs;
	}

	public long getMsgId() {
		return msgId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getHeadLength() {
		return headLength;
	}

	public int getBodyLength() {
		return bodyLength;
	}

}
