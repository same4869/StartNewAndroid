package com.sna.xunwang.startnewandroid.sp;

/**
 * Created by xunwang on 16/11/22.
 */

public class SNASetting {
    public static final String SETTING = "setting";
    private static final String BIEZHI_REFRESH_TIME = "biezhi_refresh_time";

    public static void setBiezhiRefreshTime(String time){
        PrefsMgr.putString(SETTING, BIEZHI_REFRESH_TIME, time);
    }

    public static String getBiezhiRefreshTime() {
        return PrefsMgr.getString(SETTING, BIEZHI_REFRESH_TIME, null);
    }
}
