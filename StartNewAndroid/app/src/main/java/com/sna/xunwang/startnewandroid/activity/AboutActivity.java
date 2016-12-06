package com.sna.xunwang.startnewandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.version_name)
    TextView versionName;

    private long goToDevModeTime;
    private int goToDevModeCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        versionName.setText("version " + getAppVersionName(getApplicationContext()));
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.about_use_rule)
    void useRule() {
        ToastUtil.showToast(getApplicationContext(), "爱祖国,爱和平!", TastyToast.CONFUSING);
    }

    @OnClick(R.id.app_logo)
    void gotoDevMode() {
        if ((System.currentTimeMillis() - goToDevModeTime) > 2000) {
            goToDevModeTime = System.currentTimeMillis();
            goToDevModeCount = 0;
        } else {
            goToDevModeCount++;
            if (goToDevModeCount > 9) {
                if (Constants.IS_DEBUG) {
                    ToastUtil.showToast(getApplicationContext(), "进入开发者模式");
                    Intent intent = new Intent(AboutActivity.this, DevChatListActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

}
