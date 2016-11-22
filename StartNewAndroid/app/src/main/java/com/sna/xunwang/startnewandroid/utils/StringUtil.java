package com.sna.xunwang.startnewandroid.utils;

public class StringUtil {
	public static boolean isStringNullorBlank(String string) {
		if (string == null) {
			return true;
		}
		if (string.equals("")) {
			return true;
		}
		return false;
	}
}
