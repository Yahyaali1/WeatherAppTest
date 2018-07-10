package com.example.a3.testapp.StaticVaraibles;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class weatherApiClient {
    public static final String baseUrl = "http://dataservice.accuweather.com/";
    public static final String apiKey = "rR6XOXAaxuJJuQtrTve3ecFGHmJ8jFOo";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();

        }

        return retrofit;

    }
    // for getting client of the base url.

    
}
