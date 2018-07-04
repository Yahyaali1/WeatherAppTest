package com.example.a3.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageViewAdapterDayDetailScreen extends FragmentStatePagerAdapter {
    private int NumberOfDays;
    public PageViewAdapterDayDetailScreen(FragmentManager fm, int Number){
        super(fm);
        this.NumberOfDays=Number;
    }
    public PageViewAdapterDayDetailScreen(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //use the position to determine which city Fragment we need to create here
        return new DailyDetailScreenFragment();
        //we need to pass here the info so that we can initlize the frament accrodingly

    }

    @Override
    public int getCount() {
        int count=0;
        if(NumberOfDays==0){
            return count;
        }else {
            return NumberOfDays;
        }


    }
}
