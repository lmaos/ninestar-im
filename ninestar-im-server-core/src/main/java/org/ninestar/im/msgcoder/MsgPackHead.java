package org.ninestar.im.msgcoder;

public interface MsgPackHead {
	/**
	 * 获得头数据
	 */
	public Object get(Object key);
	/**
	 * 设置头数据
	 */
	public Object put(String key, Object value);
	/**
	 * 资源地址（请求或应答的资源地址）
	 */
	public String getUri();
	/**
	 * 内容类型 
	 */
	public String getContentType();
	/**
	 * 编码默认UTF-8 
	 */
	public String getCharsetName();
	
}
