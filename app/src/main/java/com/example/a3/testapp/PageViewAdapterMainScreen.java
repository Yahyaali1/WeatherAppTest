package com.example.a3.testapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageViewAdapterMainScreen extends FragmentStatePagerAdapter {
    private int NumberOfCities;
    public PageViewAdapterMainScreen(FragmentManager fm,int Number){
        super(fm);
        this.NumberOfCities=Number;
    }
    public PageViewAdapterMainScreen(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //use the position to determine which city Fragment we need to create here
        return new MainScreenFragment();
        //we need to pass here the info so that we can initlize the frament accrodingly

    }

    @Override
    public int getCount() {
        int count=0;
        if(NumberOfCities==0){
            return count;
        }else {
            return NumberOfCities;
        }


    }
}
