package com.loliclub.dbhelper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Bean工具类
 * 
 */
public class BeanUtil {

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	
	public static final SimpleDateFormat FORMAT_DAY = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.getDefault());
	
	public static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat(
			"HH:mm:ss", Locale.getDefault());
			
	/**
	 * 生成16位UUID
	 * 
	 * @return
	 */
	public static String generateId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 日期转换成字符串,格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if(date == null)
			return null;
		return FORMAT.format(date);
	}
	
	/**
	 * 字符串转换成日期，格式为yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String dateToDayString(Date date) {
		if(date == null)
			return null;
		return FORMAT_DAY.format(date);
	}
	
	/**
	 * 字符串转换成时间，格式为HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToTimeString(Date date) {
		if(date == null)
			return null;
		return FORMAT_TIME.format(date);
	}
	
	/**
	 * 字符串转换成日期，格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String string) throws ParseException {
		if(string == null || "".equals(string) || "null".equals(string))
			return null;
		return FORMAT.parse(string);
	}
	
	/**
	 * 字符串转换成日期，格式为yyyy-MM-dd
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDayDate(String string) throws ParseException {
		if(string == null || "".equals(string) || "null".equals(string))
			return null;
		return FORMAT_DAY.parse(string);
	}
	
	/**
	 * 字符串转换成日期，格式为HH:mm:ss
	 * @param string
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToTimeDate(String string) throws ParseException {
		if(string == null || "".equals(string) || "null".equals(string))
			return null;
		return FORMAT_TIME.parse(string);
	}
	
}
