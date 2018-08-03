package com.example.a3.testapp.Dagger;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    public static WeatherApplicationComponent weatherApplicationComponent;

    public static App get(Context activity){
        return (App) activity.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
         weatherApplicationComponent = DaggerWeatherApplicationComponent.builder()
                .moduleContext(new ModuleContext(this)).build();


    }
}
