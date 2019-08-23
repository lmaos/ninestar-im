package org.ninestar.im.server.handler_v0;

import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.server.NineStarImSerHead;
import org.ninestar.im.server.NineStarImSerRequest;

public class NineStarImMsgSerV0Request implements NineStarImSerRequest {
	private long msgPackId;
	private NineStarImMsgSerV0ReqHead head;
	private byte[] body;
	private MsgPackage msgPackage;

	
	NineStarImMsgSerV0Request(long msgPackId, NineStarImMsgSerV0ReqHead head, byte[] body,
			MsgPackage msgPackage) {
		this.msgPackId = msgPackId;
		this.head = head;
		this.body = body;
		this.msgPackage = msgPackage;
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

}
