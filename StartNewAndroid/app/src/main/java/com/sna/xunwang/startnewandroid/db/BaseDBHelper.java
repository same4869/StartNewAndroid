package com.sna.xunwang.startnewandroid.db;

import android.content.Context;

import com.sna.xunwang.startnewandroid.app.SNAApplication;

import java.util.List;

/**
 * Created by xunwang on 16/11/18.
 */

public abstract class BaseDBHelper<T> {

    protected Context getContext() {
        return SNAApplication.getInstance();
    }

    public abstract String getTable();

    public abstract void save(T obj);

    public abstract void update(T obj);

    public abstract void delete(String id);

    public abstract void deleteAll();

    public abstract T find(String id);

    public abstract int getCount();

    public abstract List<T> getAllData();
}
