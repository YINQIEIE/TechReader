package com.yq.eie.http.response;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

public class RoomDBManager {

    private static AppDatabase database;

    public static RoomDatabase getRoomDB(Context context) {
        if (null == database) {
            synchronized (RoomDBManager.class) {
                if (null == database) {
                    database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app").build();
                }
            }
        }
        return database;
    }

}
