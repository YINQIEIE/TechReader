package com.yq.eie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.yq.eie.db.DBHelper.TABLE_BLOG;

public class DbManager {

    private DBHelper dbHelper;

    public DbManager(Context context) {
        dbHelper = DBHelper.getDbHelper(context.getApplicationContext());
    }

    public boolean addBlog(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues(1);
        values.put("url", url);
        long result = db.insert(TABLE_BLOG, null, values);
        db.close();
        return result != -1;
    }

    public void deleteBlog(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_BLOG, "url", new String[]{url});
        db.close();
    }

    public List queryAllBlogs() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor result = db.query(TABLE_BLOG, null, null, null, null, null, null);
        while (result.moveToNext()) {
            String url = result.getString(result.getColumnIndex("url"));
            list.add(url);
        }
        result.close();
        db.close();
        return list;
    }

}
