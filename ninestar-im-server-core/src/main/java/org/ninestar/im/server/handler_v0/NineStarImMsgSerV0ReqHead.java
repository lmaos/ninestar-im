package org.ninestar.im.server.handler_v0;

import org.ninestar.im.msgcoder.ImContentType;
import org.ninestar.im.server.NineStarImSerHead;

import com.alibaba.fastjson.JSONObject;

public class NineStarImMsgSerV0ReqHead extends JSONObject implements NineStarImSerHead {

	private static final long serialVersionUID = 1L;

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
		String contentType = this.getString("contentType");
		if (contentType == null) {
			contentType = ImContentType.TEXT;
		}
		return contentType;
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

	public void setUri(String uri) {
		this.put("uri", uri);
	}

	public void setSource(String source) {
		this.put("source", source);
	}

	public void setCharsetName(String CharsetName) {
		this.put("CharsetName", CharsetName);
	}

	public void setContentType(String contentType) {
		this.put("contentType", contentType);
	}

}
