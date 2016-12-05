package com.sna.xunwang.startnewandroid.activity;

import android.content.Intent;
import android.os.Bundle;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.config.Constants;
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

}
