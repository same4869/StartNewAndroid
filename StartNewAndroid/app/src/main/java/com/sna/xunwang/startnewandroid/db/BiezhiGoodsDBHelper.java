package com.sna.xunwang.startnewandroid.db;

import android.database.Cursor;

import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xunwang on 16/11/18.
 */

public class BiezhiGoodsDBHelper extends BaseDBHelper<BiezhiGoodsBean> {
    private SNADatabaseHelper mHelper;
    private static BiezhiGoodsDBHelper instance;

    private BiezhiGoodsDBHelper() {
        mHelper = SNADatabaseHelper.getInstance(getContext());
    }

    private static synchronized void initInstanceSyn() {
        if (instance == null) {
            instance = new BiezhiGoodsDBHelper();
        }
    }

    public static BiezhiGoodsDBHelper getInstance() {
        if (instance == null) {
            initInstanceSyn();
        }
        return instance;
    }

    @Override
    public String getTable() {
        return "BIEZHI_GOODS";
    }

    @Override
    public void save(BiezhiGoodsBean obj) {
        if (obj == null) {
            return;
        }
        String sql = "insert into " + getTable() + "("
                + "ID,TITLE,URL,PRICE,PIC_URL"
                + ")values(?,?,?,?,?)";
        Object[] bindArgs = {obj.getId(), obj.getTitle(), obj.getUrl(), obj.getPrice(),
                obj.getPicUrl()};
        mHelper.execSQL(sql, bindArgs);
    }

    @Override
    public void update(BiezhiGoodsBean obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll() {
        String sql = "delete from " + getTable();
        mHelper.execSQL(sql);
    }

    @Override
    public BiezhiGoodsBean find(String id) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<BiezhiGoodsBean> getAllData() {
        List<BiezhiGoodsBean> datas = new ArrayList<BiezhiGoodsBean>();
        String[] columns = {"ID", "TITLE", "URL", "PRICE", "PIC_URL"};
        Cursor cursor = mHelper.query(getTable(), columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            BiezhiGoodsBean biezhiGoodsBean = new BiezhiGoodsBean();
            biezhiGoodsBean.setId(cursor.getString(cursor.getColumnIndex("ID")));
            biezhiGoodsBean.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
            biezhiGoodsBean.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
            biezhiGoodsBean.setPrice(cursor.getString(cursor.getColumnIndex("PRICE")));
            biezhiGoodsBean.setPicUrl(cursor.getString(cursor.getColumnIndex("PIC_URL")));
            datas.add(biezhiGoodsBean);
        }
        if (cursor != null) {
            cursor.close();
            mHelper.close();
        }
        return datas;
    }


    public List<BiezhiGoodsBean> getUserFav() {
        List<BiezhiGoodsBean> lists = new ArrayList<BiezhiGoodsBean>();
        String where = "userid = '" + BmobUser.getCurrentUser().getObjectId();
        String[] columns = {"ID", "TITLE", "URL", "PRICE", "PIC_URL"};
        Cursor cursor = mHelper.query(getTable(), columns, where, null, null, null, null);
        while (cursor.moveToNext()) {
            BiezhiGoodsBean biezhiGoodsBean = new BiezhiGoodsBean();
            biezhiGoodsBean.setId(cursor.getString(cursor.getColumnIndex("ID")));
            biezhiGoodsBean.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
            biezhiGoodsBean.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
            biezhiGoodsBean.setPrice(cursor.getString(cursor.getColumnIndex("PRICE")));
            biezhiGoodsBean.setPicUrl(cursor.getString(cursor.getColumnIndex("PIC_URL")));
            lists.add(biezhiGoodsBean);
        }
        if (cursor != null) {
            cursor.close();
            mHelper.close();
        }
        return lists;
    }
}
