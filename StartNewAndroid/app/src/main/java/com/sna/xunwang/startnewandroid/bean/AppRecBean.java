package com.sna.xunwang.startnewandroid.bean;

/**
 * Created by xunwang on 16/11/28.
 */

public class AppRecBean {
    private int appImgSrc;
    private String appTitle;
    private String appContent;
    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getAppImgSrc() {
        return appImgSrc;
    }

    public void setAppImgSrc(int appImgSrc) {
        this.appImgSrc = appImgSrc;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppContent() {
        return appContent;
    }

    public void setAppContent(String appContent) {
        this.appContent = appContent;
    }
}
