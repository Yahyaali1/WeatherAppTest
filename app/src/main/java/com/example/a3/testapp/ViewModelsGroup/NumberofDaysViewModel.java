package com.example.a3.testapp.ViewModelsGroup;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.Date;
import java.util.List;

/**
 * Created by IBM on 7/17/2018.
 */

public class NumberofDaysViewModel extends ViewModel {
    private LiveData<List<HourlyWeatherData>> weeklyData;
    private static final String tag = "ofDays_View_Model";

    public LiveData<List<HourlyWeatherData>> getWeeklyData() {
        return weeklyData;
    }

    public NumberofDaysViewModel(Repo repo, String locationId, Date today){


        weeklyData=repo.dbNumberofDays(locationId,today);





        //DailyWweatherData
    }

}
