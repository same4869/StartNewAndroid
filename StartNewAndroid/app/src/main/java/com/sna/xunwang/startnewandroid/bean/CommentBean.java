package com.sna.xunwang.startnewandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xunwang on 16/12/1.
 */

public class CommentBean extends BmobObject{
    private UserBean user;
    private String commentContent;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
