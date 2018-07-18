package com.example.a3.testapp.SupportClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.a3.testapp.ActivityMainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IBM on 7/17/2018.
 */

public class Conversion {
    private static final String tag="Class_Conv";
    public static String Convert(int choice,int temp){
        Log.d(tag,String.valueOf(choice));
        if( choice==1){
            return String.valueOf((int)((temp-32)*.5556)+ " C");
        }else{
            return String.valueOf((int)temp)+" F";
        }
    }
    public static int Choice(Activity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);

        return  activity.getApplication().getApplicationContext().getSharedPreferences("Mine",Context.MODE_PRIVATE).getInt(ActivityMainActivity.Companion.getCHOICE_TEMP(),1);
                //sharedPreferences.getInt(ActivityMainActivity.Companion.getCHOICE_TEMP(),0);

    }

    public static int Choice(Context activity){


        return  activity.getApplicationContext().getSharedPreferences("Mine",Context.MODE_PRIVATE).getInt(ActivityMainActivity.Companion.getCHOICE_TEMP(),1);
        //sharedPreferences.getInt(ActivityMainActivity.Companion.getCHOICE_TEMP(),0);

    }

    public static String setHour(Date today){
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String formattedDate = df.format(today);
        return formattedDate;
    }
    public static String setDay(Date today){
        SimpleDateFormat df = new SimpleDateFormat("EEE");
        String formattedDate = df.format(today);
        return formattedDate;

    }
}
