package com.sna.xunwang.startnewandroid.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.sna.xunwang.startnewandroid.bean.BiezhiGoodsBean;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xunwang on 16/11/23.
 */

public class CollectDBHelper extends BaseDBHelper<BiezhiGoodsBean> {
    private SNADatabaseHelper mHelper;
    private static CollectDBHelper instance;

    private CollectDBHelper() {
        mHelper = SNADatabaseHelper.getInstance(getContext());
    }

    private static synchronized void initInstanceSyn() {
        if (instance == null) {
            instance = new CollectDBHelper();
        }
    }

    public static CollectDBHelper getInstance() {
        if (instance == null) {
            initInstanceSyn();
        }
        return instance;
    }

    @Override
    public String getTable() {
        return "FAV_GOODS";
    }

    @Override
    public void save(BiezhiGoodsBean obj) {

    }

    @Override
    public void update(BiezhiGoodsBean obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAll() {

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
        return null;
    }

    public void deleteFav(BiezhiGoodsBean biezhiGoodsBean) {
        String where = "userid = '" + BmobUser.getCurrentUser().getObjectId() + "' AND objectid = '"
                + biezhiGoodsBean.getObjectId() + "'";
        Cursor cursor = mHelper.query(getTable(), null, where, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            ContentValues cv = new ContentValues();
            cv.put("isfav", 0);
            mHelper.update(getTable(), cv, where, null);
        }
        if (cursor != null) {
            cursor.close();
            mHelper.close();
        }
    }

    public long insertFav(BiezhiGoodsBean biezhiGoodsBean) {
        long uri = 0;
        String where = "userid = '" + BmobUser.getCurrentUser().getObjectId() + "' AND objectid = '"
                + biezhiGoodsBean.getObjectId() + "'";
        Cursor cursor = mHelper.query(getTable(), null, where, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            ContentValues conv = new ContentValues();
            conv.put("isfav", 1);
            mHelper.update(getTable(), conv, where, null);
        } else {
            ContentValues cv = new ContentValues();
            cv.put("userid", BmobUser.getCurrentUser().getObjectId());
            cv.put("objectid", biezhiGoodsBean.getObjectId());
            cv.put("isfav", biezhiGoodsBean.isMyFav() == true ? 1 : 0);
            uri = mHelper.insert(getTable(), null, cv);
        }
        if (cursor != null) {
            cursor.close();
            mHelper.close();
        }
        return uri;
    }

    public List<BiezhiGoodsBean> setFav(List<BiezhiGoodsBean> lists) {
        Cursor cursor = null;
        if (lists != null && lists.size() > 0) {
            for (Iterator<BiezhiGoodsBean> iterator = lists.iterator(); iterator.hasNext(); ) {
                BiezhiGoodsBean content = (BiezhiGoodsBean) iterator.next();
                String where = "userid = '" + BmobUser.getCurrentUser().getObjectId() + "' AND objectid = '"
                        + content.getObjectId() + "'";
                cursor = mHelper.query(getTable(), null, where, null, null, null, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    if (cursor.getInt(cursor.getColumnIndex("isfav")) == 1) {
                        content.setMyFav(true);
                    } else {
                        content.setMyFav(false);
                    }
                }
            }
        }
        if (cursor != null) {
            cursor.close();
            mHelper.close();
        }
        return lists;
    }

}
