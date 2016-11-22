package com.sna.xunwang.startnewandroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.utils.StringUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    public static final int PASSWORD_MIN_LENGTH = 5;

    @BindView(R.id.et_account)
    EditText accountEditText;
    @BindView(R.id.et_password)
    EditText passwordEditText;

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
        StatusBarUtil.setColor(this, Color.parseColor("#00ff00"));
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
}
