package com.example.a3.testapp.DataModelDataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "hourlyWeatherData")
public class HourlyWeatherData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dataId")
    @NonNull
    private int dataId;
    private String locationId;
    private int temperatureValue;
    private String iconPhrase;
    public Date dateTime;

    private int iconId;

    public HourlyWeatherData( String locationId, int temperatureValue, String iconPhrase, Date dateTime, int iconId) {

        this.locationId = locationId;
        this.temperatureValue = temperatureValue;
        this.iconPhrase = iconPhrase;
        this.dateTime = dateTime;
        this.iconId = iconId;
    }
    @Ignore
    public HourlyWeatherData(String locationId){
        this.locationId=locationId;
        this.temperatureValue=0;
        this.iconPhrase="refresh the app to display data";
        this.iconId=0;

    }

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

    public int getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(int temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
