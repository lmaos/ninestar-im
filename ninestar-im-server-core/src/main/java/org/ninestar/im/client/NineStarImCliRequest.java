package org.ninestar.im.client;

import org.ninestar.im.msgcoder.BodyOutput;
import org.ninestar.im.msgcoder.MsgPackage;

public interface NineStarImCliRequest extends BodyOutput {

	NineStarImCliHead getHead();

	MsgPackage toMsgPackage();

	void setBody(byte[] body);

}
