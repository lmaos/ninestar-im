package org.ninestar.im.server;

import org.ninestar.im.msgcoder.BodyOutput;
import org.ninestar.im.msgcoder.MsgPackage;

public interface NineStarImSerResponse extends BodyOutput {

	NineStarImSerHead getHead();

	void setBody(byte[] body);

	MsgPackage toMsgPackage();

	/**
	 * 设置应答状态
	 */
	void setState(int state);

	/**
	 * 设置应答提示内容
	 */
	void setMsg(String msg);

}
