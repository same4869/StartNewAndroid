package com.sna.xunwang.startnewandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.DevelopItemAdapter;
import com.sna.xunwang.startnewandroid.bean.DevelopItemBean;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xunwang on 16/11/28.
 */

public class DevelopFragment extends BaseFragment {
    @BindView(R.id.develop_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.develop_cool_wait_view)
    AVLoadingIndicatorView avLoadingIndicatorView;

    private DevelopItemAdapter adapter;
    private List<DevelopItemBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_develop;
    }

    @Override
    public void lazyFetchData() {
        requestDevInfo();
    }

    private void requestDevInfo() {
        BmobQuery<DevelopItemBean> query = new BmobQuery<DevelopItemBean>();
        query.setLimit(20);
        query.findObjects(new FindListener<DevelopItemBean>() {
            @Override
            public void done(List<DevelopItemBean> object, BmobException e) {
                if (e == null) {
                    adapter.setData(object);
                    avLoadingIndicatorView.smoothToHide();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void initViews() {
        adapter = new DevelopItemAdapter(getActivity(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);

        avLoadingIndicatorView.smoothToShow();
    }
}
