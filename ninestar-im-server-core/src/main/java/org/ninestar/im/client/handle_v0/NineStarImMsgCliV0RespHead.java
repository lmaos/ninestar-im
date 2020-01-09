package org.ninestar.im.client.handle_v0;

import org.ninestar.im.client.NineStarImCliHead;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class NineStarImMsgCliV0RespHead implements NineStarImCliHead {

	private int state;
	private String uri;
	private String msg;
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

	public int getState() {
		return this.state;
	}

	public String getMsg() {
		return this.msg;
	}

	void setState(int state) {
		this.state = state;
	}

	void setUri(String uri) {
		this.uri = uri;
	}

	void setMsg(String msg) {
		this.msg = msg;
	}

	void setContentType(String contentType) {
		this.contentType = contentType;
	}

	void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	void setHeadData(JSONObject headData) {
		this.headData = headData;
	}

	@Override
	public Object get(Object key) {
		return headData.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return headData.put(key, value);
	}
	
	public String toString() {
		return JSON.toJSONString(this);
	}
}
