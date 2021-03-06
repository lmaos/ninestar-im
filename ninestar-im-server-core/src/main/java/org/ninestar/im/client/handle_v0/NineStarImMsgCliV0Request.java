package org.ninestar.im.client.handle_v0;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.ninestar.im.client.ConstMPID;
import org.ninestar.im.client.NineStarImCliRequest;
import org.ninestar.im.msgcoder.MsgPackage;

import com.alibaba.fastjson.JSON;

public class NineStarImMsgCliV0Request implements NineStarImCliRequest{
//	private long msgPackId = ConstMPID.nextId();
	private NineStarImMsgCliV0ReqHead head;
	private byte[] body;
	private MsgPackage msgPackage;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	public NineStarImMsgCliV0Request(String uri) {
		this.head = new NineStarImMsgCliV0ReqHead(uri);
		
	}
	
	public NineStarImMsgCliV0Request setHeaderValue(String key, String value) {
		this.head.put(key, value);
		return this;
	}

	public byte[] getBodyBytes() {
		return body;
	}

	@Override
	public NineStarImMsgCliV0ReqHead getHead() {
		return head;
	}

	@Override
	public void setBody(byte[] body) {
		out = new ByteArrayOutputStream(body.length);
		try {
			out.write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MsgPackage getMsgPackage() {
		return msgPackage;
	}

	@Override
	public OutputStream out() {

		return out;
	}

	@Override
	public void clearBody() {
		out = new ByteArrayOutputStream();

	}

	@Override
	public MsgPackage toMsgPackage() {
		String headJson = JSON.toJSONString(head);
		byte[] headBytes = headJson.getBytes(Charset.forName("UTF-8"));
		byte[] bodyBytes = out.toByteArray();
		long msgPackId = ConstMPID.nextId();
		return MsgPackage.createMsgReqPack((short) 0, msgPackId, headBytes, bodyBytes);
	}

}
