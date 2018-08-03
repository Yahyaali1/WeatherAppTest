package com.example.a3.testapp.Dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
 class ModuleContext {
    private final Context context;

    ModuleContext(Context context){
        this.context=context;
    }
    @Provides
    @WeatherApplicationScope
    Context context(){
        return this.context;
    }

}
