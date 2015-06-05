package com.loliclub.network.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * HTTP请求客户端
 * 
 */
public class HttpClient {
	
	private static final int CONNECT_TIMEOUT = 10000;
	private static final int RESPONSE_TIMEOUT = 10000;
	
	private ThreadPoolExecutor threadPool;
	
	private static HttpClient client;
	
	private HttpClient() {
		threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}
	
	private static synchronized void syncInit() {
		if (client == null)
			client = new HttpClient();
	}
	
	public static synchronized final HttpClient getInstance() {
		syncInit();
		return client;
	}
	
	public void get(Context context, final String url, final Map<String, String> params,
			final HttpClientResponseHandler responseHandler) {
		if (responseHandler == null)
			return;
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					URL urlEntity = null;
					if (params != null && !params.isEmpty()) {
						Iterator<String> iterator = params.keySet().iterator();
						StringBuffer paramsBuffer = new StringBuffer(url);
						paramsBuffer.append("?");
						while(iterator.hasNext()) {
							String key = iterator.next();
							paramsBuffer.append(key);
							paramsBuffer.append("=");
							paramsBuffer.append(params.get(key));
							paramsBuffer.append("&");
						}
						paramsBuffer.deleteCharAt(paramsBuffer.length() - 1);
						urlEntity = new URL(paramsBuffer.toString());
					} else 
						urlEntity = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) urlEntity.openConnection();
					// 以下设置只能使用一次，每次使用的时候都需要重新设置
					urlConnection.setDoInput(true);
					// 设置超时
					urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
					urlConnection.setReadTimeout(RESPONSE_TIMEOUT);
					// post请求不能使用缓存
					urlConnection.setRequestMethod("GET"); 
					urlConnection.setUseCaches(false);
					// 设置传输的内容，JSON对象
					urlConnection.setRequestProperty("Content-type", "application/json");
					// 获取输入流，服务器响应的内容
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					StringBuffer buffer = new StringBuffer();
					String lineString = null;
					while ((lineString = reader.readLine()) != null)
						buffer.append(lineString);
					reader.close();
					urlConnection.disconnect();
					Log.e("HTTPClient", "response Code is " + urlConnection.getResponseCode());
					Log.e("HTTPClient", "response Message is " + urlConnection.getResponseMessage());
					responseHandler.onSuccess(200, new JSONObject(buffer.toString()));
				} catch (MalformedURLException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "MalformedURLException");
				} catch (SocketTimeoutException e) {
						e.printStackTrace();
						responseHandler.onFailure(e, "SocketTimeoutException");
				} catch (IOException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "IOException");
				} catch (JSONException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "JSONException");
				} 
			}
		});
	}

	public void post(Context context, final String url, final JSONObject jsonObj,
			final HttpClientResponseHandler responseHandler) {
		if (responseHandler == null)
			return;
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					URL urlEntity = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) urlEntity.openConnection();
					// 以下设置只能使用一次，每次使用的时候都需要重新设置
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					// 设置超时
					urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
					urlConnection.setReadTimeout(RESPONSE_TIMEOUT);
					// post请求不能使用缓存
					urlConnection.setRequestMethod("POST"); 
					urlConnection.setUseCaches(false);
					// 设置传输的内容，JSON对象
					urlConnection.setRequestProperty("Content-type", "application/json"); 
					DataOutputStream outStream = new DataOutputStream(urlConnection.getOutputStream());
					outStream.writeBytes(jsonObj.toString());
					outStream.flush();
					outStream.close();
					// 获取输入流，服务器响应的内容
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					StringBuffer buffer = new StringBuffer();
					String lineString = null;
					while ((lineString = reader.readLine()) != null)
						buffer.append(lineString);
					reader.close();
					urlConnection.disconnect();
					Log.e("HTTPClient", "response Code is " + urlConnection.getResponseCode());
					Log.e("HTTPClient", "response Message is " + urlConnection.getResponseMessage());
					responseHandler.onSuccess(200, new JSONObject(buffer.toString()));
				} catch (MalformedURLException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "MalformedURLException");
				} catch (SocketTimeoutException e) {
						e.printStackTrace();
						responseHandler.onFailure(e, "SocketTimeoutException");
				} catch (IOException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "IOException");
				} catch (JSONException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "JSONException");
				} 
			}
		});
	}

}
