package org.ninestar.im.server;

import org.ninestar.im.msgcoder.BodyOutput;
import org.ninestar.im.msgcoder.MsgPackage;

public interface NineStarImSerResponse extends BodyOutput {

	NineStarImSerHead getHead();

	void setBody(byte[] body);

	MsgPackage toMsgPackage();

}
