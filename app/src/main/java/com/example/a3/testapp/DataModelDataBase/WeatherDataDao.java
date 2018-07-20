package com.example.a3.testapp.DataModelDataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Dao
public interface WeatherDataDao {
    @Insert
    public void insertLocation(Locations locations);
    @Insert
    public void insertDailyWeather(List<DailyWeatherData> dailyWeatherData);

    @Insert
    public void insertDailyWeatherObject(DailyWeatherData dailyWeatherData);
    @Insert
    public void insertHourlyWeather(List<HourlyWeatherData> HourlyWeatherData);
    @Insert
    public void insertHourlyWeather(HourlyWeatherData HourlyWeatherData);

    @Query("SELECT * from locations")
    public LiveData<List<Locations>> getAllLocations();

    @Query("SELECT * from locations")
    public List<Locations> getAllCities();

    @Query("Delete from DailyWeatherData where locationId=:loc")
    public void deleteDailyWeatherData(String loc);

    @Query("Delete from hourlyWeatherData where locationId=:loc")
    public void deleteHourlyWeatherData(String loc);


    @Query("SELECT * from DailyWeatherData where locationId LIKE :locationId and dateTime>=:today")
    public  LiveData<List<DailyWeatherData>> getFiveDayData(String locationId, Date today);

    @Query("SELECT * from DailyWeatherData where locationId LIKE :locationId ")
    public  LiveData<List<DailyWeatherData>> getFiveDayData(String locationId);
    //UPDATE QUERY TESTING


    @Query("SELECT * from hourlyWeatherData where locationId=:locationId and dateTime=:today")
    public  LiveData<List<HourlyWeatherData>> getDailyData(String locationId, Date today);



    @Query("SELECT Distinct dateTime,dataId,locationId,temperatureValue,iconId,iconPhrase from hourlyWeatherData where dateTime>=:date and locationId Like:loc")
    public LiveData<List<HourlyWeatherData>> getNumberOfDays(String loc,Date date);

    @Query("SELECT * from hourlyWeatherData where dateTime >=:date and dateTime<:tomo and locationId Like :loc")
    public LiveData<List<HourlyWeatherData>> getHourlyDayDetail(String loc,Date date,Date tomo);

    @Query("SELECT * from hourlyWeatherData")
    public LiveData<List<HourlyWeatherData>> getAllHourlyWeatherData();


    @Delete
    public void deleteLocation(Locations locations);






}
