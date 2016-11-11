package com.sna.xunwang.startnewandroid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xunwang on 16/11/9.
 */

public class BiezhiGoodsBean extends BmobObject {
    private String id;
    private String title;
    private String url;
    private String price;
    private String picUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
