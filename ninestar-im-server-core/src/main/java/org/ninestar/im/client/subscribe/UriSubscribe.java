package org.ninestar.im.client.subscribe;

import org.ninestar.im.client.NineStarImCliResponse;

@FunctionalInterface
public interface UriSubscribe {

	void onmessage(NineStarImCliResponse response) throws Exception;
	
}
