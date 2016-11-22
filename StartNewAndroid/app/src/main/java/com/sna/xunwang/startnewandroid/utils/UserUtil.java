package com.sna.xunwang.startnewandroid.utils;

import com.sna.xunwang.startnewandroid.bean.UserBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by xunwang on 16/11/22.
 */

public class UserUtil {

    public static boolean isLogined() {
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        if (userBean != null) {
            return true;
        } else {
            return false;
        }
    }

    public static UserBean getUserInfo() {
        return BmobUser.getCurrentUser(UserBean.class);
    }

}
