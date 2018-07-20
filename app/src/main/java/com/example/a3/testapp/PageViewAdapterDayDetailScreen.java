package com.example.a3.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PageViewAdapterDayDetailScreen extends FragmentStatePagerAdapter {

    private List<HourlyWeatherData> hourlyWeatherData;
    private Locations city;
    private final String tag = "Page_Adapt";

    public PageViewAdapterDayDetailScreen(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(tag,hourlyWeatherData.get(position).getDateTime().toString());

        //use the position to determine which city Fragment we need to create here
        return new FragmentDailyDetailScreen(hourlyWeatherData.get(position).dateTime,city);
        //we need to pass here the info so that we can initlize the frament accrodingly

    }

    public void updateData(List<HourlyWeatherData> data, Locations loc){
        this.city=loc;

        //List<HourlyWeatherData> hourlyWeatherDatatmp= new ArrayList<>();
        if (data!=null){

            for ( int i =0;i<data.size();i++){
                Calendar cal = Calendar.getInstance();
                cal.setTime(data.get(i).dateTime);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
               data.get(i).setDateTime(cal.getTime());

            }
            Log.d(tag,String.valueOf(data.size()));
            for ( int i=0;i<data.size()-1;i++){
                if(data.get(i).dateTime.compareTo(data.get(i+1).dateTime)==0){
                    data.remove(i);
                    i=i-1;
                }
            }


            this.hourlyWeatherData=data;
            Log.d(tag,String.valueOf(hourlyWeatherData.size()));

        }





    }
    @Override
    public int getCount() {

        if(hourlyWeatherData!=null && hourlyWeatherData.size()!=0){
            return hourlyWeatherData.size();
        }else{
            return 0;
        }


    }
}
