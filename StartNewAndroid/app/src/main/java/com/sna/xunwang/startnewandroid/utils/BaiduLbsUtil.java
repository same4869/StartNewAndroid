package com.sna.xunwang.startnewandroid.utils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.sna.xunwang.startnewandroid.config.Constants;

/**
 * Created by xunwang on 16/11/8.
 */

public class BaiduLbsUtil {
    private static NotifyListener mNotifyer = new NotifyListener();
    private static BaiduNotifyListener mBaiduNotifyListener;

    public static class NotifyListener extends BDNotifyListener {
        public void onNotify(BDLocation mlocation, float distance) {
            XLog.d(Constants.TAG, "onNotify");
            if (mBaiduNotifyListener != null) {
                mBaiduNotifyListener.onBDNotify(mlocation, distance);
            }
        }
    }

    public static void setNotifyLocation(LocationClient mLocationClient, double v, double v1, float v2, String s) {
        mNotifyer.SetNotifyLocation(v, v1, v2, s);
        mLocationClient.registerNotify(mNotifyer);
        XLog.d(Constants.TAG, "setNotifyLocation");
    }

    public static void setBaiduNotifyListener(BaiduNotifyListener baiduNotifyListener) {
        mBaiduNotifyListener = baiduNotifyListener;
    }

    public interface BaiduNotifyListener {
        void onBDNotify(BDLocation mlocation, float distance);
    }
}
