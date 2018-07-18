package com.example.a3.testapp.DataModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by IBM on 7/17/2018.
 */

public class ApiWeeklyWeatherDataList {
    @SerializedName("DailyForecasts")
    private ArrayList<ApiWeeklyWeatherData> apiWeeklyWeatherData;

    public ApiWeeklyWeatherDataList(ArrayList<ApiWeeklyWeatherData> apiWeeklyWeatherData) {
        this.apiWeeklyWeatherData = apiWeeklyWeatherData;
    }

    public ArrayList<ApiWeeklyWeatherData> getApiWeeklyWeatherData() {
        return apiWeeklyWeatherData;
    }

    public void setApiWeeklyWeatherData(ArrayList<ApiWeeklyWeatherData> apiWeeklyWeatherData) {
        this.apiWeeklyWeatherData = apiWeeklyWeatherData;
    }
}
