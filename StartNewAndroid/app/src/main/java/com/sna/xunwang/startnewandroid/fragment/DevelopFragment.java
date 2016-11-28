package com.sna.xunwang.startnewandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.DevelopItemAdapter;
import com.sna.xunwang.startnewandroid.bean.DevelopItemBean;

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

    private DevelopItemAdapter adapter;
    private List<DevelopItemBean> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_develop;
    }

    @Override
    public void lazyFetchData() {
//        list.clear();
//
//        DevelopItemBean developItemBean = new DevelopItemBean();
//        developItemBean.setItemImgUrl("http://pic5.ablesky" +
//                ".cn/content/pic/coursephoto/2015/03/25/8f40c6ac-ae97-42b6-b803-1abe2c5f38e3.png");
//        developItemBean.setItemTitle("测试");
//        developItemBean.setItemContent("我是测试我是测试");
//        developItemBean.setItemUrl("http://www.7724.com/");
//        list.add(developItemBean);
//        adapter.notifyDataSetChanged();
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
    }
}
