package com.sna.xunwang.startnewandroid.bean;

import java.io.Serializable;

/**
 * Created by xunwang on 16/11/23.
 */

public class FeedbackUIBean implements Serializable {
    /** 聊天内容 */
    private String tMessage;
    /** 头像 */
    private Integer portrait;
    /** 发送信息当前时间 */
    private String time;
    /** 用户id */
    private int uid;

    public FeedbackUIBean(String tMessage, Integer portrait, String time, int uid) {
        this.tMessage = tMessage;
        this.portrait = portrait;
        this.time = time;
        this.uid = uid;
    }

    public String gettMessage() {
        return tMessage;
    }

    public void settMessage(String tMessage) {
        this.tMessage = tMessage;
    }

    public Integer getPortrait() {
        return portrait;
    }

    public void setPortrait(Integer portrait) {
        this.portrait = portrait;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return uid;
    }

    public void setId(int uid) {
        this.uid = uid;
    }
}
