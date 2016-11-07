package com.sna.xunwang.startnewandroid.utils;

import android.util.Log;

import com.sna.xunwang.startnewandroid.config.Constants;

/**
 * Created by xunwang on 16/11/7.
 */

public class XLog {
    public static final String LOG = XLog.class.getSimpleName();

    public static boolean isDebug() {
        return Constants.IS_DEBUG;
    }

    public static void d(String tag, String message) {
        if (!isDebug() || message == null)
            return;
        Log.d(tag == null ? LOG : tag, message);
    }

    public static void e(String tag, String message) {
        if (!isDebug() || message == null)
            return;
        Log.e(tag == null ? LOG : tag, message);
    }

    public static void w(String tag, String message) {
        if (!isDebug() || message == null)
            return;
        Log.w(tag == null ? LOG : tag, message);
    }

    public static void i(String tag, String message) {
        if (!isDebug() || message == null)
            return;
        Log.i(tag == null ? LOG : tag, message);
    }

    public static void wtf(String tag, String message) {
        if (!isDebug() || message == null)
            return;
        Log.wtf(tag == null ? LOG : tag, message);
    }

    public static void w(String tag, Throwable th) {
        if (!isDebug())
            return;
        Log.w(tag == null ? LOG : tag, "exception", th);
    }

}
