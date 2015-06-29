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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.support.annotation.Nullable;
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
	
	public static synchronized HttpClient getInstance() {
		syncInit();
		return client;
	}

	/**
	 * get 方法
	 * @param url 请求地址
	 * @param params 请求参数，放在地址后面
	 * @param jsonObj 请求参数，通过流
	 * @param responseHandler 回调方法
	 */
	public void get(final String url, @Nullable final Map<String, String> params,
					@Nullable final JSONObject jsonObj, final HttpClientResponseHandler responseHandler) {
		if (responseHandler == null)
			return;
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					// 根据参数组装URL
					URL urlEntity;
					if (params != null && !params.isEmpty()) {
						Iterator<String> iterator = params.keySet().iterator();
						StringBuilder paramsBuffer = new StringBuilder(url);
						paramsBuffer.append("?");
						while (iterator.hasNext()) {
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
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					// 设置超时
					urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
					urlConnection.setReadTimeout(RESPONSE_TIMEOUT);
					// 不使用缓存
					urlConnection.setRequestMethod("GET");
					urlConnection.setUseCaches(false);
					// 设置传输的内容，JSON对象，并在HTTP写入传输语言
					urlConnection.setRequestProperty("Charset", "UTF-8");
					urlConnection.setRequestProperty("Content-type", "application/json");
					urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
					// 获取输出流，并写入参数
					if (jsonObj != null) {
						// 设置使用输出流
						DataOutputStream outStream = new DataOutputStream(urlConnection.getOutputStream());
						outStream.writeBytes(jsonObj.toString());
						outStream.flush();
						outStream.close();
					}
					// 获取输入流，服务器响应的内容
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					final StringBuilder buffer = new StringBuilder();
					String lineString;
					while ((lineString = reader.readLine()) != null)
						buffer.append(lineString);
					reader.close();
					urlConnection.disconnect();
					final int responseCode = urlConnection.getResponseCode();
					Log.e("HttpClient", "response Code is " + responseCode);
					Log.e("HttpClient", "response Message is " + urlConnection.getResponseMessage());
					try {
						responseHandler.onSuccess(200, new JSONObject(buffer.toString()));
					} catch (JSONException e) {
						responseHandler.onFailure(e, "JSONException");
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "MalformedURLException");
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "SocketTimeoutException");
				} catch (IOException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "IOException");
				}
			}
		});
	}

	/**
	 * post 请求
	 * @param url 请求地址
	 * @param params 请求参数，放在请求地址后面
	 * @param jsonObj 请求参数，通过流传输
	 * @param responseHandler 回调方法
	 */
	public void post(final String url, @Nullable final Map<String, String> params,
					 @Nullable final JSONObject jsonObj, final HttpClientResponseHandler responseHandler) {
		if (responseHandler == null)
			return;
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					// 根据URL参数组装URL
					URL urlEntity;
					if (params != null && !params.isEmpty()) {
						Iterator<String> iterator = params.keySet().iterator();
						StringBuilder paramsBuffer = new StringBuilder(url);
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
					urlConnection.setDoOutput(true);
					urlConnection.setDoInput(true);
					// 设置超时
					urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
					urlConnection.setReadTimeout(RESPONSE_TIMEOUT);
					// post请求不能使用缓存
					urlConnection.setRequestMethod("POST");
					urlConnection.setUseCaches(false);
					// 设置传输的内容，JSON对象
					urlConnection.setRequestProperty("Charset", "UTF-8");
					urlConnection.setRequestProperty("Content-type", "application/json");
					urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
					// 获取输出流，并写入参数
					if (jsonObj != null) {
						DataOutputStream outStream = new DataOutputStream(urlConnection.getOutputStream());
						outStream.writeBytes(jsonObj.toString());
						outStream.flush();
						outStream.close();
					}
					// 获取输入流，服务器响应的内容
					BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					final StringBuilder buffer = new StringBuilder();
					String lineString;
					while ((lineString = reader.readLine()) != null)
						buffer.append(lineString);
					reader.close();
					urlConnection.disconnect();
					final int responseCode = urlConnection.getResponseCode();
					Log.e("HttpClient", "response Code is " + responseCode);
					Log.e("HttpClient", "response Message is " + urlConnection.getResponseMessage());
					try {
						responseHandler.onSuccess(200, new JSONObject(buffer.toString()));
					} catch (JSONException e) {
						e.printStackTrace();
						responseHandler.onFailure(e, "JSONException");
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "MalformedURLException");
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "SocketTimeoutException");
				} catch (IOException e) {
					e.printStackTrace();
					responseHandler.onFailure(e, "IOException");
				}
			}
		});
	}

}
