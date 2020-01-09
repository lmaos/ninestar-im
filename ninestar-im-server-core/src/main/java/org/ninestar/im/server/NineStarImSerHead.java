package org.ninestar.im.server;

import org.ninestar.im.msgcoder.MsgPackHead;

public interface NineStarImSerHead extends MsgPackHead{
	
	public int getState();
	
	public String getMsg();
	
	
}
