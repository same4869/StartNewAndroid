package com.sna.xunwang.startnewandroid.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sdsmdg.tastytoast.TastyToast;
import com.sna.xunwang.startnewandroid.R;
import com.sna.xunwang.startnewandroid.activity.AboutActivity;
import com.sna.xunwang.startnewandroid.activity.FavActivity;
import com.sna.xunwang.startnewandroid.activity.FeedbackActivity;
import com.sna.xunwang.startnewandroid.activity.LoginActivity;
import com.sna.xunwang.startnewandroid.bean.UserBean;
import com.sna.xunwang.startnewandroid.config.Constants;
import com.sna.xunwang.startnewandroid.utils.BitmapBlurUtil;
import com.sna.xunwang.startnewandroid.utils.CacheUtil;
import com.sna.xunwang.startnewandroid.utils.ToastUtil;
import com.sna.xunwang.startnewandroid.utils.UserUtil;
import com.sna.xunwang.startnewandroid.utils.XLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xunwang on 16/11/21.
 */

public class UserFragment extends BaseFragment {
    private static final int LOGIN_KEY = 99;
    private static final int SELECT_CAMERA = 1;
    private static final int SELECT_ALBUM = 2;
    private static final int SELECT_CROP = 3;

    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.user_img_logo)
    RoundedImageView roundedImageView;
    @BindView(R.id.user_text)
    TextView userText;
    @BindView(R.id.user_logout)
    TextView userLogout;

    private AlertDialog albumDialog;
    private String dateTime;

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
        } else {
            showAlbumDialog();
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
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_theme_layout)
    void theme() {

    }

    @OnClick(R.id.user_app_layout)
    void app() {

    }

    @OnClick(R.id.user_about_layout)
    void about() {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }

    public void showAlbumDialog() {
        albumDialog = new AlertDialog.Builder(getActivity()).create();
        albumDialog.setCanceledOnTouchOutside(false);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);

        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                albumDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                getAvataFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                albumDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                getAvataFromCamera();
            }
        });
    }

    private void getAvataFromCamera() {
        File f = new File(CacheUtil.getCacheDirectory(getContext(), true, "icon") + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, SELECT_CAMERA);
    }

    private void getAvataFromAlbum() {
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2, SELECT_ALBUM);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, SELECT_CROP);
    }

    private void updateIcon(String avataPath) {
        if (avataPath != null) {
            final BmobFile file = new BmobFile(new File(avataPath));
            file.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        UserBean currentUser = UserUtil.getUserInfo();
                        currentUser.setAvatar(file);
                        currentUser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getActivity(), "更改头像成功", TastyToast.SUCCESS);
                                } else {
                                    XLog.d(Constants.TAG, "1 ---> " + e.getMessage());
                                    ToastUtil.showToast(getActivity(), "更新头像失败,请检查网络", TastyToast
                                            .ERROR);
                                }
                            }
                        });
                    } else {
                        XLog.d(Constants.TAG, "2 ---> " + e.getMessage());
                        ToastUtil.showToast(getActivity(), "更新头像失败,请检查网络", TastyToast.ERROR);
                    }
                }
            });
        }
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtil.getCacheDirectory(getContext(), true, "icon") + dateTime + "_12.jpg";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN_KEY:
                checkLoginStatus();
                break;
            case SELECT_CAMERA:
                String files = CacheUtil.getCacheDirectory(getContext(), true, "icon") + dateTime;
                File file = new File(files);
                if (file.exists() && file.length() > 0) {
                    Uri uri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                } else {

                }
                break;
            case SELECT_ALBUM:
                if (data == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case SELECT_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        String iconUrl = saveToSdCard(bitmap);
                        userImg.setImageBitmap(bitmap);
                        updateIcon(iconUrl);
                    }
                }
                break;
        }
    }
}
