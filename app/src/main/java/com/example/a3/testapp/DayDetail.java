package com.example.a3.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DayDetail extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManger;
    String [] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDayDetail);
        setSupportActionBar(toolbar);
        data = new String[10];

        viewManger = new LinearLayoutManager(this,1,false);
        viewAdapter= new MyAdapterDayDetail(data);
        recyclerView = (RecyclerView) findViewById(R.id.dayDetailRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManger);
        recyclerView.setAdapter(viewAdapter);




    }

}
