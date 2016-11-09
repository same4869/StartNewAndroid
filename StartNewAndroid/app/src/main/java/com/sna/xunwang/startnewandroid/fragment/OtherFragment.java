package com.sna.xunwang.startnewandroid.fragment;

import android.content.Intent;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.OtherActivity;
import com.sna.xunwang.startnewandroid.bean.BiezhiGoodBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.manager.BaiduLocationManager;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xunwang on 16/11/9.
 */

public class OtherFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
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
    void test3() {
        Intent intent = new Intent(getActivity(), OtherActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.test_btn4)
    void test4() {
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add((int) (Math.random() * 5000) + "");
        }
        requesetBiezhiGoodsInfo(lists);
    }

    private void requesetBiezhiGoodsInfo(List<String> randoms) {
        BmobQuery<BiezhiGoodBean> query = new BmobQuery<>();
        query.setLimit(10).addWhereContainedIn("id", randoms)
                .findObjects(new FindListener<BiezhiGoodBean>() {
                    @Override
                    public void done(List<BiezhiGoodBean> object, BmobException e) {
                        if (e == null) {
                            for (BiezhiGoodBean biezhiGoodBean : object) {
                                XLog.d(Constants.TAG, "biezhiGoodBean.getTitle() --> " + biezhiGoodBean.getTitle());
                            }
                        } else {
                            XLog.d(Constants.TAG, "error：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
    }
}
