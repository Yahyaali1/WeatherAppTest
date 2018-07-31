package com.example.a3.testapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.a3.testapp.DataModelDataBase.Locations;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

public class PageViewAdapterMainScreen extends FragmentStatePagerAdapter {
    private int NumberOfCities;
    private List<Locations> selectedLocations=null;
    public PageViewAdapterMainScreen(FragmentManager fm,int Number){
        super(fm);
        this.NumberOfCities=Number;
    }

    public PageViewAdapterMainScreen(FragmentManager fm, List<Locations> locations){
        super(fm);
        this.selectedLocations=locations;

    }
    public PageViewAdapterMainScreen(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //use the position to determine which city Fragment we need to create here
        return new FragmentMainScreen(selectedLocations.get(position));
        //write a specical case if the position is zero we need to do is like show someting or another fragement here on main scrren.
        //we need to pass here the info so that we can initlize the frament accrodingly
        //

    }
    @Override
    public int getCount() {
        int count=0;
        if(selectedLocations!=null){
            return selectedLocations.size();
        }else {
            return 0;
        }


    }
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;

    }
    public void updateData(List<Locations> newLocations){
        selectedLocations=newLocations;
        notifyDataSetChanged();
    }
}
