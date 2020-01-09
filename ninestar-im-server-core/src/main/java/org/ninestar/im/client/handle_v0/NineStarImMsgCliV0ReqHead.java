package org.ninestar.im.client.handle_v0;

import org.ninestar.im.client.NineStarImCliHead;
import org.ninestar.im.msgcoder.ImContentType;

import com.alibaba.fastjson.JSONObject;

/**
 * 客户端请求答头
 * 
 */
public class NineStarImMsgCliV0ReqHead implements NineStarImCliHead {

	// private static final long serialVersionUID = 1L;
	private int state;
	private String uri;
	private String msg;
	private String contentType = "TEXT";
	private String charsetName = "UTF-8";
	private JSONObject headData = new JSONObject();
	
	NineStarImMsgCliV0ReqHead(String uri) {
		this.setUri(uri);
		this.setContentType(ImContentType.TEXT);
		this.setCharsetName("UTF-8");
		this.setState(0);
	}

	public JSONObject getHeadData() {
		return headData;
	}

	public <T> T getJavaBean(Class<T> type) {
		return headData.toJavaObject(type);
	}

	public String getUri() {
		return this.uri;
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getCharsetName() {
		return this.charsetName;
	}

	void setUri(String uri) {
		this.uri = uri;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public int getState() {
		return state;
	}
	
	public String getMsg() {
		return msg;
	}
	
	@Override
	public Object get(Object key) {
		return headData.get(key);
	}
	
	@Override
	public Object put(String key, Object value) {
		return headData.put(key, value);
	}
}
