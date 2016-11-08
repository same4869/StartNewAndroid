package com.sna.xunwang.startnewandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sna.xunwang.startnewandroid.utils.XLog;

import butterknife.ButterKnife;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by xunwang on 16/11/7.
 */

public abstract class BaseFragment extends Fragment {
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        isViewPrepared = true;
        View rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewPrepared = false;
        hasFetchData = false;
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true; //已加载过数据
            lazyFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        XLog.d(TAG, "isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {//当当前为显示页面时
            lazyFetchDataIfPrepared();
        }
    }


    public abstract int getLayoutId();

    public abstract void lazyFetchData();

    public abstract void initViews();

}
