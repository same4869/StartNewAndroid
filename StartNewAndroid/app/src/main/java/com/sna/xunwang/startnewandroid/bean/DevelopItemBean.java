package com.sna.xunwang.startnewandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xunwang on 16/11/28.
 */

public class DevelopItemBean extends BmobObject{
    private String itemImgUrl;
    private String itemTitle;
    private String itemContent;
    private String itemUrl;

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
