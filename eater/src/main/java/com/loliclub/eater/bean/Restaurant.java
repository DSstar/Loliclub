package com.loliclub.eater.bean;

import com.loliclub.dbhelper.standard.BeanStandard;
import com.loliclub.dbhelper.util.BeanUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant extends BeanStandard {

	/**
	 * 与数据库版本号一致
	 */
	private static final long serialVersionUID = 1L;
	
	@primaryKey
	public String id;
	
	public String name;
	
	public String phoneNumber;

	@Override
	public JSONObject toJSONObject(int type) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("id", id);
			jsonObj.put("deviceName", name);
			jsonObj.put("modelNum", phoneNumber);
			// 固定填充字段
			jsonObj.put("sn", id);
			jsonObj.put("mac", id);
			jsonObj.put("qrcode", id);
			jsonObj.put("deviceType", "03");
			jsonObj.put("communicationType", 4);
			jsonObj.put("maxUserQuantity", 1);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return jsonObj;
	}

	@Override
	public void setAttribute(JSONObject jsonObj, int type) {
		this.id = jsonObj.optString("id");
		this.name = jsonObj.optString("deviceName");
		this.phoneNumber = jsonObj.optString("modelNum");
	}

	@Override
	public void setDefault(int type) {
		this.id = BeanUtil.generateId().substring(0,8);
	}

	@Override
	public String toString() {
		return "Restaurant{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}
}
