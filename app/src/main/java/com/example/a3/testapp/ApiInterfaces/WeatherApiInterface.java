package com.example.a3.testapp.ApiInterfaces;

import com.example.a3.testapp.DataModel.ApiWeeklyWeatherDataList;
import com.example.a3.testapp.DataModel.ApiHourlyWeatherData;
import com.example.a3.testapp.DataModel.SearchCity;
import com.example.a3.testapp.DataModel.ApiWeeklyWeatherData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiInterface {
    @GET("locations/v1/cities/autocomplete")
    Call<List<SearchCity>> getSearchCityDeatils(@Query("apikey") String apikey,@Query("q") String searchQuery);
    @GET("forecasts/v1/daily/5day/{Id}")
    Call<List<ApiWeeklyWeatherData>> getWeeklyWeatherData(@Path("Id") String cityId, @Query("apikey") String apikey);
    @GET("forecasts/v1/daily/5day/{Id}")
    Call<ApiWeeklyWeatherDataList> getWeeklyWeatherDataList(@Path("Id") String cityId, @Query("apikey") String apikey);
    @GET("forecasts/v1/hourly/12hour/{Id}")
    Call<List<ApiHourlyWeatherData>> getHourlyWeatherData(@Path("Id") String cityId, @Query("apikey") String apikey);





}
