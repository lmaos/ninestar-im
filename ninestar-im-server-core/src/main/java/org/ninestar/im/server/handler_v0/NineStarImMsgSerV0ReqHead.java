package org.ninestar.im.server.handler_v0;

import org.ninestar.im.server.NineStarImSerHead;

import com.alibaba.fastjson.JSONObject;

public class NineStarImMsgSerV0ReqHead implements NineStarImSerHead {

	private int state;
	private String msg;
	private String uri;
	private String contentType = "TEXT";
	private String charsetName = "UTF-8";
	private JSONObject headData = new JSONObject();

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

	void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	void setContentType(String contentType) {
		this.contentType = contentType;
	}

	void setHeadData(JSONObject headData) {
		this.headData = headData;
	}

	void setMsg(String msg) {
		this.msg = msg;
	}

	void setState(int state) {
		this.state = state;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
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
