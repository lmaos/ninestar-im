package org.ninestar.im.server.handler_v1;

import org.ninestar.im.server.handler_v0.NineStarImMsgSerV0Response;

public class NineStarImMsgSerV1Response extends NineStarImMsgSerV0Response{
	protected NineStarImMsgSerV1Response(long msgPackId) {
		super(msgPackId);
	}
	
	void put(String key, Object value) {
		this.getHead().put(key, value);
	}
	
}
