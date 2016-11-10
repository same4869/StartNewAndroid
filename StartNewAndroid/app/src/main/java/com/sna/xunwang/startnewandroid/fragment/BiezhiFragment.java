package com.sna.xunwang.startnewandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.BiezhiGoodsAdapter;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.TimeUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;
import com.sna.xunwang.startnewandroid.view.NextRefreshCountDownTimerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xunwang on 16/11/7.
 */

public class BiezhiFragment extends BaseFragment {
    @BindView(R.id.biezhi_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.biezhi_countdown_view)
    NextRefreshCountDownTimerView nextRefreshCountDownTimerView;
    private BiezhiGoodsAdapter biezhiGoodsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_biezhi;
    }

    @Override
    public void lazyFetchData() {
        prepareRequeset();
    }

    @Override
    public void initViews() {
        biezhiGoodsAdapter = new BiezhiGoodsAdapter(getActivity(), null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(biezhiGoodsAdapter);
        TimeUtil.calcNextTime(nextRefreshCountDownTimerView, System.currentTimeMillis());

    }

    private void prepareRequeset() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < Constants.EVER_TIME_SHOW; i++) {
            lists.add(String.valueOf((int) (Math.random() * 2000)));
        }
        requesetBiezhiGoodsInfo(lists);
    }

    private void requesetBiezhiGoodsInfo(List<String> randoms) {
        BmobQuery<BiezhiGoodBean> query = new BmobQuery<>();
        query.setLimit(Constants.EVER_TIME_SHOW).addWhereContainedIn("id", randoms)
                .findObjects(new FindListener<BiezhiGoodBean>() {
                    @Override
                    public void done(List<BiezhiGoodBean> object, BmobException e) {
                        if (e == null) {
                            for (BiezhiGoodBean biezhiGoodBean : object) {
                                XLog.d(Constants.TAG, "biezhiGoodBean.getTitle() --> " + biezhiGoodBean.getTitle());
                            }
                            biezhiGoodsAdapter.setData(object);
                        } else {
                            XLog.d(Constants.TAG, "error：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
    }

}
