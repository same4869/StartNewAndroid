package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.BiezhiGoodsAdapter;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xunwang on 16/11/23.
 */

public class FavActivity extends BaseActivity {
    @BindView(R.id.biezhi_fav_recyclerview)
    RecyclerView recyclerView;

    private BiezhiGoodsAdapter biezhiGoodsAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fav;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        biezhiGoodsAdapter = new BiezhiGoodsAdapter(this, null);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(biezhiGoodsAdapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        if (UserUtil.isLogined()) {
            prepareRequeset();
        } else {
            showNoDataLayout(true);
        }
    }

    protected void showNoDataLayout(boolean isShow) {
        ViewStub stub = (ViewStub) findViewById(R.id.no_data_stub);
        stub.inflate();
        RelativeLayout noDataLayout = (RelativeLayout) findViewById(R.id.base_no_data_layout);
        if (isShow) {
            recyclerView.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noDataLayout.setVisibility(View.GONE);
        }
    }

    private void prepareRequeset() {
        UserBean user = BmobUser.getCurrentUser(UserBean.class);
        BmobQuery<BiezhiGoodsBean> query = new BmobQuery<BiezhiGoodsBean>();
        query.addWhereRelatedTo("favorite", new BmobPointer(user));
        query.order("-createdAt");
        query.setLimit(50);
        BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
        query.addWhereLessThan("createdAt", date);
        query.findObjects(new FindListener<BiezhiGoodsBean>() {
            @Override
            public void done(List<BiezhiGoodsBean> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() == 0) {
                        showNoDataLayout(true);
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setMyFav(true);
                    }
                    biezhiGoodsAdapter.setData(list);
                } else {
                    ToastUtil.showToast(getApplicationContext(), "网络可能不给力,请重试", TastyToast.ERROR);
                }
            }
        });
    }
}
