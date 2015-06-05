package com.loliclub.eater.services;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 * WebSocket服务
 *
 */
public class WebSocketService extends WebSocketClient {

	private Callback callback;
	
	private WebSocketService(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}
	
	public WebSocketService(URI serverUri, Draft draft, Callback callback) {
		super(serverUri, draft);
		this.callback = callback;
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	@Override
	public void onOpen(ServerHandshake arg0) {
		callback.onOpen();
	}
	
	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		callback.onClose(arg0, arg1, arg2);
	}
	
	@Override
	public void onMessage(String arg0) {
		callback.onMessage(arg0);
	}

	@Override
	public void onError(Exception arg0) {
		callback.onError(arg0);
	}

	public interface Callback{
		
		public void onOpen();
		
		public void onClose(int code, String content, boolean flag);
		
		public void onError(Exception e);
		
		public void onMessage(String msg);
	}

}
