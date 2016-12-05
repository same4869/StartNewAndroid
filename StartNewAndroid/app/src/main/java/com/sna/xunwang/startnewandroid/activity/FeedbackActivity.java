package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class FeedbackActivity extends BaseActivity implements View.OnLayoutChangeListener {
    private FeedbackUIAdapter adapter;
    private UserBean userBean;
    private LinkedList<FeedbackUIBean> beans = new LinkedList<FeedbackUIBean>();

    @BindView(R.id.feedback_lvMessages_rv)
    RecyclerView messageRv;
    @BindView(R.id.feedback_edt)
    EditText enterTx;
    @BindView(R.id.root_view)
    RelativeLayout rootView;

    private int sum;
    private String welcomeStr = "亲,如果对本软件有什么意见和建议都可以在这里直接吐槽,作者会及时跟您沟通~(本条为自动回复)";
    private int screenHeight;
    private int keyHeight;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        rootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void initData() {
        userBean = UserUtil.getUserInfo();
        if (userBean == null) {
            return;
        }

        adapter = new FeedbackUIAdapter(this, beans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        messageRv.setLayoutManager(linearLayoutManager);
        messageRv.setNestedScrollingEnabled(false);
        messageRv.setAdapter(adapter);

        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        messageRv.setHasFixedSize(true);

        requestAllMsgInUser(userBean.getUsername());
    }

    private void initWelcome() {
        adapter.addItemNotifiChange(new FeedbackUIBean(welcomeStr, R.drawable
                .about_icon, new Date() + "", 1));
    }

    @OnClick(R.id.feedback_enter)
    void startSend() {
        if (!StringUtil.isStringNullorBlank(enterTx.getText().toString())) {
            sendMessageToDev(enterTx.getText().toString());
            enterTx.setText("");
        }
    }

    private void sendMessageToDev(final String msg) {
        if (StringUtil.isStringNullorBlank(msg)) {
            ToastUtil.showToast(getApplicationContext(), "内容不要为空噢", TastyToast.ERROR);
            return;
        }
        if (userBean == null || userBean.getUsername() == null) {
            ToastUtil.showToast(getApplicationContext(), "请先登录哈", TastyToast.INFO);
            finish();
            return;
        }
        XLog.d(Constants.TAG, "sendMessageToDev");
        updateFeedbackMsgToBmob(userBean.getUsername(), msg);
        adapter.addItemNotifiChange(new FeedbackUIBean(msg, R.drawable.about_icon, new Date() + "", 0));
        sum++;
        messageRv.smoothScrollToPosition(sum);
    }

    private void updateFeedbackMsgToBmob(String username, String text) {
        final FeedbackToBmobBean feedbackToBmobBean = new FeedbackToBmobBean();
        feedbackToBmobBean.setUsername(username);
        feedbackToBmobBean.setText(text);
        feedbackToBmobBean.setTarget("dev");
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

    private void requestAllMsgInUser(final String curUsername) {
        BmobQuery<FeedbackToBmobBean> eq1 = new BmobQuery<FeedbackToBmobBean>();
        eq1.addWhereEqualTo("username", curUsername);

        BmobQuery<FeedbackToBmobBean> eq2 = new BmobQuery<FeedbackToBmobBean>();
        eq2.addWhereEqualTo("username", "dev");
        BmobQuery<FeedbackToBmobBean> eq3 = new BmobQuery<FeedbackToBmobBean>();
        eq2.addWhereEqualTo("target", curUsername);
        List<BmobQuery<FeedbackToBmobBean>> queries = new ArrayList<BmobQuery<FeedbackToBmobBean>>();
        queries.add(eq2);
        queries.add(eq3);
        BmobQuery<FeedbackToBmobBean> mainQuery = new BmobQuery<FeedbackToBmobBean>();
        BmobQuery<FeedbackToBmobBean> and = mainQuery.and(queries);

        List<BmobQuery<FeedbackToBmobBean>> orQuerys = new ArrayList<BmobQuery<FeedbackToBmobBean>>();
        orQuerys.add(eq1);
        orQuerys.add(and);

        BmobQuery<FeedbackToBmobBean> query = new BmobQuery<FeedbackToBmobBean>();
        query.or(orQuerys);

        query.order("createdAt");
        query.findObjects(new FindListener<FeedbackToBmobBean>() {
            @Override
            public void done(List<FeedbackToBmobBean> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getUsername().equals("dev")) {
                            adapter.addItemNotifiChange(new FeedbackUIBean(list.get(i).getText(), R.drawable
                                    .about_icon, list.get(i).getCreatedAt(), 1));
                        } else if (list.get(i).getUsername().equals(curUsername)) {
                            adapter.addItemNotifiChange(new FeedbackUIBean(list.get(i).getText(), R.drawable
                                    .about_icon, list.get(i).getCreatedAt(), 0));
                        }
                        sum++;
                    }
                    messageRv.smoothScrollToPosition(sum);
                    initWelcome();
                } else {

                }
            }
        });
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            XLog.d(Constants.TAG, "软键盘弹起");
            messageRv.smoothScrollToPosition(sum);

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            XLog.d(Constants.TAG, "软键盘收起");
            messageRv.smoothScrollToPosition(sum);

        }
    }
}
