package com.sna.xunwang.startnewandroid.manager;

import android.content.Context;

import com.iflytek.autoupdate.IFlytekUpdate;
import com.iflytek.autoupdate.IFlytekUpdateListener;
import com.iflytek.autoupdate.UpdateConstants;
import com.iflytek.autoupdate.UpdateInfo;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.XLog;

/**
 * Created by xunwang on 16/12/6.
 */

public class UpdateManager {

    private static UpdateManager instance = null;

    private UpdateManager() {

    }

    public static UpdateManager getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new UpdateManager();
        }
    }

    //自动更新回调方法,详情参考demo
    IFlytekUpdateListener updateListener = new IFlytekUpdateListener() {
        @Override
        public void onResult(int errorcode, UpdateInfo result) {
            XLog.d(Constants.TAG, "updateListener errorcode --> " + errorcode + " result.getDownloadUrl() --> " +
                    result.getDownloadUrl());
        }
    };

    public void startCheckUpdate(Context context) {
        //初始化自动更新对象
        IFlytekUpdate updManager = IFlytekUpdate.getInstance(context);
        //开启调试模式,默认不开启
        updManager.setDebugMode(true);
        //开启wifi环境下检测更新,仅对自动更新有效,强制更新则生效
        updManager.setParameter(UpdateConstants.EXTRA_WIFIONLY, "true");
        //设置通知栏使用应用icon,详情请见示例
        updManager.setParameter(UpdateConstants.EXTRA_NOTI_ICON, "true");
        //设置更新提示类型,默认为通知栏提示
        updManager.setParameter(UpdateConstants.EXTRA_STYLE, UpdateConstants.UPDATE_UI_DIALOG);
        // 启动自动更新
        updManager.autoUpdate(context, null);

    }
}
