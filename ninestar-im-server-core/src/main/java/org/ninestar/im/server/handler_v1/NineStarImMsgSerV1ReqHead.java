package org.ninestar.im.server.handler_v1;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSON;

public class NineStarImMsgSerV1ReqHead {

	public NineStarImMsgSerV1ReqHead(String sourceId) {
		this.sourceId = sourceId;
	}

	// 发送的目标
	private Set<String> targeIds = new HashSet<String>();
	private String sourceId; // 发送源
	private boolean sendall;

	public Set<String> getTargeIds() {
		return targeIds;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void addTargeId(String targeId) {
		this.targeIds.add(targeId);
	}

	public void addTargeIdAll(String[] targeIds) {
		for (String targeId : targeIds) {
			this.targeIds.add(targeId);
		}
	}

	public void addTargeIdAll(Collection<String> targeIds) {
		this.targeIds.addAll(targeIds);
	}
	
	public void setSendall(boolean sendall) {
		this.sendall = sendall;
	}
	
	public boolean isSendall() {
		return sendall;
	}
	
	public byte[] toBytes() {
		String json = JSON.toJSONString(this);
		return json.getBytes(Charset.forName("UTF-8"));
	}
}
