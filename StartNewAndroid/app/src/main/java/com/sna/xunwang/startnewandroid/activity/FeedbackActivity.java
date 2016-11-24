package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.adapter.FeedbackUIAdapter;
import com.sna.xunwang.startnewandroid.bean.FeedbackToBmobBean;
import com.sna.xunwang.startnewandroid.bean.FeedbackUIBean;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.StringUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.util.Date;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FeedbackActivity extends BaseActivity {
    private FeedbackUIAdapter adapter;
    private UserBean userBean;
    private LinkedList<FeedbackUIBean> sList = null;
    private LinkedList<FeedbackUIBean> beans = null;

    @BindView(R.id.feedback_lvMessages_rv)
    RecyclerView messageRv;
    @BindView(R.id.feedback_edt)
    EditText enterTx;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        userBean = UserUtil.getUserInfo();

        sList = new LinkedList<FeedbackUIBean>();
        beans = new LinkedList<FeedbackUIBean>();
        String[] msg = new String[]{"您好，我是刚被您召唤出来的千帆冷冷，你可以问我一些汽车百科，太难的我可不会噢，偷偷告诉你，问其他的其实我也会噢~"};

        // 0 是教师； 1 是学生
        for (int i = 0; i < 1; i++) {
            sList.add(new FeedbackUIBean(msg[i], R.drawable.about_icon, "", 1));
        }

        // 归放到 同一个 类集合Bean中
        for (int j = 0; j < sList.size(); j++) {
            beans.add(sList.get(j));
        }

        adapter = new FeedbackUIAdapter(this, beans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRv.setLayoutManager(linearLayoutManager);
        messageRv.setNestedScrollingEnabled(false);
        messageRv.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        messageRv.setHasFixedSize(true);
    }

    @OnClick(R.id.feedback_enter)
    void startSend() {
        if (!StringUtil.isStringNullorBlank(enterTx.getText().toString())) {
            sendMessageToDev(enterTx.getText().toString());
        }
    }

    private void sendMessageToDev(String msg) {
        if (StringUtil.isStringNullorBlank(msg)) {
            ToastUtil.showToast(getApplicationContext(), "内容不要为空噢", TastyToast.ERROR);
            return;
        }
        if (userBean == null) {
            ToastUtil.showToast(getApplicationContext(), "请先登录哈", TastyToast.INFO);
            finish();
        }
        XLog.d(Constants.TAG, "sendMessageToDev");
        updateFeedbackMsgToBmob(userBean.getUsername(), msg);
        adapter.addItemNotifiChange(new FeedbackUIBean(msg, R.drawable.about_icon, new Date() + "", 0));
    }

    private void updateFeedbackMsgToBmob(String username, String text) {
        final FeedbackToBmobBean feedbackToBmobBean = new FeedbackToBmobBean();
        feedbackToBmobBean.setUsername(username);
        feedbackToBmobBean.setText(text);
        feedbackToBmobBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    XLog.d(Constants.TAG, "添加反馈数据成功 用户名 --》" + feedbackToBmobBean.getUsername() + " 消息 --》" +
                            feedbackToBmobBean.getText());
                } else {
                    XLog.d(Constants.TAG, "添加反馈数据失败 e.getMessage() --> " + e.getMessage());
                }
            }
        });
    }
}
