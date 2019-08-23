package org.ninestar.im.server.handler_v0;

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

}
