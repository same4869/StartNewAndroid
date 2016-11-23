package com.sna.xunwang.startnewandroid.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by xunwang on 16/11/9.
 */

public class BiezhiGoodsBean extends BmobObject {
    private String id;
    private String title;
    private String url;
    private String price;
    private String picUrl;

    private BmobRelation relation;// 评论
    private boolean myFav;// 收藏

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

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public boolean isMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }
}
