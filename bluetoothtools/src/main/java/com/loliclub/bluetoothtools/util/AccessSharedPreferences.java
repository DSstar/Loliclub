package com.loliclub.bluetoothtools.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * SharePreferences Util
 * 
 */
public class AccessSharedPreferences {

	public static synchronized void saveProperty(Context context,
			String fileName, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static synchronized void saveProperty(Context context,
			String fileName, String key, boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static synchronized void saveProperty(Context context,
			String fileName, String key, int value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static synchronized void saveProperty(Context context,
			String fileName, String key, float value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static synchronized void saveProperty(Context context,
			String fileName, String key, long value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public static synchronized String readProperty(Context context,
			String fileName, String key, String defvalue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defvalue);
	}

	public static synchronized boolean readProperty(Context context,
			String fileName, String key, boolean defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static synchronized int readProperty(Context context,
			String fileName, String key, int defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defValue);
	}

	public static synchronized float readProperty(Context context,
			String fileName, String key, float defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getFloat(key, defValue);
	}

	public static synchronized long readProperty(Context context,
			String fileName, String key, long defValue) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, defValue);
	}

	public static synchronized Map<String, ?> readProperty(Context context,
			String fileName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getAll();
	}

	public static synchronized void removeData(Context context,
			String fileName, String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}

	public static synchronized void clearData(Context context, String fileName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

}
