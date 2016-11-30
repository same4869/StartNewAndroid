package com.sna.xunwang.startnewandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sna.xunwang.startnewandroid.config.Constants;

/**
 * Created by xunwang on 16/11/18.
 */

public class SNADatabaseHelper extends AbstractDatabaseHelper {
    private static SNADatabaseHelper instance = null;

    private String databaseName = "Sna.db";
    private int databaseVersion = 1;
    private Context context;

    @Override
    protected String[] createDBTables() {
        String[] object = {
                "CREATE TABLE IF NOT EXISTS BIEZHI_GOODS("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ",ID VARCHAR(32)" + ",TITLE VARCHAR(100)" + ",URL VARCHAR(100)" + ",PRICE VARCHAR(20)" +
                        ",PIC_URL VARCHAR(100)" + ",objectid VARCHAR(20))",
                "CREATE TABLE IF NOT EXISTS FAV_GOODS("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ",userid VARCHAR(100)" + ",objectid VARCHAR(100)" + ",isfav VARCHAR(100))"};
        return object;
    }

    @Override
    protected String getMyDatabaseName() {
        return databaseName;
    }

    @Override
    protected int getDatabaseVersion() {
        return databaseVersion;
    }

    @Override
    protected String getTag() {
        return Constants.TAG;
    }

    private static synchronized void initSyn(Context context) {
        instance = new SNADatabaseHelper(context);
    }

    public static SNADatabaseHelper getInstance(Context context) {
        if (instance == null) {
            initSyn(context);
        }
        return instance;
    }

    private SNADatabaseHelper(Context context) {
        this.context = context;
    }

    public void execSQL(String sql, Object[] bindArgs) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.execSQL(sql, bindArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execSQL(String[] sql, Object[][] bindArgs) {
        if (sql == null || sql.length == 0) {
            return;
        }
        init(context);
        if (mDb == null) {
            return;
        }
        for (int i = 0; i < sql.length; i++) {
            try {
                mDb.execSQL(sql[i], bindArgs[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void execSQL(String sql) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String table, ContentValues values, String whereClause,
                       String[] whereArgs) {
        init(context);
        if (mDb == null) {
            return;
        }
        try {
            mDb.update(table, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        init(context);
        if (mDb == null) {
            return 0;
        }
        try {
            return mDb.insertOrThrow(table, nullColumnHack, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        init(context);
        if (mDb == null) {
            return null;
        }
        return mDb.rawQuery(sql, selectionArgs);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        init(context);

        if (mDb == null) {
            return null;
        }
        return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}
