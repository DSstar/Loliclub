package com.loliclub.eater.properties;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.loliclub.network.http.HttpClient;
import com.loliclub.network.http.HttpClientResponseHandler;

import android.content.Context;

public class ServerAPI {

	public static final String RESPONSE_CODE = "responseCode";
	
	public static final String RESPONSE_SESSIONID = "sessionId";
	
	public static final String RESPONSE_ACCESSTOKEN = "accessToken";
	
	public static final String RESPONSE_NEXTTS = "nextTs";
	
	public static final String RESPONSE_RECORDCOUNT = "recordCount";
	
	public static final String RESPONSE_FILESIZE = "fileSize";
	
	public static final String RESPONSE_FILEURL = "fileUrl";
	
	/** 帐号服务接口 **/
	/**
	 * 用户注册
	 * @param context 设备上下文环境
	 * @param account 帐号信息
	 * @param member 成员信息
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void signup(final Context context,Account account,List<AccountBinding> accountBindings,List<Member> member,final JsonHttpResponseHandler cback,final boolean async){
		//LSLogger.LSLog("-- signup --");
		JSONObject accountJson = new AccountRequest().toJsonObject(account,accountBindings);
		JSONArray memberList=new JSONArray();
		for(int i=0;i<member.size();i++){
			memberList.put(new MemberRequest().toJsonObject(member.get(i)));
		}
		JSONObject signupJson = new JSONObject();
		try {
			signupJson.put("account", accountJson);
			signupJson.put("member", memberList);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_SIGNUP, signupJson,cback,async);
		LSLog.e(LSLog.LS_NET, "signup url is " + UrlBuilder.URL_BASE + UrlBuilder.URL_SIGNUP);
		LSLog.e(LSLog.LS_NET, "signup json is " + signupJson.toString());
	}
	*/
	
	/**
	 * 检查登录名是否存在
	 * @param context 设备上下文环境
	 * @param LSLoginname 登录名
	 * @param carrier 运营商
	 * @param app 应用名称
	 * @param LSLoginType 登录类型
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void existUsername(final Context context, String loginname, int loginType, final JsonHttpResponseHandler cback,final boolean async){
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("app", APPNAME);
			jsonObj.put("carrier", CARRIER);
			jsonObj.put("loginname", loginname);
			jsonObj.put("loginType", loginType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- existUsername --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_CHECKUSERNAME, jsonObj,cback,async);
		LSLog.e(LSLog.LS_NET,"URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_CHECKUSERNAME);
		LSLog.e(LSLog.LS_NET,"JSONObject is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 用户登录，第三方初始登录时需要用户初始化设置成员信息
	 * 
	 * @param context
	 *            设备上下文环境
	 * @param loginname
	 *            用户名，使用第三方登录时，登录名为openID，
	 * @param password
	 *            密码，使用第三方登录时，密码为accessToken。
	 * @param appname
	 *            应用名
	 * @param carrier
	 *            运营商
	 * @param loginType
	 *            登录类型
	 * @param clientId
	 *            客户端ID，唯一标识
	 * @param callBack
	 *            回调方法
	 */
	public static void signin(final Context context, String loginname,
			String password, String appname, String carrier, int loginType,
			String clientId, final HttpClientResponseHandler callBack) {
		String timestamp = String.valueOf(System.currentTimeMillis());
		String nonce = Integer.toString((int) (Math.random() * 100000));
		String signature = calculateSignature(password, timestamp, nonce);
		String url = buildStrURL(UrlBuilder.URL_SIGNIN, signature, timestamp,
				nonce);
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("loginname", loginname);
			jsonObj.put("app", appname);
			jsonObj.put("carrier", carrier);
			if (loginType >= 3) {
				// loginType >= 3,则输入的password为第三方的accessToken
				jsonObj.put("accessToken", password);
			}
			jsonObj.put("clientId", clientId);
			jsonObj.put("loginType", loginType);
		} catch (JSONException e) {
			e.printStackTrace();
			callBack.onFailure(e, "");
		}
		HttpClient.getInstance().post(context, url, jsonObj, callBack);
	}

	/**
	 * 修改密码
	 * @param context 设备上下文环境
	 * @param userName 用户名
	 * @param password 原密码
	 * @param newPassword 新密码
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void changePassword(final Context context, String password, String newPassword,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE, UrlBuilder.URL_CHANGEPASSWORD, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("oldPassword", password);
			jsonObj.put("newPassword", newPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		LSLog.e(LSLog.LS_NET, "-- Change password --");
		NetManager.post(context, url, jsonObj, cback, async);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET, "JSON IS " + jsonObj);
	}
	*/
	
	/**
	 * 修改用户名（暂时不能使用）
	 * @param context 设备上下文环境
	 * @param userName 原用户名
	 * @param newUserName 新用户名
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void changeUserName(final Context context,String loginname,String password, String newLoginname,int loginType,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_CHANGE_LOGINNAME, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("oldLoginname", loginname);
			jsonObj.put("password", password);
			jsonObj.put("newLoginname",newLoginname);
			jsonObj.put("LSLoginType",loginType);
		} catch (Exception e) {
		}
		LSLog.e(LSLog.LS_NET, "-- changeUserName --");
		NetManager.post(context, url, jsonObj, cback,async);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET, "jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 修改帐号属性 
	 * @param context 设备上下文环境
	 * @param bpUnti 血压单位（可以为空）
	 * @param heightUnit 身高单位（可以为空）
	 * @param weightUnit 体重单位（可以为空）
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void changeAccountProfile(final Context context,String bpUnti, String heightUnit,String weightUnit,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_CHANGEPROFILE, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("bpUnit", bpUnti);
			jsonObj.put("heightUnit", heightUnit);
			jsonObj.put("weightUnit", weightUnit);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET, "-- changeAccountProfile --");
		NetManager.post(context, url, jsonObj, cback, async);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET, "jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 退出登录
	 * @param context
	 * @param sessionId
	 * @param cback
	 */
	public static void logout(final Context context, String sessionId, final HttpClientResponseHandler cback){
		Map<String, String> params = new HashMap<String, String>();
		params.put("sessionId", sessionId);
		HttpClient.getInstance().get(context, UrlBuilder.URL_LOGOUT, params, cback);
	}
	
	/**
	 * 找回密码
	 * @param context 设备上下文
	 * @param username 用户名
	 * @param appn 应用名 
	 * @param carrier 运营商
	 * * @param loginType 登录类型
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void forgetPassword(final Context context,String loginname, int loginType,final JsonHttpResponseHandler cback,final boolean async){
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("app", APPNAME);
			jsonObj.put("carrier", CARRIER);
			jsonObj.put("loginname", loginname);
			jsonObj.put("loginType", loginType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- forgetPassword --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_RESETPASSWORD, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_RESETPASSWORD);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 用于手机账号，忘记密码时调用
	 * @param context
	 * @param app	应用名称
	 * @param carrier  运营商
	 * @param mobile	手机号
	 * @param captcha   验证码
	 * @param newPassword 设置的新密码
	 * @param cback
	 */
	/*
	public static void resetPasswordByMobile(final Context context,
			String mobile,String captcha,String newPassword,final JsonHttpResponseHandler cback){
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("app", APPNAME);
			jsonObj.put("carrier", CARRIER);
			jsonObj.put("mobile", mobile);
			jsonObj.put("captcha", captcha);
			jsonObj.put("password", newPassword);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- resetPasswordByMobile --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_RESETPASSWORD, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_RESETPASSWORD_BYMOBILE);
		LSLog.e(LSLog.LS_NET," resetPasswordByMobile jsonObj is " + jsonObj.toString());
	}
	*/
	
	/** 成员服务接口 **/
	/**
	 * 保存/修改成员信息，如果需要同时修改多个成员信息请使用upload接口
	 * @param context 设备上下文环境
	 * @param object 如果参数是member类型，则新增用户;如果是Map，则修改用户信息
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void updateMember(final Context context,Object object,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_SAVEMEMBER, signature, timestamp, nonce, NetManager.sessionId);		
		if(object instanceof Member){
			Member member=(Member)object;
			JSONObject memberJson = new MemberRequest().toJsonObject(member);
			NetManager.post(context, url, memberJson, cback,async);
			LSLog.e(LSLog.LS_NET,"URL is " + url);
			LSLog.e(LSLog.LS_NET, "JSON is " + memberJson.toString());
		} else if(object instanceof Map<?, ?>){
			Map<?,?> map=(Map<?,?>)object;
			JSONObject memberMap=new MemberRequest().toJsonObject(map);
			NetManager.post(context, url, memberMap, cback,async);
			LSLog.d(LSLog.LS_NET, "JSON is " + memberMap.toString());
		}
	}
	*/
	
	/**
	 * 删除成员信息
	 * @param context 设备上下文对象
	 * @param memberId 成员对象ID
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void deleteMember(final Context context,String memberId,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_DELETEMEMBER, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("memberId", memberId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- deleteMember --");
		NetManager.post(context,url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/** 设备服务接口 **/
	/**
	 * 获取设备信息
	 * @param context 设备上下文环境
	 * @param qrCode 设备二维码文本，实质就是设备的SN号
	 * @param deviceBinding 设备绑定信息，true-带绑定信息 ，false-不带绑定信息
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void getDeviceInfo(final Context context,String qrCode, boolean deviceBinding,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_GETDEVICE, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("qrcode", qrCode);
			jsonObj.put("deviceBinding", deviceBinding);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- getDeviceInfo --");
		NetManager.post(context,url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 获取设备信息（根据mac地址）
	 * @param context 设备上下文环境
	 * @param mac　设备mac地址
	 * @param deviceBinding 设备绑定信息，true-带绑定信息 ，false-不带绑定信息
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void getDeviceInfoByMac(final Context context,String mac, boolean deviceBinding,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_GETDEVICEBYMAC, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("mac", mac);
			jsonObj.put("deviceBinding", deviceBinding);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- getDeviceInfoByMac --");
		NetManager.post(context,url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 获取设备绑定信息
	 * @param context 设备上下文环境
	 * @param deviceId 设备ID号
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void getDeviceBinding(final Context context,String deviceId,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_GETDEVICEBIND, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("deviceId", deviceId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"获取设备绑定信息");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 获取设备图片
	 * @param context 设备上下文环境
	 * @param picture 设备图片名
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	private static void getDevicePicture(final Context context,String picture,final JsonHttpResponseHandler cback,final boolean async){
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("picture", picture);
			jsonObj.put("carrier", CARRIER);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- getDevicePicture --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_GETDEVICEPICTURE, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_GETDEVICEPICTURE);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 添加设备信息
	 * @param context 设备上下文环境
	 * @param device 设备对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void addDevice(final Context context,Device device,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_ADDDEVICE, signature, timestamp, nonce, NetManager.sessionId);
		LSLog.e(LSLog.LS_NET,"-- addDevice --");
		DeviceRequest deviceRequest = new DeviceRequest();
		NetManager.post(context, url, deviceRequest.toJsonObject(device), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + deviceRequest.toJsonObject(device).toString());
	}
	*/
	
	/**
	 * 绑定家庭成员       
	 * @param context 设备上下文环境
	 * @param deviceBinding 设备绑定关系对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void bindMember(final Context context,List<DeviceBinding> deviceBinding,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_BINDMEMBER, signature, timestamp, nonce, NetManager.sessionId);
		DeviceBindingRequest bindingRequest = new DeviceBindingRequest();
		LSLog.e(LSLog.LS_NET,"-- bindMember --");
		NetManager.post(context, url, bindingRequest.toJsonObject(deviceBinding), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + bindingRequest.toJsonObject(deviceBinding).toString());
	}
	*/
	
	/**
	 * 解绑家庭成员
	 * @param context 设备上下文环境
	 * @param deviceSn 设备SN号
	 * @param userNo 用户编号
	 * @param memberId 成员ID，可以为空，空表示解除设备与帐号的绑定关系
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void unbindMember(final Context context,String deviceId,int userNo,String memberId,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_UNBINDMEMBER, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("deviceId", deviceId);
			jsonObj.put("userNo", userNo);
			jsonObj.put("memberId", memberId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- unbindMember --");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + jsonObj.toString());
	}
	*/
	
	/** 数据服务接口 **/
	/**
	 * 保存体重数据，如果上传一组数据请使用upload接口
	 * @param context 设备上下文环境
	 * @param weightRecord 体重记录对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void saveWeightRecord(final Context context,WeightRecord weightRecord,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_SAVEWEIGHT, signature, timestamp, nonce, NetManager.sessionId);
		WeightRecordRequest weightRequest = new WeightRecordRequest();
		LSLog.e(LSLog.LS_NET,"-- saveWeightRecord --");
		NetManager.post(context, url, weightRequest.toJsonObject(weightRecord), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + weightRequest.toJsonObject(weightRecord).toString());
	}
	*/
	
	/**
	 * 删除体重数据
	 * @param context 设备上下文环境
	 * @param id 体重记录ID
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void deleteWeightRecord(final Context context,String id,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_DELETEWEIGHT, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- deleteWeightRecord --");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"params is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 保存血压记录，如果上传一组数据请使用upload接口
	 * @param context 设备上下文环境
	 * @param bpRecord 血压记录对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void saveBPRecord(final Context context,BPRecord bpRecord,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_SAVEBP, signature, timestamp, nonce, NetManager.sessionId);
		BPRecordRequest bpRequest = new BPRecordRequest();
		LSLog.e(LSLog.LS_NET,"-- saveBPRecord --");
		NetManager.post(context, url, bpRequest.toJsonObject(bpRecord), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + bpRequest.toJsonObject(bpRecord).toString());
	}
	*/
	
	/**
	 * 删除血压数据
	 * @param context 设备上下文环境
	 * @param id 血压记录ID
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void deleteBPRecord(final Context context,String id,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		
		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_DELETEBP, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- deleteBPRecord --");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"params is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 保存计步器记录，如果上传一组数据请使用upload接口
	 * @param context 设备上下文环境
	 * @param pedometerRecord 计步器记录对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void savepedometerRecord(final Context context,PedometerRecord pedometerRecord,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_SAVE_PEDOMETER, signature, timestamp, nonce, NetManager.sessionId);
		PedometerRecordRequest pedometerRequest=new PedometerRecordRequest();
		NetManager.post(context, url, pedometerRequest.toJsonObject(pedometerRecord), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + pedometerRequest.toJsonObject(pedometerRecord).toString());
	}
	*/
	
	/**
	 * 删除计步器记录
	 * @param context 设备上下文环境
	 * @param id 计步器记录ID
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void deletepedometerRecords(final Context context,String id,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_DELETE_PEDOMETER, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- deleteHeightRecord --");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"params is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 保存身高记录，如果上传一组数据请使用upload接口
	 * @param context 设备上下文环境
	 * @param heightRecord 身高记录对象
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void saveHeightRecord(final Context context,HeightRecord heightRecord,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_SAVEHEIGHT, signature, timestamp, nonce, NetManager.sessionId);
		HeightRecordRequest heightRequest = new HeightRecordRequest();
		LSLog.e(LSLog.LS_NET,"-- saveHeightRecord --");
		NetManager.post(context, url, heightRequest.toJsonObject(heightRecord), cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + heightRequest.toJsonObject(heightRecord).toString());
	}
	*/
	
	/**
	 * 删除身高记录
	 * @param context 设备上下文环境
	 * @param id 身高记录ID
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void deleteHeightRecord(final Context context,String id,final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_DELETEHEIGHT, signature, timestamp, nonce, NetManager.sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET,"-- deleteHeightRecord --");
		NetManager.post(context, url, jsonObj, cback);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"params is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 下载数据，数据以文件形式返回
	 * @param context 设备上下文环境
	 * @param ts 时间戳，0代表初始化下载，其他数字代表更新下载
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	public static void download(final Context context,String accessToken, String sessionId, long ts,final HttpClientResponseHandler cback,final boolean async){
		String timestamp = Long.toString(System.currentTimeMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(accessToken,timestamp, nonce);

		String url=buildStrURL(UrlBuilder.URL_DOWNLOAD, signature, timestamp, nonce, sessionId);
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("ts", ts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		HttpClient.getInstance().post(context, url, jsonObj, cback);
	}
	
	/**
	 * 上传数据，如果某记录保存失败，则服务器会返回该记录的ID
	 * @param context 设备上下文环境
	 * @param account 帐号信息对象
	 * @param members 成员列表
	 * @param devices 设备列表，可以为空
	 * @param deviceBindings 设备用户绑定列表，可以为空
	 * @param weightRecords 体重记录列表，可以为空
	 * @param bpRecords 血压记录列表，可以为空
	 * @param heightRecords 身高记录列表，可以为空
	 * @param bmiRecords BMI记录列表，可以为空
	 * @param cback 回调方法
	 * @param async 异步标志
	 */
	/*
	public static void upload(final Context context,Account account,List<AccountBinding> accountBindings ,List<Member> members,List<Device> devices,List<DeviceBinding> deviceBindings,
			List<WeightRecord> weightRecords,List<BPRecord> bpRecords,List<HeightRecord> heightRecords,List<Bmirecord> bmiRecords,List<PedometerRecord> pedometerRecords,List<MemberGoal> memberGoals,
			final JsonHttpResponseHandler cback,final boolean async){
		if(NetManager.sessionId == null || "".equals(NetManager.sessionId)){
			//返回错误信息
			return;
		}
		LSLog.e(LSLog.LS_NET, "upload");
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int)(Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);

		String url=toStrURL(UrlBuilder.URL_BASE,UrlBuilder.URL_UPLOAD, signature, timestamp, nonce, NetManager.sessionId);
		UploadRequest upload = new UploadRequest();
		LSLog.e(LSLog.LS_NET,"-- upload --");
		NetManager.post(context, url.toString(), 
				upload.toJsonObject(account ,accountBindings, members, devices, deviceBindings, weightRecords, bpRecords, heightRecords, bmiRecords,pedometerRecords,memberGoals), 
				cback, async);
		LSLog.e(LSLog.LS_NET,"URL is " + url);
		LSLog.e(LSLog.LS_NET,"jsonObj is " + 
				upload.toJsonObject(account,accountBindings, members, devices, deviceBindings, weightRecords, bpRecords, heightRecords, bmiRecords,pedometerRecords,memberGoals)
				.toString());
	}
	*/
	
	/** 公共服务接口 **/
	/**
	 * 获取最新APP版本信息
	 * @param context 设备上下文环境
	 * @param cback 回调方法
	 */
	/*
	public static void getLatesAndroidVersion(final Context context,String type,final JsonHttpResponseHandler cback){
		//type参数，1表示IOS，2表示Android
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("type", "android");
			jsonObj.put("carrier", CARRIER);
			jsonObj.put("appname", APPNAME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET, "-- getLastAndroidVersion --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_GETLASTESTVERSION, jsonObj, cback);
		LSLog.e(LSLog.LS_NET, "URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_GETLASTESTVERSION);
		LSLog.e(LSLog.LS_NET, "jsonObj is " + jsonObj.toString());
	}
	*/
	
	/**
	 * 获取最新APP配置文件信息
	 * @param context 设备上下文环境
	 * @param cback 回调方法
	 */
	/*
	public static void getLatesPropertiesVersion(final Context context,double version,String fileName,final JsonHttpResponseHandler cback){
		JSONObject jsonObj = new JSONObject(); 
		try {
			jsonObj.put("type", "android");
			jsonObj.put("carrier", CARRIER);
			jsonObj.put("appname", APPNAME);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LSLog.e(LSLog.LS_NET, "-- getLastPropertiesVersion --");
		NetManager.post(context, UrlBuilder.URL_BASE + UrlBuilder.URL_GETLASTESTCONFIG, jsonObj, cback);
		LSLog.e(LSLog.LS_NET, "URL is " + UrlBuilder.URL_BASE + UrlBuilder.URL_GETLASTESTCONFIG);
		LSLog.e(LSLog.LS_NET, "jsonObj is " + jsonObj.toString());
	}
	*/
	
	//下载最新配置文件
	/*
	public static void downloadPropertiesVerson(final Context context,final double version, final String fileName){
		
		getLatesPropertiesVersion(context, version, fileName, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				super.onSuccess(statusCode, response);
				if(response.optInt("responseCode")==200){
					JSONObject responseAppConfig = response.optJSONObject("appConfig");
					if(responseAppConfig!=null){
						String responseUrl = responseAppConfig.optString("url");
						double responseVersion = responseAppConfig.optDouble("version");
						if(responseVersion > version ){
							if(responseUrl!=null && "".equals(responseUrl)){
								//下载
								NetManager.downloadPropertiesFile(context, responseUrl,fileName);
							}else{
								LSLog.e(LSLog.LS_NET, "错误，下载配置文件的url无效 或Version 无效");
							}
						}else{
							LSLog.e(LSLog.LS_NET, "本版本为最新，不用下载");
						}
					}else{
						LSLog.e(LSLog.LS_NET, "错误，返回appConfig为空");
					}
				}else{
					LSLog.e(LSLog.LS_NET, "错误，下载配置文件失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				System.out.println("错误，网络或服务器异常，下载配置文件失败");
			}
			
		});
	}
	*/
	
	/**
	 * 计算signature的值
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	/*
	protected static String calculateSignature(String accessToken, String timestamp, String nonce){
		//获取accessToken
		String[] tmpArr= {accessToken,timestamp,nonce};
		//计算加密后的字符串
		Arrays.sort(tmpArr);
		String tmpStr = ArrayToString(tmpArr);
		return SHA1Encode(tmpStr);
	}
	*/
	
	
	/**
	 * 下载图片接口
	 * @param context  设备上下文环境
	 * @param url      图片URL
	 * @param cback    回调方法
	 */
	/*
	public static void downloadPicture(Context context,String url,BinaryHttpResponseHandler cback){
		NetManager.get(context,url, null, cback);
	}
	*/
	
	/**
	 * 下载文件接口
	 * @param context 设备上下文环境
	 * @param url     下载文件url
	 * @param cback   回调方法
	 */
	/*
	public static void downloadFile(final Context context, String url,
			final AsyncHttpResponseHandler cback,final boolean snyc) {
		NetManager.get(context, url, null, cback, snyc);
	}
	*/
	/*
	private static void downloadFile(final Context context,String downUrl,final DownloadFileCallback cback){
		String savePath=context.getCacheDir().getAbsolutePath()+"/app";
		final String url=downUrl;
		final String out = savePath;
		new Thread(new Runnable() {
			public void run() {
				try {
					DownloadFileTask dlfk = new DownloadFileTask(url,out,cback);
					dlfk.DownAndEctract();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}*/
	
	/**
	 * 下载数据
	 * @param context
	 * @param ts
	 * @param callback
	 * @param async
	 */
	/*
	public static void downloadFileData( Context context,long ts,final DownloadFileCallback callback,final Boolean async){
		final Context mContext=context;
		download(mContext, ts, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				try {
					if (response.getInt("responseCode")==200) {
						String url = response.optString("fileUrl");
						final long nextTs = response.optLong("nextTs");
						if (url!=null) {
							downloadFile(mContext, url, new DownloadFileCallback(){
								@Override
								public void onSuccess(int code,JSONObject response) {
									JSONObject json = new JSONObject();
									try {
										json.put("nextTs", nextTs);
										json.put("downloadFile", response);
										callback.onSuccess(200, json);
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							});
						}else {
							callback.onError(400,"下載文件地址為空");
						}
						
					}else{
						callback.onError(500,response.toString());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				callback.onError(500,content);
			}
			
		}, async);
	}
	*/
	
	//获取设备图片接口
	/*
	public static void getDevicePic(final Context context,String pic,final BinaryHttpResponseHandler cback){
		getDevicePicture(context, pic, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				if (response.optInt("responseCode")==200) {
					if (response.optString("pictureUrl")!=null && !"".equals(response.optString("pictureUrl"))) {
					
						downloadPicture(context, response.optString("pictureUrl"),cback);
					}else{
						cback.onFailure(null, "找不到设备url，服务器设备url为空");
					}
				}else{
					cback.onFailure(null, "服务器有误，获取不了设备url");
				}
			}
		}, true);
	}
	*/
	
	//设置成员目标
	/*
	public static void saveSetMemberGoal(Context context,
			MemberGoal memberGoal, final JsonHttpResponseHandler cback,
			final boolean async) {

		if (NetManager.sessionId == null || "".equals(NetManager.sessionId)) {
			// 返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance()
				.getTimeInMillis());
		String nonce = Integer.toString((int) (Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url = toStrURL(UrlBuilder.URL_BASE,
				UrlBuilder.URL_SET_MEMBER_GOAL, signature, timestamp, nonce,
				NetManager.sessionId);
		LSLog.e(LSLog.LS_NET, "memberGoalJson URL is  " + url);
		LSLog.e(LSLog.LS_NET, "-- 设置成员目标 --步行-睡眠--睡眠设置");
		JSONObject memberGoalJson = new MemberGoalRequest()
				.toJsonObject(memberGoal);
		NetManager.post(context, url, memberGoalJson, cback, async);
		LSLog.e(LSLog.LS_NET,
				"设置成员目标 --jsonObj is " + memberGoalJson.toString());

	}
	*/
	
	// 设置成员目标
	/*
	public static void saveSetSleepGoal(Context context, SleepTime sleepGoal,
			final JsonHttpResponseHandler cback, final boolean async) {
		if (NetManager.sessionId == null || "".equals(NetManager.sessionId)) {
			// 返回错误信息
			return;
		}
		String timestamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String nonce = Integer.toString((int) (Math.random() * 100000));
		String signature = calculateSignature(timestamp, nonce);
		String url = toStrURL(UrlBuilder.URL_BASE,
				UrlBuilder.URL_SET_MEMBER_GOAL, signature, timestamp, nonce,
				NetManager.sessionId);
		LSLog.e(LSLog.LS_NET, "memberGoalJson URL is  " + url);

		LSLog.e(LSLog.LS_NET, "-- 设置睡眠目標--");

		NetManager.post(context, url, sleepGoal.toJsonObject(), cback, async);
		LSLog.e(LSLog.LS_NET,
				"设置睡眠目標 --jsonObj is " + sleepGoal.toJsonObject().toString());
	}
	*/
	
	//获取第三方图片
	/**
	 * 
	 * @param context
	 * @param picName   第三方图片名
	 * @param type		第三方登录类型
	 * @param cback
	 */
	/*
	public static void getThirdPartyPic(Context context,String picName,int type,DownloadPicCallback cback){
		LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String,String>();
		//head_portrait = picName;
		if (type==6) {
			linkedHashMap.put("userNick", picName);
			linkedHashMap.put("width", "100");
			linkedHashMap.put("height", "100");
			linkedHashMap.put("type", "sns");
		}
		String url = getUrlWithQueryString(UrlBuilder.URL_BASEE_THIRDPARTY, linkedHashMap);
		NetManager.get(url, cback);
	}
	*/
	
	/*
	private static String getUrlWithQueryString(String url,
			LinkedHashMap<String, String> map) {
		if (map != null) {
			Iterator<?> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				Object key = keys.next();
				if (url.indexOf("?") == -1) {
					url += "?" + key + "=" + (map.get(key));
				} else {
					url += "&" + key + "=" + (map.get(key));
				}
			}

		}
		return url;
	}
	*/
	
	/**
	 * 组装URL
	 * @param url
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	protected static String buildStrURL(String url,String signature,String timestamp,
			String nonce){
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("?");
		buffer.append("signature=" + signature);
		buffer.append("&");
		buffer.append("timestamp=" + timestamp);
		buffer.append("&");
		buffer.append("nonce=" + nonce);
		buffer.append("&");
		return buffer.toString();
	}

	/**
	 * 组装URL
	 * @param url
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param sessionId
	 * @return
	 */
	protected static String buildStrURL(String url,String signature,String timestamp,
			String nonce,String sessionId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("?");
		buffer.append("signature=" + signature);
		buffer.append("&");
		buffer.append("timestamp=" + timestamp);
		buffer.append("&");
		buffer.append("nonce=" + nonce);
		buffer.append("&");
		buffer.append("sessionId=" +sessionId);
		return buffer.toString();
	}
	
	/**
	 * 计算Signature
	 * @param timestamp
	 * @param nonce
	 * @param accessToken
	 * @return
	 */
	protected static String calculateSignature(String accessToken, String timestamp, String nonce){
		String[] tmpArr= {accessToken,timestamp,nonce};
		//计算加密后的字符串
		Arrays.sort(tmpArr);
		String tmpStr = ArrayToString(tmpArr);
		return SHA1Encode(tmpStr);
	}
	
	/**
	 * 将字符串数组转换成字符串
	 * @param array
	 * @return
	 */
	private static String ArrayToString(String[] array) {
		StringBuffer bf = new StringBuffer();
		for (String str : array) {
			bf.append(str);
		}
		return bf.toString();
	}
	
	/**
	 * 计算SHA1
	 * @param sourceString
	 * @return
	 */
	private static String SHA1Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	/**
	 * 将byte转换为十六进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toLowerCase();
	}
	

}
