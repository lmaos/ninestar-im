package org.ninestar.im.client;
@FunctionalInterface
public interface NineStarImCliRespCallback<T extends NineStarImCliResponse> {

	void onMessage(T response);
	
}
