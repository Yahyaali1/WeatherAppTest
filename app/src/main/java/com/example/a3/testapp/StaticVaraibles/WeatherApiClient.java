package com.example.a3.testapp.StaticVaraibles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {
    private static final String baseUrl = "http://dataservice.accuweather.com/";
    public static final String apiKey = "rR6XOXAaxuJJuQtrTve3ecFGHmJ8jFOo";

    public static final String apikey2="MEDj9OPm6VIaUWEP9Ag9A3PA2d84eMy7";


    //captial first
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;

    }
    // for getting client of the base url.

    
}
