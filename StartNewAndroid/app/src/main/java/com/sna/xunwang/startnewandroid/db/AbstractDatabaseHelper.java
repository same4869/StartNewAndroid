package com.sna.xunwang.startnewandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xunwang on 16/11/18.
 */

public abstract class AbstractDatabaseHelper {
    /** */
    /**
     * SQLite数据库实例
     */
    public SQLiteDatabase mDb = null;

    /** */
    /**
     * 数据库创建帮手
     */
    protected CreateDBHelper mDbHelper = null;

    /** */
    /**
     * 获得当前数据库帮手类标识(一般是该类名称)，用于日志等的记录
     *
     * @return
     */
    protected abstract String getTag();

    /** */
    /**
     * 获得数据库名称
     *
     * @return
     */
    protected abstract String getMyDatabaseName();

    /** */
    /**
     * 获得数据库版本，值至少为1。 当数据库结构发生改变的时候，请将此值加1，系统会在初始化时自动调用 createDBTables和dropDBTables方法更新数据库结构。
     *
     * @return
     */
    protected abstract int getDatabaseVersion();

    /** */
    /**
     * 创建数据库表的SQL语句，一个元素一条语句
     *
     * @return
     */
    protected abstract String[] createDBTables();

    /** */
    /**
     * TODO 内部数据库创建帮手类
     */
    private class CreateDBHelper extends SQLiteOpenHelper {
        public CreateDBHelper(Context ctx) {
            super(ctx, getMyDatabaseName(), null, getDatabaseVersion());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            executeBatch(createDBTables(), db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        /** */
        /**
         * 批量执行Sql语句
         * e
         *
         * @param sqls
         * @param db
         */
        private void executeBatch(String[] sqls, SQLiteDatabase db) {
            if (sqls == null)
                return;

            db.beginTransaction();
            try {
                int len = sqls.length;
                for (int i = 0; i < len; i++) {
                    db.execSQL(sqls[i]);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }

        }
    }

    /** */
    /**
     * 打开或者创建一个指定名称的数据库
     *
     * @param ctx
     */
    private synchronized void open(Context ctx) {
        if (mDbHelper == null) {
            try {
                mDbHelper = new CreateDBHelper(ctx);
                mDb = mDbHelper.getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void init(Context ctx) {
        if (ctx == null) {
            throw new RuntimeException("ctx is null");
        }
        if (mDbHelper == null) {
            open(ctx);
        }
    }

    /** */
    /**
     * 关闭数据库
     */
    public void close() {
        try {
            if (mDb != null && mDb.isOpen()) {
                mDb.close();
            }
            if (mDbHelper != null) {
                mDbHelper.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
