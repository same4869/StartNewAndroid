package com.sna.xunwang.startnewandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.BiezhiGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xunwang on 16/11/7.
 */

public class BiezhiFragment extends BaseFragment {
    @BindView(R.id.biezhi_recyclerview)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_biezhi;
    }

    @Override
    public void lazyFetchData() {

    }

    @Override
    public void initViews() {
        List<String> mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new BiezhiGoodsAdapter(getActivity(), mDatas));
        recyclerView.setNestedScrollingEnabled(false);

    }

}
