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
    public String dateTime;
    private int temperatureValueDay;
    private String iconPhraseDay;
    private int iconIdDay;
    private int temperatureValueNight;
    private String iconPhraseNight;

    public DailyWeatherData(@NonNull int dataId, String locationId, String dateTime, int temperatureValueDay, String iconPhraseDay, int iconIdDay, int temperatureValueNight, String iconPhraseNight, int iconIdNight) {
        this.dataId = dataId;
        this.locationId = locationId;
        this.dateTime = dateTime;
        this.temperatureValueDay = temperatureValueDay;
        this.iconPhraseDay = iconPhraseDay;
        this.iconIdDay = iconIdDay;
        this.temperatureValueNight = temperatureValueNight;
        this.iconPhraseNight = iconPhraseNight;
        this.iconIdNight = iconIdNight;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private int iconIdNight;

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

}
