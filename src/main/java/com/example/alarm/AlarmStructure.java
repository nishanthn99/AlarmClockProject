package com.example.alarm;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ALARM",primaryKeys = {"HOUR","MINUTE","ZONE"})
public class AlarmStructure {
    @ColumnInfo(name="HOUR")
    @NonNull String hour;
    @ColumnInfo(name="MINUTE")
    @NonNull String minute;
    @ColumnInfo(name="ZONE")
    @NonNull String zone;
    @ColumnInfo(name="STATUS")
    @NonNull String status;
    @ColumnInfo(name="SNOOZE")
    @NonNull String snooze;
    @Ignore
    AlarmStructure(String hour,String minute,String zone,String status){
        this.hour=hour;
        this.minute=minute;
        this.zone=zone;
        this.status=status;
    }
    AlarmStructure(String hour,String minute,String zone,String status,String snooze){
        this.hour=hour;
        this.minute=minute;
        this.zone=zone;
        this.status=status;
        this.snooze=snooze;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }


    public String getStatus() {return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSnooze() {
        return snooze;
    }

    public void setSnooze(String snooze) {
        this.snooze = snooze;
    }
}
