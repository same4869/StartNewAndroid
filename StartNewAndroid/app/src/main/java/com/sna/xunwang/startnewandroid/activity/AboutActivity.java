package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;

import butterknife.OnClick;

public class AboutActivity extends BaseActivity {
    private long goToDevModeTime;
    private int goToDevModeCount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.about_icon)
    void gotoDevMode() {
        if ((System.currentTimeMillis() - goToDevModeTime) > 2000) {
            goToDevModeTime = System.currentTimeMillis();
            goToDevModeCount = 0;
        } else {
            goToDevModeCount++;
            if (goToDevModeCount > 9) {
                ToastUtil.showToast(getApplicationContext(), "进入开发者模式");
            }
        }
    }

}
