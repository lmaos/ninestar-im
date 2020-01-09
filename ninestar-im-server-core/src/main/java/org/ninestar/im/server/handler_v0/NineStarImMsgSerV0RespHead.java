package org.ninestar.im.server.handler_v0;

import org.ninestar.im.server.NineStarImSerHead;

import com.alibaba.fastjson.JSONObject;

/**
 * 服务应答头
 * 
 */
public class NineStarImMsgSerV0RespHead implements NineStarImSerHead {
	//private static final long serialVersionUID = 1L;

	NineStarImMsgSerV0RespHead(NineStarImSerHead head) {
		this.setUri(head.getUri());
		this.setContentType(head.getContentType());
		this.setCharsetName(head.getCharsetName());
		this.setState(0);
	}

	private int state;
	private String uri;
	private String msg;
	private String contentType = "TEXT";
	private String charsetName = "UTF-8";
	private String boxId;
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
		return charsetName;
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
	
	void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	
	public String getBoxId() {
		return boxId;
	}
	
}
