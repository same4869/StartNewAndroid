package com.sna.xunwang.startnewandroid.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.BiezhiGoodsAdapter;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.db.BiezhiGoodsDBHelper;
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
    private LinearLayoutManager linearLayoutManager;

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
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(biezhiGoodsAdapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        TimeUtil.calcNextTime(nextRefreshCountDownTimerView, System.currentTimeMillis());

    }

    private void prepareRequeset() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < Constants.EVER_TIME_SHOW; i++) {
            //暂时展示13000-14000的数据,后续再定逻辑
            String num = String.valueOf((int) (Math.random() * 1000) + 13000);
            lists.add(num);
            XLog.d(Constants.TAG, "num --> " + num);
        }
        requesetBiezhiGoodsInfo(lists);
    }

    private void requesetBiezhiGoodsInfo(List<String> randoms) {
        BmobQuery<BiezhiGoodsBean> query = new BmobQuery<>();
        query.setLimit(Constants.EVER_TIME_SHOW).addWhereContainedIn("id", randoms)
                .findObjects(new FindListener<BiezhiGoodsBean>() {
                    @Override
                    public void done(List<BiezhiGoodsBean> object, BmobException e) {
                        if (e == null) {
                            XLog.d(Constants.TAG, "object.size() --> " + object.size());
                            for (int i = 0; i < object.size(); i++) {
                                XLog.d(Constants.TAG, "biezhiGoodBean.getTitle() --> " + object.get(i).getTitle());
                                BiezhiGoodsDBHelper.getInstance().save(object.get(i));
                            }
                            biezhiGoodsAdapter.setData(object);
                        } else {
                            XLog.d(Constants.TAG, "error：" + e.getMessage() + "," + e.getErrorCode());
                            List<BiezhiGoodsBean> datas = BiezhiGoodsDBHelper.getInstance().getAllData();
                            biezhiGoodsAdapter.setData(datas);
                        }
                    }
                });
    }


}
