package com.example.a3.testapp.Dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a3.testapp.SupportClasses.PrefHandle;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleSharedPref {
    @Provides
    @WeatherApplicationScope
    SharedPreferences sharedPreferences(Context context){
        return context.getSharedPreferences(PrefHandle.Companion.getSHARED_PREF(),Context.MODE_PRIVATE);

    }
}
