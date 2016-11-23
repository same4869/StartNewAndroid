package com.sna.xunwang.startnewandroid.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.FavActivity;
import com.sna.xunwang.startnewandroid.activity.LoginActivity;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.utils.BitmapBlurUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * Created by xunwang on 16/11/21.
 */

public class UserFragment extends BaseFragment {
    private static final int LOGIN_KEY = 1;

    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.user_img_logo)
    RoundedImageView roundedImageView;
    @BindView(R.id.user_text)
    TextView userText;
    @BindView(R.id.user_logout)
    TextView userLogout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void lazyFetchData() {
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        if (UserUtil.isLogined()) {
            UserBean userBean = UserUtil.getUserInfo();
            userText.setText(userBean.getUsername());
            userLogout.setVisibility(View.VISIBLE);
        } else {
            userText.setText("未登录,登录后收藏永久保存");
            userLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        Glide.with(getActivity()).load("http://www.2cto.com/uploadfile/Collfiles/20150410/2015041008373592.jpg")
                .asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                userImg.setImageBitmap(BitmapBlurUtil.setBlur(resource, 25));
            }
        });

    }

    @OnClick(R.id.user_img_logo)
    void imgLogo() {
        if (!UserUtil.isLogined()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, LOGIN_KEY);
        }
    }

    @OnClick(R.id.user_logout)
    void logout() {
        showLogoutDialog();
    }

    public void showLogoutDialog() {
        AlertDialog.Builder logoutDialogBuilder = new AlertDialog.Builder(getActivity());
        logoutDialogBuilder.setTitle("登出");
        logoutDialogBuilder.setMessage("确定要登出吗？");
        logoutDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                BmobUser.logOut(); // 清除缓存用户对象
                if (!UserUtil.isLogined()) {
                    ToastUtil.showToast(getContext(), "登出成功啦", TastyToast.SUCCESS);
                    checkLoginStatus();
                }
            }
        });
        logoutDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        logoutDialogBuilder.show();
    }

    @OnClick(R.id.user_collect_layout)
    void collect() {
        Intent intent = new Intent(getActivity(), FavActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_feedback_layout)
    void feedback() {

    }

    @OnClick(R.id.user_theme_layout)
    void theme() {

    }

    @OnClick(R.id.user_app_layout)
    void app() {

    }

    @OnClick(R.id.user_about_layout)
    void about() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_KEY) {
            checkLoginStatus();
        }
    }
}
