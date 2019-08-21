package org.ninestar.im.demo;

import java.util.Arrays;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.msgcoder.MsgUtils;
import org.ninestar.im.msgcoder.exception.VersionErrorException;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import io.netty.buffer.Unpooled;

public class MsgPackDemo {
	static {
		Logger log = (Logger) LoggerFactory.getLogger("root");
		log.setLevel(Level.OFF);
	}
	
	public static void main(String[] args) throws VersionErrorException {
		short version = 1000;
		MsgPackage msgPackage = null;
//		msgPackage = MsgPackage.createHeartbeatReqPack((short) 1000);
//		msgPackage = MsgPackage.createHeartbeatRespPack(version);
		msgPackage = MsgPackage.createMsgReqPack(version, 1, "{}".getBytes(), "123".getBytes());
		byte[] bs = msgPackage.toBytes();
		System.out.println(Arrays.toString(bs));
		MsgPackage ms = MsgUtils.readMsgPackage(version, Unpooled.wrappedBuffer(bs));
		System.out.println(JSON.toJSONString(ms));
	}
}
	