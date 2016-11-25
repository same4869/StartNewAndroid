package com.sna.xunwang.startnewandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xunwang on 16/11/23.
 */

public class FeedbackToBmobBean extends BmobObject {
    private String username;
    private String text;
    private String target;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
