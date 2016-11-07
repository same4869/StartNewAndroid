package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;

/**
 * Created by xunwang on 16/11/7.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("kkkkkkkk", "BaseActivity");
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        initToolBar();
        initData();
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();

    public abstract void initData();
}
