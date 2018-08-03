package com.example.a3.testapp.Dagger;

import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.StaticVaraibles.WeatherApiClient;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ModuleWeatherApiInterface {
    @Provides
    public WeatherApiInterface weatherApiInterface(Retrofit retrofit){

        return retrofit.create(WeatherApiInterface.class);

    }

    @Provides
    public Retrofit retrofit (){

        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static final String apiKey = "rR6XOXAaxuJJuQtrTve3ecFGHmJ8jFOo";
    private static final String baseUrl = "http://dataservice.accuweather.com/";
}
