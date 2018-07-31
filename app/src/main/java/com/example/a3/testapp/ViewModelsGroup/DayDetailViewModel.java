package com.example.a3.testapp.ViewModelsGroup;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.Date;
import java.util.List;

/**
 * Created by IBM on 7/17/2018.
 */

public class DayDetailViewModel extends ViewModel {
    private LiveData<List<HourlyWeatherData>> dailyData;
    private static final String tag = "DayDays_View_Model";

    public LiveData<List<HourlyWeatherData>> getDailyData() {
        return dailyData;
    }

    public DayDetailViewModel(Repo repo, String locationId, Date today){


        dailyData =repo.dbGetDailyHourlyDetail(locationId,today);
        Log.d(tag," Model Created");


        //DailyWweatherData
    }

}
