package org.ninestar.im.client.handle_v0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import org.ninestar.im.client.NineStarImCliRequest;
import org.ninestar.im.client.NineStarImCliRespCallback;
import org.ninestar.im.client.NineStarImCliResponse;
import org.ninestar.im.client.NineStarImClient;
import org.ninestar.im.client.NineStarImOutput;
import org.ninestar.im.client.error.NineStarCliRequestTimeoutException;
import org.ninestar.im.msgcoder.MsgPackage;
import org.ninestar.im.utils.Named;
import org.ninestar.im.utils.Sleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NineStarImV0Output implements NineStarImOutput {

	private static final Logger log = LoggerFactory.getLogger(NineStarImV0Output.class);

	private static Map<Long, SyncResult> result = new ConcurrentHashMap<Long, SyncResult>();
	private static Map<Long, CallbackCacheData> callbacks = new ConcurrentHashMap<Long, CallbackCacheData>();
	private static LinkedTransferQueue<AsyncResult> responseQueue = new LinkedTransferQueue<>();
	private static ExecutorService exec = Executors.newFixedThreadPool(10,Named.newThreadFactory("readAsync"));
	private static Thread readTr = new Thread(NineStarImV0Output::runAsync, "READ-TR");
	private static Thread timeoutTr = new Thread(NineStarImV0Output::runTimeout, "TIMEOUT-TR");

	static {
		readTr.setDaemon(true);
		readTr.start();
		timeoutTr.setDaemon(true);
		timeoutTr.start();
	}
	
	private NineStarImClient client;
	private long readTimeout = 10000;

	public NineStarImV0Output(NineStarImClient client, long readTimeout) {
		this.client = client;
		this.readTimeout = readTimeout;
	}

	public NineStarImV0Output(NineStarImClient client) {
		this.client = client;
	}

	/**
	 * 设置读超时
	 * 
	 * @param readTimeout
	 */
	public void setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
	}

	private static void runTimeout() {
		while (true) {
			Set<Entry<Long, CallbackCacheData>> entries = callbacks.entrySet();
			List<Long> timeouts = new ArrayList<Long>();
			for (Entry<Long, CallbackCacheData> entry : entries) {
				if (entry.getValue().isTimeout()) {
					timeouts.add(entry.getKey());
				}
			}

			if (timeouts.size() > 0) {
				for (Long id : timeouts) {
					CallbackCacheData ccd = callbacks.remove(id);
					if (ccd != null) {
						String url = ccd.getUrl();
						log.error(String.format("请求超时,msgPackId:%d, url:%s", id, url));
					}
				}
			} else {
				if (!Sleep.sleep(1000)) {
					break;
				}
			}
		}
	}

	private static void runAsync() {
		while (true) {
			try {
				AsyncResult asyncResult = responseQueue.take();
				if (asyncResult != null) {
					exec.execute(() -> {
						asyncResult.execute();
					});
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	void setNineStarImCliResponse(NineStarImMsgCliV0Response response) {
		long msgPackId = response.getMsgPackId();
		SyncResult syncResult = result.remove(msgPackId);
		if (syncResult != null) {
			syncResult.syncSetResult(response);
			return;
		}

		CallbackCacheData cacheData = callbacks.remove(msgPackId);
		if (cacheData == null) {
			return;
		}
		if (cacheData.isTimeout()) {
			String url = cacheData.getUrl();
			log.error(String.format("请求超时,msgPackId:%d, url:%s", msgPackId, url));
			return;
		}
		NineStarImCliRespCallback<?> callback = cacheData.getCallback();
		responseQueue.add(new AsyncResult(callback, response));
	}

	@Override
	public void send(NineStarImCliRequest request,
			NineStarImCliRespCallback<? extends NineStarImCliResponse> callback) {
		MsgPackage msg = request.toMsgPackage();
		long msgPackId = msg.getMsgId(); // 获得消息ID
		String url = request.getHead().getUri(); // 获得URI
		client.writeAndFlush(msg); // 输出
		callbacks.put(msgPackId, new CallbackCacheData(callback, readTimeout, url)); // 设置回掉
	}

	/**
	 * 发送消息并同步等待应答
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends NineStarImCliResponse> T sendSync(NineStarImCliRequest request, long awaitTime)
			throws NineStarCliRequestTimeoutException {
		MsgPackage msg = request.toMsgPackage();
		long msgPackId = msg.getMsgId();
		SyncResult syncResult = new SyncResult();
		result.put(msgPackId, syncResult);
		client.writeAndFlush(msg);
		T r = (T) syncResult.getResponse(awaitTime);
		result.remove(msgPackId);
		return r;
	}

	public <T extends NineStarImCliResponse> T sendSync(NineStarImCliRequest request)
			throws NineStarCliRequestTimeoutException {
		return sendSync(request, readTimeout);
	}

}
