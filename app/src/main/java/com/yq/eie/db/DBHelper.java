package com.yq.eie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String dbName = "my_data.db";
    public static final String TABLE_BLOG = "blog_collection";
    public static final String TABLE_TAG = "blog_tag";
    public static final int version = 1;
    public static final String SQL_CREATE_TABLE_BLOG = "CREATE TABLE IF NOT EXISTS " + TABLE_BLOG + "(id INTEGER PRIMARY KEY AUTOINCREMENT,url VARCHAR(200) NOT NULL);";
    public static final String SQL_CREATE_TABLE_TAG = "CREATE TABLE IF NOT EXISTS " + TABLE_TAG + "(id INTEGER PRIMARY KEY AUTOINCREMENT,tag VARCHAR(200) NOT NULL);";

    private static volatile DBHelper dbHelper;

    public static DBHelper getDbHelper(Context context) {
        if (null == dbHelper) {
            synchronized (DBHelper.class) {
                if (null == dbHelper)
                    dbHelper = new DBHelper(context);
            }
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_BLOG);
//        db.execSQL(SQL_CREATE_TABLE_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 可以给表格新增列或进行其他操作
        db.execSQL("DROP TABLE " + TABLE_BLOG + ";");
//        db.execSQL("DROP TABLE " + TABLE_TAG + ";");
        db.execSQL(SQL_CREATE_TABLE_BLOG);
    }
}
