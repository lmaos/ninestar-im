package org.ninestar.im.server.handler_v0;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerRequest;

public class NineStarImMsgSerV0Request implements NineStarImSerRequest {
	private long msgPackId;
	private NineStarImMsgSerV0ReqHead head;
	private byte[] body;
	private MsgPackage msgPackage;
	private ByteArrayInputStream in;

	NineStarImMsgSerV0Request(long msgPackId, NineStarImMsgSerV0ReqHead head, byte[] body, MsgPackage msgPackage) {
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
	public NineStarImMsgSerV0ReqHead getHead() {
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

}
