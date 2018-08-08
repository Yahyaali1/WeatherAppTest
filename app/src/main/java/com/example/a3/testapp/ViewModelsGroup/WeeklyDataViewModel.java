package com.example.a3.testapp.ViewModelsGroup;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by IBM on 7/17/2018.
 */

public class WeeklyDataViewModel extends ViewModel {
    private LiveData<List<DailyWeatherData>> weeklyData;
    private MutableLiveData<List<DailyWeatherData>> weeklyDataRx = new MutableLiveData<>();

    private static final String tag = "Weekly_View_Model";

    public LiveData<List<DailyWeatherData>> getWeeklyData() {
        return weeklyDataRx;
    }

    public WeeklyDataViewModel(Repo repo, String locationId, Date today){

        //weeklyData= repo.dbGetFiveDaysSummary(locationId,today);
        repo.dbGetFiveDaysSummaryRx(locationId,today).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DailyWeatherData>>() {
                    @Override
                    public void accept(List<DailyWeatherData> dailyWeatherData) throws Exception {

                        weeklyDataRx.postValue(dailyWeatherData);
                    }

                });
        Log.d(tag,"Weekly Model Created");


        //DailyWweatherData
    }

}
