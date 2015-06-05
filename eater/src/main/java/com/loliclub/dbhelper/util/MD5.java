package com.loliclub.dbhelper.util;

import java.security.MessageDigest;
import java.util.Locale;

public class MD5 {

	/**
	 * MD5加密
	 * @param str 需要加密的字符串
	 * @param isToUpperCase 是否使用大写加密
	 * @return
	 */
	public static String makeMD5(String str, boolean isToUpperCase) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		if (isToUpperCase)
			return hexValue.toString().toUpperCase(Locale.getDefault());
		else
			return hexValue.toString();
	}

}
