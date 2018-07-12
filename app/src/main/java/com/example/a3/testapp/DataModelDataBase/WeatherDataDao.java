package com.example.a3.testapp.DataModelDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface WeatherDataDao {
    @Insert
    public void insertLocation(Locations locations);
    @Insert
    public void insertDailyWeather(List<DailyWeatherData> dailyWeatherData);
    @Insert
    public void insertHourlyWeather(List<HourlyWeatherData> HourlyWeatherData);

    @Query("SELECT * from locations")
    public List<Locations> getAllLocations();

    @Delete
    public void deleteLocation(Locations locations);






}
