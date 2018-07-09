package com.example.a3.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
//using bind View Already
public class DayDetail extends AppCompatActivity {

    @BindView(R.id.DayDetailViewPager) ViewPager viewPager;
    String [] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDayDetail);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        int i = 0;
        i=intent.getIntExtra(MainActivity.Companion.getDAY_SELECTED(),0);

        ButterKnife.bind(this);
        //we need this intent to tell us what s=city has been selected , and what day
        //currently on the day selected is being used
        viewPager.setAdapter(new PageViewAdapterDayDetailScreen(getSupportFragmentManager(),7));
        viewPager.setCurrentItem(i);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            Toast.makeText(getApplicationContext(),"Test go",Toast.LENGTH_LONG).show();
        }else{

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
}
