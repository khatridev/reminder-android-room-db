package com.devk.mapper.data.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    public int _id;

    @NonNull
    @ColumnInfo(name = "Reminder")
    public String Reminder;

    @ColumnInfo(name = "IsTimeSet")
    public boolean IsDateTimeInfoSet;

    @ColumnInfo(name = "DateInfo")
    public String DateInfo;

    @ColumnInfo(name = "TimeInfo")
    public String TimeInfo;

    @ColumnInfo(name = "PlaceInfo")
    public String PlaceInfo;

    @ColumnInfo(name = "IsPlaceSet")
    public   boolean IsPlaceInfoSet;

    public Reminder(){

    }
    public Reminder(String rText, boolean isTimeSet, String dateInfo, String timeInfo, boolean isPlaceSet, String placeInfo){
        this.Reminder = rText;
        this.IsDateTimeInfoSet = isTimeSet;
        this.DateInfo = dateInfo;
        this.TimeInfo = timeInfo;
        this.IsPlaceInfoSet = isPlaceSet;
        this.PlaceInfo = placeInfo;
    }
}
