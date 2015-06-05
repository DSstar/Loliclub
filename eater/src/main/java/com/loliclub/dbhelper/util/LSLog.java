package com.loliclub.dbhelper.util;

import android.util.Log;

/**
 * Log输出工具类
 *
 */
public class LSLog {

	private static boolean enableLog = false;

	public static final void setEnalbeLog(boolean enable) {
		enableLog = enable;
	}

	public static final boolean isEnalbeLog() {
		return enableLog;
	}

	public static final void d(String tag, String msg) {
		if (enableLog)
			Log.d(tag, msg);
	}

	public static final void e(String tag, String msg) {
		if (enableLog)
			Log.e(tag, msg);
	}

	public static final void i(String tag, String msg) {
		if (enableLog)
			Log.i(tag, msg);
	}

	public static final void v(String tag, String msg) {
		if (enableLog)
			Log.v(tag, msg);
	}

	public static final void w(String tag, String msg) {
		if (enableLog)
			Log.w(tag, msg);
	}

}
