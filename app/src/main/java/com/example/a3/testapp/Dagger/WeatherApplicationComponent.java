package com.example.a3.testapp.Dagger;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.DataModelDataBase.WeatherDataDao;
import com.example.a3.testapp.StaticVaraibles.Repo;

import dagger.Component;


@WeatherApplicationScope
@Component (modules = {ModuleContext.class,ModuleSharedPref.class, ModuleWeatherDataDao.class,ModuleWeatherApiInterface.class})
public interface WeatherApplicationComponent {



    void injectSharedPref(Activity activity);
    void injectRepo(Repo repo);


}
