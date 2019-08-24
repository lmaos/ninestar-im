package org.ninestar.im.msgcoder;

public interface MsgPackHead {
	
	public Object get(Object key);

	public Object put(String key, Object value);

	public String getUri();

	public String getContentType();

	public String getCharsetName();

	public String getSource();
	
}
