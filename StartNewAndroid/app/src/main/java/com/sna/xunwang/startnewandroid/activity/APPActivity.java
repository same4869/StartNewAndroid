package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.AppRecAdapter;
import com.sna.xunwang.startnewandroid.bean.AppRecBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class APPActivity extends BaseActivity {
    @BindView(R.id.app_recyclerview)
    RecyclerView recyclerView;

    private AppRecAdapter adapter;
    private List<AppRecBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_app;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initAppData();

        adapter = new AppRecAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
    }

    private void initAppData(){
        AppRecBean appRecBean1 = new AppRecBean();
        appRecBean1.setAppImgSrc(R.drawable.about_icon);
        appRecBean1.setAppTitle("千帆知车");
        appRecBean1.setAppContent("做最有亲和力的汽车社区");
        list.add(appRecBean1);

        AppRecBean appRecBean2 = new AppRecBean();
        appRecBean2.setAppImgSrc(R.drawable.collect_icon);
        appRecBean2.setAppTitle("千帆抢红包");
        appRecBean2.setAppContent("无广告无插件无后门的抢红包神器");
        list.add(appRecBean2);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {

    }
}