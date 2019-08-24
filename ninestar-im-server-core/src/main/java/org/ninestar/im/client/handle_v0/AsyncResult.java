package org.ninestar.im.client.handle_v0;

import org.ninestar.im.client.NineStarImCliRespCallback;
import org.ninestar.im.client.NineStarImCliResponse;

public class AsyncResult {
	private NineStarImCliRespCallback callback;
	private NineStarImCliResponse response;
	public AsyncResult(NineStarImCliRespCallback callback,
			NineStarImCliResponse response) {
		super();
		this.callback = callback;
		this.response = response;
	}
	public NineStarImCliRespCallback<? extends NineStarImCliResponse> getCallback() {
		return callback;
	}
	public void setCallback(NineStarImCliRespCallback<? extends NineStarImCliResponse> callback) {
		this.callback = callback;
	}
	public NineStarImCliResponse getResponse() {
		return response;
	}
	public void setResponse(NineStarImCliResponse response) {
		this.response = response;
	}
	
	public void execute() {
		callback.onMessage(response);	
	}
	
}
