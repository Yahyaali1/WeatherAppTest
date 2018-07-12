package com.example.a3.testapp.DataModelDataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "DailyWeatherData")
public class DailyWeatherData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dataId")
    @NonNull
    private int dataId;

    private String locationId;

    public DailyWeatherData(int dataId, String locationId, int temperatureValueDay, String iconPhraseDay, int iconIdDay, int temperatureValueNight, String iconPhraseNight, int iconIdNight, Date dateTime) {
        this.dataId = dataId;
        this.locationId = locationId;
        this.temperatureValueDay = temperatureValueDay;
        this.iconPhraseDay = iconPhraseDay;
        this.iconIdDay = iconIdDay;
        this.temperatureValueNight = temperatureValueNight;
        this.iconPhraseNight = iconPhraseNight;
        this.iconIdNight = iconIdNight;
        this.dateTime = dateTime;
    }

    private int temperatureValueDay;
    private String iconPhraseDay;
    private int iconIdDay;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getTemperatureValueDay() {
        return temperatureValueDay;
    }

    public void setTemperatureValueDay(int temperatureValueDay) {
        this.temperatureValueDay = temperatureValueDay;
    }

    public String getIconPhraseDay() {
        return iconPhraseDay;
    }

    public void setIconPhraseDay(String iconPhraseDay) {
        this.iconPhraseDay = iconPhraseDay;
    }

    public int getIconIdDay() {
        return iconIdDay;
    }

    public void setIconIdDay(int iconIdDay) {
        this.iconIdDay = iconIdDay;
    }

    public int getTemperatureValueNight() {
        return temperatureValueNight;
    }

    public void setTemperatureValueNight(int temperatureValueNight) {
        this.temperatureValueNight = temperatureValueNight;
    }

    public String getIconPhraseNight() {
        return iconPhraseNight;
    }

    public void setIconPhraseNight(String iconPhraseNight) {
        this.iconPhraseNight = iconPhraseNight;
    }

    public int getIconIdNight() {
        return iconIdNight;
    }

    public void setIconIdNight(int iconIdNight) {
        this.iconIdNight = iconIdNight;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    private int temperatureValueNight;
    private String iconPhraseNight;
    private int iconIdNight;

    @ColumnInfo(name = "dateTime")
    @TypeConverters({TimeStampConverter.class})
    public Date dateTime;

}
