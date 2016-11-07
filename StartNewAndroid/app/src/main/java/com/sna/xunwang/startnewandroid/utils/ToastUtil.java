package com.sna.xunwang.startnewandroid.utils;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by xunwang on 16/11/7.
 */

public class ToastUtil {
    private static TastyToast toast = null;

    public static void showToast(Context context, String msg) {
        showToast(context, msg, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
    }

    public static void showToast(Context context, String msg, int type) {
        showToast(context, msg, TastyToast.LENGTH_SHORT, type);
    }

    public static void showToast(Context context, String msg, int length, int type) {
        toast.makeText(context, msg, length, type);
    }
}
