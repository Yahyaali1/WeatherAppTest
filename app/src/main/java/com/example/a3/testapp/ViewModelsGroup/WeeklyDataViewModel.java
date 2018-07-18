package com.example.a3.testapp.ViewModelsGroup;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.Date;
import java.util.List;

/**
 * Created by IBM on 7/17/2018.
 */

public class WeeklyDataViewModel extends ViewModel {
    private LiveData<List<DailyWeatherData>> weeklyData;
    private static final String tag = "Weekly_View_Model";

    public LiveData<List<DailyWeatherData>> getWeeklyData() {
        return weeklyData;
    }

    public WeeklyDataViewModel(Repo repo, String locationId, Date today){

        weeklyData= repo.getFiveDaysData(locationId,today);
        Log.d(tag,"Weekly Model Created");


        //DailyWweatherData
    }

}
