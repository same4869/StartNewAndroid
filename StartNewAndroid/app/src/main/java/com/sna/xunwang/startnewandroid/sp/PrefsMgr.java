package com.sna.xunwang.startnewandroid.sp;

import android.content.SharedPreferences;

import com.sna.xunwang.startnewandroid.app.SNAApplication;

public class PrefsMgr {

	public static int getInt(String tbl, String key, int def) {
		if (SNAApplication.getInstance() == null) {
			return def;
		}
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		return prefs.getInt(key, def);
	}

	public static long getLong(String tbl, String key, long def) {
		if (SNAApplication.getInstance() == null) {
			return def;
		}
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		return prefs.getLong(key, def);
	}

	public static String getString(String tbl, String key, String def) {
		if (SNAApplication.getInstance() == null) {
			return def;
		}
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		return prefs.getString(key, def);
	}

	public static boolean getBoolean(String tbl, String key, boolean def) {
		if (SNAApplication.getInstance() == null) {
			return def;
		}
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		return prefs.getBoolean(key, def);
	}

	public static float getFloat(String tbl, String key, float def) {
		if (SNAApplication.getInstance() == null) {
			return def;
		}

		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		return prefs.getFloat(key, def);
	}

	public static void putInt(String tbl, String key, int value) {
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public static void putLong(String tbl, String key, long value) {
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public static void putString(String tbl, String key, String value) {
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.apply();
	}

	public static void putBoolean(String tbl, String key, boolean value) {
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public static void putFloat(String tbl, String key, float value) {
		SharedPreferences prefs = SNAApplication.getInstance().getSNASharedPreferences(tbl);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putFloat(key, value);
		editor.apply();
	}
}
