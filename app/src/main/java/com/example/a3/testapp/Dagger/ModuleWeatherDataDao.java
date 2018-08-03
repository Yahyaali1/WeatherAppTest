package com.example.a3.testapp.Dagger;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.a3.testapp.DataModelDataBase.WeatherDataDao;
import com.example.a3.testapp.DataModelDataBase.WeatherDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleWeatherDataDao {
    @Provides
    @WeatherApplicationScope
    public WeatherDataDao weatherDataDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.weatherDataDao();
    }
    @Provides
    @WeatherApplicationScope
    public WeatherDatabase weatherDatabase(Context context)
    {
         return Room.databaseBuilder(context.getApplicationContext(),
                 WeatherDatabase.class, "weather_database")
                 .build();
    }




}
