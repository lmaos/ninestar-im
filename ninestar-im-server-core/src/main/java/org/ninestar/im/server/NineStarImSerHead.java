package org.ninestar.im.server;

import org.ninestar.im.msgcoder.MsgPackHead;

public interface NineStarImSerHead extends MsgPackHead{
	
	public Object get(Object key);

	public Object put(String key, Object value);
	
	public String getUri();

	public String getContentType();

	public String getCharsetName();

	public String getSource();
	
	
}