package com.sna.xunwang.startnewandroid.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.utils.StringUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText usernameEt;
    @BindView(R.id.et_password)
    EditText passwordEt;
    @BindView(R.id.et_pwd_again)
    EditText password2Et;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_register)
    void register() {
        if (canSignUp()) {
            signUp(usernameEt.getText().toString(), passwordEt.getText().toString());
        } else {
            ToastUtil.showToast(getApplicationContext(), "请确认都不为空且两次密码输入一致", TastyToast.ERROR);
        }
    }

    // 注册
    private void signUp(String username, String password) {
        UserBean bu = new UserBean();
        bu.setUsername(username);
        bu.setPassword(password);
        // 注意：不能用save方法进行注册
        bu.signUp(new SaveListener<UserBean>() {

            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(getApplicationContext(), "注册成功", TastyToast.SUCCESS);
                    finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "注册失败,请重试", TastyToast.ERROR);
                }
            }
        });
    }

    private boolean canSignUp() {
        String userNameString = usernameEt.getText().toString();
        String passwordString = passwordEt.getText().toString();
        String password2String = password2Et.getText().toString();
        if (!StringUtil.isStringNullorBlank(userNameString) && !StringUtil.isStringNullorBlank(passwordString)
                && !StringUtil.isStringNullorBlank(password2String)) {
            if (passwordString.equals(password2String)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
