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
    void insertLocation(Locations locations);
    @Insert
    void insertDailyWeather(List<DailyWeatherData> dailyWeatherData);

    @Insert
    void insertDailyWeatherObject(DailyWeatherData dailyWeatherData);
    @Insert
    void insertHourlyWeather(List<HourlyWeatherData> HourlyWeatherData);
    @Insert
    void insertHourlyWeather(HourlyWeatherData HourlyWeatherData);

    @Query("SELECT * from locations")
    LiveData<List<Locations>> getAllLocations();

    @Query("SELECT * from locations")
    List<Locations> getAllCities();

    @Query("Delete from DailyWeatherData where locationId=:loc")
    void deleteDailyWeatherData(String loc);

    @Query("Delete from hourlyWeatherData where locationId=:loc")
    void deleteHourlyWeatherData(String loc);


    @Query("SELECT * from DailyWeatherData where locationId LIKE :locationId and dateTime>=:today")
    LiveData<List<DailyWeatherData>> getFiveDayData(String locationId, Date today);

    @Query("SELECT * from DailyWeatherData where locationId LIKE :locationId")
    List<DailyWeatherData> getNotifcationData(String locationId);

    @Query("SELECT * from DailyWeatherData where locationId LIKE :locationId ")
    LiveData<List<DailyWeatherData>> getFiveDayData(String locationId);
    //UPDATE QUERY TESTING


    @Query("SELECT * from hourlyWeatherData where locationId=:locationId and dateTime=:today")
    LiveData<List<HourlyWeatherData>> getDailyData(String locationId, Date today);

    @Query("SELECT * from hourlyWeatherData where locationId=:locationId and dateTime>=:today")
    HourlyWeatherData getHourlyDataWidget(String locationId, Date today);



    @Query("SELECT Distinct dateTime,dataId,locationId,temperatureValue,iconId,iconPhrase from hourlyWeatherData where dateTime>=:date and locationId Like:loc")
    LiveData<List<HourlyWeatherData>> getNumberOfDays(String loc, Date date);

    @Query("SELECT * from hourlyWeatherData where dateTime >=:date and dateTime<:tomo and locationId Like :loc")
    LiveData<List<HourlyWeatherData>> getHourlyDayDetail(String loc, Date date, Date tomo);

    @Query("SELECT * from hourlyWeatherData")
    LiveData<List<HourlyWeatherData>> getAllHourlyWeatherData();


    @Delete
    void deleteLocation(Locations locations);






}
