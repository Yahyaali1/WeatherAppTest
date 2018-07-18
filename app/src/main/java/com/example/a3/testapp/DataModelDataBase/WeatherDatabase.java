package com.example.a3.testapp.DataModelDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Locations.class,HourlyWeatherData.class,DailyWeatherData.class},version = 3)
@TypeConverters({TimeStampConverter.class})
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDataDao weatherDataDao();
    private static WeatherDatabase Inst;

    public static WeatherDatabase getDatabase (final Context context){

        if (Inst == null) {
            synchronized (WeatherDatabase.class) {
                if (Inst == null) {
                    Inst = Room.databaseBuilder(context.getApplicationContext(),
                            WeatherDatabase.class, "weather_database")
                            .build();

                }
            }
        }
        return Inst;
    }

}
