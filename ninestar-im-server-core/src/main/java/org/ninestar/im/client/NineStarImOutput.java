package org.ninestar.im.client;

import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;

public interface NineStarImOutput {

	void send(NineStarImCliRequest request, NineStarImCliRespCallback<? extends NineStarImCliResponse> callback);

	<T extends NineStarImCliResponse> T sendSync(NineStarImCliRequest request, long awaitTime) throws NineStarCliRequestTimeoutException;
	
	NineStarImCliRequest createRequest(String uri);
}
