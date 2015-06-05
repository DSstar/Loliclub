package com.loliclub.network.http;

import org.json.JSONObject;

public abstract class HttpClientResponseHandler {

	public void onSuccess(int statusCode, JSONObject response) {
		this.onSuccess(statusCode, response.toString());
	}
	
	public void onSuccess(int statusCode, String response) {
		
	}
	
	public void onFailure(Throwable e, JSONObject errorResponse) {
		this.onFailure(e, errorResponse.toString());
	}
	
	public void onFailure(Throwable e, String errorResponse) {
		
	}
	
}
