package com.example.a3.testapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;
import com.example.a3.testapp.ViewModelsGroup.DayDetailDataFactory;
import com.example.a3.testapp.ViewModelsGroup.DayDetailViewModel;
import com.example.a3.testapp.ViewModelsGroup.NumberofDaysViewModel;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//using bindview Already
public class FragmentDailyDetailScreen extends Fragment {

    @BindView(R.id.dayDetailRecycleView) RecyclerView recyclerView;
    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManager;
    private Date today;
    private Locations city;
    private List<HourlyWeatherData> weatherData;
    private Repo repo;
    private static String tag="DayDetail_Frag";
    public FragmentDailyDetailScreen(){
      //we will use this methods to insert the data and decide the data to be displayed
    }
    @SuppressLint("ValidFragment")
    public FragmentDailyDetailScreen(Date date, Locations city){
        this.city=city;
        this.today =date;
        repo=Repo.getRepo(this.getContext());
        weatherData=null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_day_detail,container,false);
        ButterKnife.bind(this,rootView);

        viewAdapter = new MyAdapterDayDetail();


        DayDetailDataFactory dayDetailDataFactory = new DayDetailDataFactory(repo,city.getLocationId(),today);
        DayDetailViewModel dayDetailViewModel = ViewModelProviders.of(this,dayDetailDataFactory).get(DayDetailViewModel.class);
        dayDetailViewModel.getDailyData().observe(this, new Observer<List<HourlyWeatherData>>() {
            @Override
            public void onChanged(@Nullable List<HourlyWeatherData> hourlyWeatherData) {

                if(hourlyWeatherData!=null && hourlyWeatherData.size()!=0){

                    MyAdapterDayDetail myAdapterDayDetail=(MyAdapterDayDetail) viewAdapter;
                    myAdapterDayDetail.UpdateData(hourlyWeatherData);
                    myAdapterDayDetail.notifyDataSetChanged();
                    Log.d(tag,"Main Fragement Data Changed");

                }
            }
        });



        AddData(); //set up the recyclce view for the main layout



        return rootView;


    }
    private void AddData(){


        viewManager = new LinearLayoutManager(getContext(),1,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);

        //internal View Initilized



    }

}
