package com.sna.xunwang.startnewandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.StringUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    public static final int PASSWORD_MIN_LENGTH = 5;

    @BindView(R.id.et_account)
    EditText accountEditText;
    @BindView(R.id.et_password)
    EditText passwordEditText;

    public static Tencent mTencent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_register)
    void toRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bar_theme));
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_login)
    void login() {
        if (canLogin()) {
            login(accountEditText.getText().toString(), passwordEditText.getText().toString());
        } else {
            ToastUtil.showToast(getApplicationContext(), "用户名或密码不能低于5位噢", TastyToast.ERROR);
        }
    }

    @OnClick(R.id.btn_qq_auth)
    void qqAuth() {
        qqAuthorize();
    }

    // 判断当前用户输入是否合法，是否可以登录
    private boolean canLogin() {
        Editable loUsername = accountEditText.getText();
        Editable loPassword = passwordEditText.getText();
        if (loUsername.length() < PASSWORD_MIN_LENGTH && loPassword.length() < PASSWORD_MIN_LENGTH) {
            return false;
        }
        return !StringUtil.isStringNullorBlank(loUsername.toString()) && loPassword.length() >= PASSWORD_MIN_LENGTH;
    }

    // 登录
    private void login(String username, String password) {
        UserBean bu2 = new UserBean();
        bu2.setUsername(username);
        bu2.setPassword(password);
        bu2.login(new SaveListener<UserBean>() {

            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(getApplicationContext(), "登录成功", TastyToast.SUCCESS);
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "登录失败,请重试", TastyToast.ERROR);
                }
            }
        });
    }

    private void qqAuthorize() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constants.QQ_APP_ID, getApplicationContext());
        }
        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {

            @Override
            public void onComplete(Object arg0) {
                if (arg0 != null) {
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser
                                .BmobThirdUserAuth.SNS_TYPE_QQ, token,
                                expires, openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                ToastUtil.showToast(getApplicationContext(), "QQ授权出错：" + arg0.errorCode + "--" + arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                ToastUtil.showToast(getApplicationContext(), "取消qq授权");
            }
        });
    }

    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(authInfo, new LogInListener() {

            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    XLog.d(Constants.TAG, authInfo.getSnsType() + "登陆成功返回:" + o);
                    Intent intent = new Intent(LoginActivity.this, SNAMainActivity.class);
                    intent.putExtra("json", o.toString());
                    intent.putExtra("from", authInfo.getSnsType());
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "第三方登陆失败：" + e.getMessage());
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
        XLog.d(Constants.TAG, "data --> " + data);
    }
}
