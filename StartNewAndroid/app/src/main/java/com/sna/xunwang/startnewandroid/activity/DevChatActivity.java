package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.DevChatAdapter;
import com.sna.xunwang.startnewandroid.bean.FeedbackToBmobBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DevChatActivity extends BaseActivity {
    @BindView(R.id.dev_lvMessages_rv)
    RecyclerView recyclerView;

    private DevChatAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_chat;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        list.add("所说的风格的");
        list.add("asdasdfasd");
        list.add("阿斯顿发生");
        adapter = new DevChatAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        requestUsers();
    }

    private void requestUsers() {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> object, BmobException e) {
                if (e == null) {
                    XLog.d(Constants.TAG, "object.size() --> " + object.size());
                    for (int i = 0; i < object.size(); i++) {
                        XLog.d(Constants.TAG, "object.get(i).getUsername() --> " + object.get(i).getUsername());
                        requestUserMsg(object.get(i).getUsername());
                    }
                } else {
                    XLog.d(Constants.TAG, e.getMessage());
                }
            }
        });
    }

    private void requestUserMsg(final String username) {
        BmobQuery<FeedbackToBmobBean> query = new BmobQuery<FeedbackToBmobBean>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<FeedbackToBmobBean>() {
            @Override
            public void done(List<FeedbackToBmobBean> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        XLog.d(Constants.TAG, "username --> " + username + "list.get(i).getText() --> " + list.get(i).getText());
                    }
                } else {

                }
            }
        });
    }
}
