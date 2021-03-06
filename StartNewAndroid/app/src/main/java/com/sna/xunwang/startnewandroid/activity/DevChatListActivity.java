package com.sna.xunwang.startnewandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.DevChatAdapter;
import com.sna.xunwang.startnewandroid.bean.DevelopItemBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class DevChatListActivity extends BaseActivity {
    @BindView(R.id.dev_lvMessages_rv)
    RecyclerView recyclerView;

    private DevChatAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_dev_chat;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
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

    @OnClick(R.id.check_develop)
    void checkDev() {
        setDevData();
    }

    private void setDevData() {
        DevelopItemBean developItemBean = new DevelopItemBean();
        developItemBean.setItemTitle("植物大战僵尸");
        developItemBean.setItemContent("一款深受全球玩家所推崇的休闲益智游戏");
        developItemBean.setItemImgUrl("http://img.7724.com/pipaw/logo/2015/01/20/629b051156056e7be22880a4b221c4af.png");
        developItemBean.setItemUrl("http://play.7724.com/olgames/zwdzjs/");
        developItemBean.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(getApplicationContext(), "hehe");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void initData() {
        requestUsers();
        adapter.setOnItemClickListener(new DevChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(DevChatListActivity.this, DevChatDetailActivity.class);
                intent.putExtra(DevChatDetailActivity.USER_NAME_KEY, list.get(position));
                startActivity(intent);
            }
        });
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
                        list.add(object.get(i).getUsername());
                        adapter.setData(list);
                    }
                } else {
                    XLog.d(Constants.TAG, e.getMessage());
                }
            }
        });
    }
}
