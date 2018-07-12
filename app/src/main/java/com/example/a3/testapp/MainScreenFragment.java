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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.a3.testapp.StaticVaraibles.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenFragment extends Fragment {

    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManager;
    @BindView(R.id.recycleView) RecyclerView recyclerView;
    @BindView(R.id.imageViewDay)
    ImageView imageViewDay;

    String [] data;
    public MainScreenFragment(){
      //we will use this methods to insert the data and decide the data to be displayed
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.content_main,container,false);
        ButterKnife.bind(this,rootView);
        AddData(); //set up the recyclce view for the main layout
        GlideApp.with(getContext()).clear(imageViewDay);
        GlideApp.with(getContext()).load("https://openweathermap.org/img/w/04d.png").transforms(new CenterCrop()).override(200,600)
            .fitCenter().into(imageViewDay);
        ;

//        Glide.with(getContext()).load("https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?auto=compress&cs=tinysrgb&h=350").apply(RequestOptions.circleCropTransform()).into(imageViewDay);

        return rootView;


    }
    private void AddData(){
        data = new String[10];
        viewAdapter = new MyAdapter(data);
        viewManager = new LinearLayoutManager(getContext(),0,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);

        //internal View Initilized



    }

}
