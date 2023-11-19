package com.example.alarm;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.ArrayList;
import java.util.List;

@Dao
public interface AlarmDao {
    @Query("select * from ALARM")
    List<AlarmStructure> fetching();
    @Insert
    void alarmAdd(AlarmStructure alarm);
    @Update
    void alarmEdit(AlarmStructure alarm);
    @Delete
    void alarmDelete(AlarmStructure alarm);
}
