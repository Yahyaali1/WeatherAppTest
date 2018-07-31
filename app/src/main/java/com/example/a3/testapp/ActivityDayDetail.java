package com.example.a3.testapp;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;
import com.example.a3.testapp.SupportClasses.updateHandler;
import com.example.a3.testapp.ViewModelsGroup.NumberOfDaysDataFactory;
import com.example.a3.testapp.ViewModelsGroup.NumberofDaysViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//using bind View Already
public class ActivityDayDetail extends AppCompatActivity {
    private Locations city;
    private int activeDay;
    private Date today;
    private Repo repo;
    private static final String tag="DayDet_Activity";

    @BindView(R.id.DayDetailViewPager) ViewPager viewPager;
    @BindView(R.id.fabDayDetail) FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        Toolbar toolbar = findViewById(R.id.toolbarDayDetail);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        city = (Locations) intent.getExtras().getSerializable("Location");
        activeDay= intent.getIntExtra(ActivityMainActivity.Companion.getDAY_SELECTED(),1);
        today = Calendar.getInstance().getTime(); //setting up the date

        repo=Repo.getRepo(this);
        ButterKnife.bind(this);
        //we need this intent to tell us what s=city has been selected , and what day

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate));
                if(connected()){
                    repo.getHourlyData(city.getLocationId());
                    updateHandler.Companion.widgetUpdate(getApplication());
                }else {
                    Snackbar.make(view, "Please connect to internet ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        //currently on the day selected is being used
        setUpViewModel();





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings){

            Intent intent = new Intent(this.getApplicationContext(),ActivitySettingsPage.class);
            startActivity(intent);

        }else if (item.getItemId()==R.id.action_add_location){
            Intent intent = new Intent(this.getApplicationContext(),ActivityAddLocation.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem()==0){
        super.onBackPressed();}else{
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    }
    private boolean connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
    private void setUpViewModel() {
        viewPager.setAdapter(new PageViewAdapterDayDetailScreen(getSupportFragmentManager()));
        NumberOfDaysDataFactory numberOfDaysDataFactory = new NumberOfDaysDataFactory(repo,city.getLocationId(),today);
        NumberofDaysViewModel numberofDaysViewModel = ViewModelProviders.of(this,numberOfDaysDataFactory).get(NumberofDaysViewModel.class);
        numberofDaysViewModel.getWeeklyData().observe(this, new Observer<List<HourlyWeatherData>>() {
            @Override
            public void onChanged(@Nullable List<HourlyWeatherData> hourlyWeatherData) {
                if(hourlyWeatherData!=null && hourlyWeatherData.size()!=0){
                    PageViewAdapterDayDetailScreen pageViewAdapterDayDetailScreen = (PageViewAdapterDayDetailScreen) viewPager.getAdapter();
                    pageViewAdapterDayDetailScreen.updateData(hourlyWeatherData,city);
                    pageViewAdapterDayDetailScreen.notifyDataSetChanged();
                    viewPager.setCurrentItem(activeDay);
                    Log.d(tag,"Adding the days "+hourlyWeatherData.size());
                    updateHandler.Companion.widgetUpdate(getApplication());


                }
            }
        });
    }


}
