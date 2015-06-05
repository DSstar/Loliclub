package com.loliclub.eater.properties;

public class UrlBuilder {

	public static String URL_BASE = "http://healthcenter2-hk.lifesense.com/healthcenter-personal";
	
	/** webSocket接口URL **/
	public static final String APPSERVER = "http://healthcenter2-hk.lifesense.com/healthcenter-personal";
	public static final String WS_SERVER_URL = "ws://healthcenter2-hk.lifesense.com/websocket-message/connect";

	/** 帐号服务接口 **/
	public static final String URL_SIGNUP = URL_BASE + "/personal/account_service/create_new_account";
	public static final String URL_CHECKUSERNAME = URL_BASE + "/personal/account_service/is_loginname_available";
	public static final String URL_CHECKCAPTCHA = URL_BASE + "/personal/sms_service/check_captcha";
	public static final String URL_SENDCAPTCHA = URL_BASE + "/personal/sms_service/send_captcha";
	public static final String URL_SIGNIN = URL_BASE + "/personal/account_service/login";
	public static final String URL_CHANGEPASSWORD = URL_BASE + "/personal/account_service/change_password";
	public static final String URL_CHANGEUSERNAME = URL_BASE + "/personal/account_service/change_loginname";
	public static final String URL_CHANGEPROFILE = URL_BASE + "/personal/account_service/change_account_profile";
	public static final String URL_LOGOUT = URL_BASE + "/personal/account_service/logout";
	public static final String URL_RESETPASSWORD = URL_BASE + "/personal/account_service/forget_password";
	public static final String URL_CHANGE_LOGINNAME= URL_BASE + "/personal/account_service/change_loginname";
	public static final String URL_RESETPASSWORD_BYMOBILE= URL_BASE + "/personal/account_service/reset_password_by_mobile";

	/** 成员服务接口 **/
	public static final String URL_SAVEMEMBER = URL_BASE + "/personal/member_service/save";
	public static final String URL_DELETEMEMBER = URL_BASE + "/personal/member_service/delete";

	/** 设备服务接口 **/
	public static final String URL_GETDEVICE = URL_BASE + "/personal/device_service/get_device";
	public static final String URL_GETDEVICEBYMAC = URL_BASE + "/personal/device_service/get_device_by_mac";
	public static final String URL_GETDEVICEBIND = URL_BASE + "/personal/device_service/get_device_binding";
	public static final String URL_GETDEVICEPICTURE = URL_BASE + "/personal/device_service/get_picture";
	public static final String URL_ADDDEVICE = URL_BASE + "/personal/device_service/add_device";
	public static final String URL_BINDMEMBER = URL_BASE + "/personal/device_service/bind_device";
	public static final String URL_UNBINDMEMBER = URL_BASE + "/personal/device_service/unbind_device";
	
	/** 数据服务接口 **/
	public static final String URL_SAVEWEIGHT = URL_BASE + "/personal/weight_record_service/save";
	public static final String URL_DELETEWEIGHT = URL_BASE + "/personal/weight_record_service/delete";
	public static final String URL_SAVEBP = URL_BASE + "/personal/bp_record_service/save";
	public static final String URL_DELETEBP = URL_BASE + "/personal/bp_record_service/delete";
	public static final String URL_SAVEHEIGHT = URL_BASE + "/personal/height_record_service/save";
	public static final String URL_DELETEHEIGHT = URL_BASE + "/personal/height_record_service/delete";
	public static final String URL_DOWNLOAD = URL_BASE + "/personal/sync_record_service/download";
	public static final String URL_UPLOAD = URL_BASE + "/personal/sync_record_service/upload";
	public static final String URL_SAVE_PEDOMETER= URL_BASE + "/personal/pedometer_record_service/save";
	public static final String URL_DELETE_PEDOMETER= URL_BASE + "/personal/pedometer_record_service/delete";
	public static final String URL_SET_MEMBER_GOAL = URL_BASE +  "/personal/goal_record_service/set";
	/** 公共服务接口 **/
	public static final String URL_GETLASTESTVERSION = URL_BASE + "/personal/common_service/get_latest_version";
	public static final String URL_GETLASTESTCONFIG = URL_BASE + "/personal/common_service/get_latest_config";
	
}
