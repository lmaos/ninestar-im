package org.ninestar.im.client.handle_v0;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.msgcoder.MsgPackage;

public class NineStarImMsgCliV0Response implements NineStarImCliResponse {
	private long msgPackId;
	private NineStarImMsgCliV0RespHead head;
	private byte[] body;
	private MsgPackage msgPackage;
	private ByteArrayInputStream in;

	NineStarImMsgCliV0Response(long msgPackId, NineStarImMsgCliV0RespHead head, byte[] body, MsgPackage msgPackage) {
		this.msgPackId = msgPackId;
		this.head = head;
		this.body = body;
		this.msgPackage = msgPackage;
		this.in = new ByteArrayInputStream(body);
	}

	public byte[] getBodyBytes() {
		return body;
	}

	@Override
	public NineStarImMsgCliV0RespHead getHead() {
		return head;
	}

	@Override
	public byte[] getBody() {

		return body;
	}

	public long getMsgPackId() {
		return msgPackId;
	}

	public MsgPackage getMsgPackage() {
		return msgPackage;
	}

	@Override
	public InputStream in() {

		return in;
	}

	@Override
	public void resetInput() {
		in = new ByteArrayInputStream(body);
	}

	@Override
	public void resetReadIndex() {
		in.reset();

	}

	@Override
	public void markReadIndex() {
		in.markSupported();
	}

	public int getState() {
		return head.getState();
	}

	public String getMsg() {
		return head.getMsg();
	}

	public String getUri() {
		return head.getUri();
	}
}
