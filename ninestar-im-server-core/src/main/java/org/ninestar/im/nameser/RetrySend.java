package org.ninestar.im.nameser;

import java.util.Set;

import org.ninestar.im.server.NineStarImSerResponse;

public class RetrySend {
	
	private String sourceId;
	private String serverId;
	private Set<String> targerIds;
	private long createTime = 0;
	private long timeout;
	private NineStarImSerResponse response;

	public RetrySend(String sourceId, String serverId, Set<String> targerIds, long timeout, NineStarImSerResponse response) {
		this.sourceId = sourceId;
		this.serverId = serverId;
		this.targerIds = targerIds;
		this.createTime = System.currentTimeMillis();
		this.timeout = timeout;
		this.response = response;
	}

	public String getSourceId() {
		return sourceId;
	}
	
	String getServerId() {
		return serverId;
	}

	Set<String> getTargerIds() {
		return targerIds;
	}

	long getCreateTime() {
		return createTime;
	}

	boolean isTimeout() {
		return System.currentTimeMillis() - createTime > timeout;
	}
	
	public NineStarImSerResponse getResponse() {
		return response;
	}

}
