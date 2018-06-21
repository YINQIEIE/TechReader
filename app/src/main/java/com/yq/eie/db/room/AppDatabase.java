package com.yq.eie.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

@Database(entities = {BlogEntity.class, BlogTagEntity.class}, version = 1)
@TypeConverters({TimeConverters.class, JsonConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase database;

    public static AppDatabase getInstance(Context context) {
        if (null == database) {
            synchronized (AppDatabase.class) {
                if (null == database) {
                    database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "appdata.db").build();
                }
            }
        }
        return database;
    }

    public abstract BlogDao getBlogDao();

    public abstract BlogTagDao getTagDao();

}

class TimeConverters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? new Date() : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? new Date().getTime() : date.getTime();
    }
}

class JsonConverters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
