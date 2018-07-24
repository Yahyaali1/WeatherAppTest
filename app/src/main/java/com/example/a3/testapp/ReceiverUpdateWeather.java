package com.example.a3.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReceiverUpdateWeather  extends BroadcastReceiver {


    private static final String tag="Receiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag,"In Receiver");
        Intent intent1 = new Intent(context.getApplicationContext(), IntentServiceWeatherUpdate.class);
        context.startService(intent1);


    }
}
