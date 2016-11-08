package com.sna.xunwang.startnewandroid.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;
import com.sna.xunwang.startnewandroid.R;

public class OtherActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_other;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, Color.parseColor("#ffff00"));
    }

    @Override
    public void initData() {

    }
}
