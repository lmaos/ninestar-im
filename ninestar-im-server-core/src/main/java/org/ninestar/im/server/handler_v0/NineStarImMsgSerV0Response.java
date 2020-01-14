package org.ninestar.im.server.handler_v0;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerResponse;

import com.alibaba.fastjson.JSON;

public class NineStarImMsgSerV0Response implements NineStarImSerResponse {
	private long msgPackId;
	private NineStarImMsgSerV0RespHead head;
	private byte[] body;
	private MsgPackage msgPackage;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();

	protected NineStarImMsgSerV0Response(long msgPackId, NineStarImMsgSerV0RespHead head) {
		this.msgPackId = msgPackId;
		this.head = head;
	}
	protected NineStarImMsgSerV0Response(long msgPackId) {
		this.msgPackId = msgPackId;
		this.head = new NineStarImMsgSerV0RespHead();
	}

	public byte[] getBodyBytes() {
		return body;
	}

	@Override
	public NineStarImMsgSerV0RespHead getHead() {
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

	public void setBody(String text, String charsetName) {
		this.setBody(text.getBytes(Charset.forName(charsetName)));
		this.head.setCharsetName(charsetName);
	}

	public void setBody(String text) {
		setBody(text, "UTF-8");
	}

	public long getMsgPackId() {
		return msgPackId;
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

		return MsgPackage.createMsgReqPack((short) 0, msgPackId, headBytes, bodyBytes);
	}

	public void setState(int state) {
		this.head.setState(state);
	}

	public void setMsg(String msg) {
		this.head.setMsg(msg);
	}
}
