package com.example.a3.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;

import java.util.List;

public class PageViewAdapterDayDetailScreen extends FragmentStatePagerAdapter {

    private List<HourlyWeatherData> hourlyWeatherData;
    private Locations city;

    public PageViewAdapterDayDetailScreen(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //use the position to determine which city Fragment we need to create here
        return new FragmentDailyDetailScreen(hourlyWeatherData.get(position).dateTime,city);
        //we need to pass here the info so that we can initlize the frament accrodingly

    }

    public void updateData(List<HourlyWeatherData> data, Locations loc){
        this.city=loc;
        this.hourlyWeatherData=data;

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
