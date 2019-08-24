package org.ninestar.im.client.handle_v0;

import org.ninestar.im.client.NineStarImCliHead;
import org.ninestar.im.msgcoder.ImContentType;

import com.alibaba.fastjson.JSONObject;

public class NineStarImMsgCliV0RespHead extends JSONObject implements NineStarImCliHead {

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

	public int getState() {
		return this.getIntValue("state");
	}

	public String getMsg() {
		return this.getString("msg");
	}

	// public void setUri(String uri) {
	// this.put("uri", uri);
	// }
	//
	// public void setSource(String source) {
	// this.put("source", source);
	// }
	//
	// public void setCharsetName(String CharsetName) {
	// this.put("CharsetName", CharsetName);
	// }
	//
	// public void setContentType(String contentType) {
	// this.put("uri", contentType);
	// }

}
