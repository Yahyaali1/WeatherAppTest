package com.example.a3.testapp;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.GlideApp;
import com.example.a3.testapp.StaticVaraibles.Repo;
import com.example.a3.testapp.SupportClasses.AssetSupport;
import com.example.a3.testapp.SupportClasses.Conversion;
import com.example.a3.testapp.ViewModelsGroup.WeeklyDataViewModel;
import com.example.a3.testapp.ViewModelsGroup.WeeklyDayDataFactory;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class FragmentMainScreen extends Fragment {

    private RecyclerView.Adapter viewAdapter;
    private Locations city;
    private Date today;
    private List<DailyWeatherData> weatherData;
    private Repo repo;
    private RecyclerView.LayoutManager viewManager;
    @BindView(R.id.recycleView) RecyclerView recyclerView;
    @BindView(R.id.imageViewDay) ImageView imageViewDay;
    @BindView(R.id.textViewCity) TextView textViewCity;
    @BindView(R.id.textViewTemp) TextView textViewTemp;
    @BindView(R.id.textViewDate) TextView textViewDate;
    @BindView(R.id.textViewDay) TextView textViewDay;
    @BindView(R.id.textViewLabel) TextView textViewLabel;
    private static final String tag ="Main_Scr_Frag";

    String [] data;
    private AssetSupport assetSupport= new AssetSupport();

    @SuppressLint("ValidFragment")
    public FragmentMainScreen(Locations locations){
      //we will use this methods to insert the data and decide the data to be displayed
        this.city=locations;
        repo=Repo.getRepo(this.getContext()); //might crash here.
        today = Calendar.getInstance().getTime();
        Log.d(tag,today.toString());




    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_main,container,false);
        ButterKnife.bind(this,rootView);

        AddData(); //set up the recyclce view for the main layout

        WeeklyDayDataFactory weeklyDayDataFactory = new WeeklyDayDataFactory(repo,city.getLocationId(),today);
        WeeklyDataViewModel weeklyDataViewModel = ViewModelProviders.of(this,weeklyDayDataFactory).get(WeeklyDataViewModel.class);
        weeklyDataViewModel.getWeeklyData().observe(this, new android.arch.lifecycle.Observer<List<DailyWeatherData>>() {
            @Override
            public void onChanged(@Nullable List<DailyWeatherData> dailyWeatherData) {
                Log.d(tag,"Size of the daily weather data 1 "+dailyWeatherData.size());
                weatherData=dailyWeatherData;
                if(weatherData!=null && weatherData.size()!=0 ){
                    Log.d(tag,"Updating Ui of Main screen ");

                    MainScrrenInfo();
                    GlideApp.with(getContext()).load(assetSupport.getId(weatherData.get(0).getIconIdDay())).transforms(new CenterCrop()).override(200,600)
                            .fitCenter().into(imageViewDay);
                    ;
//        Glide.with(getContext()).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&h=350").apply(RequestOptions.circleCropTransform()).into(imageViewDay);
                }else {

                    //handle the case what to show on the main scrren if there is not city data

                }
                FixedSettings();

                //Notify the smaller recycler view to change data
                MyAdapter tmp = (MyAdapter) viewAdapter;
                tmp.updateData(weatherData);
                viewAdapter.notifyDataSetChanged();


            }
        });
        weatherData=weeklyDataViewModel.getWeeklyData().getValue();

        return rootView;


    }
    private String setDate(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = df.format(today);
        return formattedDate;

    }
    private String setDay(){
        SimpleDateFormat df = new SimpleDateFormat("EEE");
        String formattedDate = df.format(today);
        return formattedDate;

    }

    private void MainScrrenInfo(){

        if(weatherData!=null&&weatherData.size()!=0){
            textViewTemp.setText(Conversion.Convert(Conversion.Choice(this.getActivity()),weatherData.get(0).getTemperatureValueDay()));
            textViewLabel.setText(weatherData.get(0).getIconPhraseDay());
        }

        //set up the icon of the image as well.
        //we need to set up temp according to the list now.

    }
    private void FixedSettings(){
        textViewDate.setText(setDate());
        textViewDay.setText(setDay());
        textViewCity.setText(city.getLocationName());
    }


    private void AddData(){

        //Handle if we need to remap the data or do something about it.

        data = new String[10];
        viewAdapter = new MyAdapter(weatherData,city);
        viewManager = new LinearLayoutManager(getContext(),0,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);

        //internal View Initilized



    }

}
