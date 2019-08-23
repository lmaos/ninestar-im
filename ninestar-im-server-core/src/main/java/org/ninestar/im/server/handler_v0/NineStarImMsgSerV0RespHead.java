package org.ninestar.im.server.handler_v0;

import org.ninestar.im.server.NineStarImSerHead;

import com.alibaba.fastjson.JSONObject;

/**
 * 服务应答头
 * 
 */
public class NineStarImMsgSerV0RespHead extends JSONObject implements NineStarImSerHead {

	private static final long serialVersionUID = 1L;

	NineStarImMsgSerV0RespHead(NineStarImSerHead head) {
		this.setUri(head.getUri());
		this.setContentType(head.getContentType());
		this.setCharsetName(head.getCharsetName());
		this.setState(0);
	}

	public JSONObject getHeadData() {
		return this;
	}

	public <T> T getJavaBean(Class<T> type) {
		return this.toJavaObject(type);
	}

	public String getUri() {
		return this.getString("uri");
	}

	public String getContentType() {
		return this.getString("contentType");
	}

	public String getCharsetName() {
		String charsetName = this.getString("charsetName");
		if (charsetName == null) {
			charsetName = "UTF-8";
		}
		return charsetName;
	}

	public String getSource() {
		return this.getString("source");
	}

	void setUri(String uri) {
		this.put("uri", uri);
	}

	public void setSource(String source) {
		this.put("source", source);
	}

	public void setCharsetName(String CharsetName) {
		this.put("CharsetName", CharsetName);
	}

	public void setContentType(String contentType) {
		this.put("uri", contentType);
	}

	public void setState(int state) {
		this.put("state", state);
	}

	public void setMsg(String msg) {
		this.put("msg", msg);
	}

}
