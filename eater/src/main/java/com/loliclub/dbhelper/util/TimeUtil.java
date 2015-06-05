package com.loliclub.dbhelper.util;

import java.util.Calendar;

/**
 * 时间工具类
 */
public class TimeUtil {

	public static final String TAG = "TimeUtil";

	/**
	 * 获取一小时的第一秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getFirstSecondInHour(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一小时的最后一秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getLastSecondInHour(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一天的第一毫秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getFirstSecondInDay(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一天的最后一毫秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getLastSecondInDay(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一星期的第一毫秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getFirstSecondInWeek(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一星期的最后一毫秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getLastSecondInWeek(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一星期的第一毫秒
	 * 
	 * @param timeInMillis
	 * @param dayOfWeek
	 *            周的第一天。若为负数，则不设置,参照Calendar
	 * @return
	 */
	public static long getFirstSecondInWeek(long timeInMillis, int dayOfWeek) {
		if (dayOfWeek < 1 || dayOfWeek > 7) {
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(dayOfWeek);
			calendar.setTimeInMillis(timeInMillis);
			calendar.set(Calendar.DAY_OF_WEEK,
					calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND,
					calendar.getActualMinimum(Calendar.MILLISECOND));
			return calendar.getTimeInMillis();
		} else 
			return getFirstSecondInWeek(timeInMillis);
	}

	/**
	 * 获取一星期的最后一毫秒
	 * 
	 * @param timeInMillis
	 * @param dayOfWeek
	 *            周的第一天。若为负数，则不设置,参照Calendar
	 * @return
	 */
	public static long getLastSecondInWeek(long timeInMillis, int dayOfWeek) {
		if (dayOfWeek < 1 || dayOfWeek > 7) {
			// 从第一天开始计算，返回加7天之后的最后一秒
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(dayOfWeek);
			calendar.setTimeInMillis(timeInMillis);
			calendar.set(Calendar.DAY_OF_WEEK,
					calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
			calendar.add(Calendar.DAY_OF_WEEK, 6);
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE,
					calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND,
					calendar.getActualMaximum(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND,
					calendar.getActualMaximum(Calendar.MILLISECOND));
			return calendar.getTimeInMillis();
		} else {
			// 无效参数以及dayOfWeek == Sunday时，返回星期六最后一毫秒
			// Sunday == 1
			return getLastSecondInWeek(timeInMillis);
		}
	}

	/**
	 * 获取一个月的第一毫秒
	 * 
	 * @param startTimeMillis
	 * @return
	 */
	public static long getFirstSecondInMonth(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一个月的最后一毫秒
	 * 
	 * @param startTimeMillis
	 * @return
	 */
	public static long getLastSecondInMonth(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一年的第一毫秒
	 * 
	 * @param TimeInMillis
	 * @return
	 */
	public static long getFirstSecondInYear(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取一年的最后一毫秒
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static long getLastSecondInYear(long timeInMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,
				calendar.getActualMaximum(Calendar.MILLISECOND));
		return calendar.getTimeInMillis();
	}

}
