package com.loliclub.eater.properties;

import org.json.JSONException;
import org.json.JSONObject;

public class WebSocketMessage {

	/**
	 * 登录指令
	 */
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_LOGIN_SUCCESS = "LOGIN_SUCCESS";

	/**
	 * 心跳指令
	 */
	public static final String ACTION_NOP = "nop";
	public static final String ACTION_NOP_SUCCESS = "nop-success";

	/**
	 * 测试指令
	 */
	public static final String ACTION_TEST = "test";

	/**
	 * 数据指令
	 */
	public static final String ACTION_NEW_DATA = "newdata";

	/**
	 * 时间
	 */
	private static final String DATETIME = "datetime";

	/**
	 * sessionId
	 */
	private static final String SESSION_ID = "sessionId";

	/**
	 * 命令
	 */
	private static final String ACTION = "action";

	/**
	 * 数据部分
	 */
	private static final String DATA = "data";

	private String sessionId;
	private String datetime;
	private String action;
	private String data;

	public WebSocketMessage() {
		
	}
	
	public WebSocketMessage(String json) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			this.setAttribute(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(SESSION_ID, sessionId);
			jsonObject.put(DATETIME, datetime);
			jsonObject.put(ACTION, action);
			jsonObject.put(DATA, data);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return jsonObject;
	}

	public void setAttribute(JSONObject jsonObject) {
		sessionId = jsonObject.optString(SESSION_ID);
		datetime = jsonObject.optString(DATETIME);
		action = jsonObject.optString(ACTION);
		data = jsonObject.optString(DATA);
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WebSocketMessage [sessionId=" + sessionId + ", datetime="
				+ datetime + ", action=" + action + ", data=" + data + "]";
	}

}
