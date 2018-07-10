package com.example.a3.testapp.ApiInterfaces;

import com.example.a3.testapp.DataModel.SearchCity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {
    @GET("locations/v1/cities/autocomplete")
    Call<List<SearchCity>> getSearchCityDeatils(@Query("apikey") String apikey,@Query("q") String searchQuery);



}
