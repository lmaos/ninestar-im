package org.ninestar.im.msgcoder;

import org.ninestar.im.msgcoder.exception.VersionErrorException;

import io.netty.buffer.ByteBuf;

public class MsgUtils {
	/**
	 * 读取版本号
	 * 
	 * @param byteBuf
	 * @return
	 */
	public static Short readVersion(ByteBuf byteBuf) {
		byteBuf.markReaderIndex();
		if (byteBuf.readableBytes() < 3) {
			return null;
		}
		short version = byteBuf.readShort();
		byteBuf.resetReaderIndex();
		return version;
	}

	/**
	 * 获取数据包，并判断是否为此版本，如果不是本版本的数据则发生异常
	 * 
	 * @param ver
	 * @param byteBuf
	 * @return
	 * @throws VersionErrorException
	 */
	public static MsgPackage readMsgPackage(short ver, ByteBuf byteBuf) throws VersionErrorException {
		if (byteBuf.readableBytes() < 3) {
			return null;
		}
		byteBuf.markReaderIndex();
		short version = byteBuf.readShort();
		if (version != ver) {
			byteBuf.resetReaderIndex();
			throw new VersionErrorException(version, ver);
		}
		byte signs = byteBuf.readByte();
		// 心跳请求包
		if (signs == MsgPackage.HEARTBEAT_REQ_PACK) {
			// 心跳请求包是最短的消息报，到此就结束啦
			return MsgPackage.createHeartbeatReqPack(version);
		}
		// 心跳应答包
		if (signs == MsgPackage.HEARTBEAT_RESP_PACK) {
			if (byteBuf.readableBytes() < 8) {
				byteBuf.resetReaderIndex();
				return null;
			}
			// 心跳应答包 是携带时间戳的
			long timestamp = byteBuf.readLong();
			return MsgPackage.createHeartbeatRespPack(version, timestamp);
		}
		byte b1 = (byte) ((signs >> 4) & 0xF);
		byte b2 = (byte) (signs & 0xF);
		int lenReadSize = b1 + b2 + 8;
		if (byteBuf.readableBytes() < lenReadSize) {
			byteBuf.resetReaderIndex();
			return null;
		}
		long msgId = byteBuf.readLong(); // 消息ID
		int headLength = readLength(byteBuf, b1); // 头长度
		int bodyLength = readLength(byteBuf, b1); // 体长度
		if (byteBuf.readableBytes() < headLength + bodyLength) {
			byteBuf.resetReaderIndex();
			return null;
		}
		byte[] headBytes = new byte[headLength];
		byte[] bodyBytes = new byte[bodyLength];
		if (headLength > 0) {
			byteBuf.readBytes(headBytes); // 读取头数据
		}
		if (bodyLength > 0) {
			byteBuf.readBytes(bodyBytes); // 读取体数据
		}
		// 创建消息包对象
		return MsgPackage.createMsgReqPack(version, msgId, headBytes, bodyBytes);
	}

	private static int readLength(ByteBuf byteBuf, byte bm) {
		if (bm == 1) {
			return byteBuf.readByte();
		} else if (bm == 2) {
			return byteBuf.readShort();
		} else if (bm == 4) {
			return byteBuf.readInt();
		} else {
			return 0;
		}
	}

	public static void putByteBuf(MsgPackage msgPackage, ByteBuf byteBuf) {
		byte[] topBytes = msgPackage.getTopBytes();
		byte[] headBytes = msgPackage.getHeadBytes();
		byte[] bodyBytes = msgPackage.getBodyBytes();
		byteBuf.writeBytes(topBytes);
		byteBuf.writeBytes(headBytes);
		byteBuf.writeBytes(bodyBytes);

	}
}
