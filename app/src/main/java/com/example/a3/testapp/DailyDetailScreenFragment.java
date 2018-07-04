package com.example.a3.testapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

//using bindview Already
public class DailyDetailScreenFragment extends Fragment {

    @BindView(R.id.dayDetailRecycleView) RecyclerView recyclerView;
    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManager;
    String [] data;
    public DailyDetailScreenFragment(){
      //we will use this methods to insert the data and decide the data to be displayed
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_day_detail,container,false);
        ButterKnife.bind(this,rootView);
        AddData(); //set up the recyclce view for the main layout



        return rootView;


    }
    private void AddData(){
        data = new String[10];
        viewAdapter = new MyAdapterDayDetail(data);
        viewManager = new LinearLayoutManager(getContext(),1,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);

        //internal View Initilized



    }

}
