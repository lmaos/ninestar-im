package org.ninestar.im.server.handler_v1;

import org.ninestar.im.client.ConstMPID;
import org.ninestar.im.msgcoder.MsgPackage;

public class NineStarImMsgSerV1Request {
	private NineStarImMsgSerV1ReqHead head;
	private MsgPackage body;

	public NineStarImMsgSerV1Request(String sourceId) {
		head = new NineStarImMsgSerV1ReqHead(sourceId);
	}

	public void setBody(MsgPackage body) {
		this.body = body;
	}

	public NineStarImMsgSerV1ReqHead getHead() {
		return head;
	}

	public MsgPackage toMsgPackage() {
		long msgId = ConstMPID.nextId();
		byte[] bodyBytes = body.toBytes();
		byte[] headBytes = head.toBytes();
		return MsgPackage.createMsgReqPack((short) 1, msgId, headBytes, bodyBytes);
	}
}
