package com.example.alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = AlarmStructure.class,exportSchema = false,version = 1)
public abstract class DBHelper extends RoomDatabase {
    private static final String DATABASE_NAME="ALARM";
    private static DBHelper instance;
    public static synchronized DBHelper getDB(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,DBHelper.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract AlarmDao alarmDao();
}
