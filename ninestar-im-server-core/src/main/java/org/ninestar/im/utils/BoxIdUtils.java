package org.ninestar.im.utils;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;

public class BoxIdUtils {
	
	public final static String BOXID_SPLIT = "##";

	public static String encodeBoxId(String boxId) {
		byte[] bs = ("{" + boxId + "}").getBytes(Charset.forName("UTF-8"));
		for (int i = 0; i < bs.length / 2; i++) {
			int endIndex = bs.length - i - 1;
			byte tmp = bs[i];
			bs[i] = bs[endIndex];
			bs[endIndex] = tmp;
		}
		return Base64.encodeBase64String(bs);
	}
	
	public static String decodeBoxId(String boxId) {
		byte[] bs = Base64.decodeBase64(boxId);
		for (int i = 0; i < bs.length / 2; i++) {
			int endIndex = bs.length - i - 1;
			byte tmp = bs[i];
			bs[i] = bs[endIndex];
			bs[endIndex] = tmp;
		}
		boxId = new String(bs, Charset.forName("UTF-8"));
		if (boxId.startsWith("{") && boxId.endsWith("}")) {
			return boxId.substring(1, boxId.length() - 1);
		}
		return boxId;
	}
	
	
	
	public static String getBoxId(String serverId, long clientId) {
		return serverId + BOXID_SPLIT + clientId;
	}

	public static String getServerId(String boxId) {
		return getServerId(boxId, null);
	}

	public static String getServerId(String boxId, String def) {
		int index = boxId.lastIndexOf(BOXID_SPLIT);
		if (index == -1) {
			return def;
		}
		return boxId.substring(0, index);
	}
	
	public static void main(String[] args) {
//		System.out.println(JSON.toJSONString(IPAddress.ip4s));
//		System.out.println(encodeBoxId(getBoxId("adasd", 1)));
//		System.out.println(decodeBoxId("fTEjI2RzYWRhew=="));
	}
	
}
