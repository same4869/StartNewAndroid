package com.sna.xunwang.startnewandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.OtherActivity;
import com.sna.xunwang.startnewandroid.manager.BaiduLocationManager;

import butterknife.OnClick;

/**
 * Created by xunwang on 16/11/7.
 */

public class DoubanFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_douban;
    }

    @Override
    public void lazyFetchData() {

    }

    @Override
    public void initViews() {

    }

    @OnClick(R.id.test_btn1)
    void test1() {
        BaiduLocationManager.getInstance(getActivity()).startGetLocation();
    }

    @OnClick(R.id.test_btn2)
    void test2() {
        BaiduLocationManager.getInstance(getActivity()).setNotifyLocation(31.312463, 121.517617, 10000, "bd09ll");
    }

    @OnClick(R.id.test_btn3)
    void test3(){
        Intent intent = new Intent(getActivity(), OtherActivity.class);
        startActivity(intent);
    }

}
