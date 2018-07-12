package com.example.a3.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] data;

    public MyAdapter(String[] data){
        this.data=data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.customLinear) LinearLayout linearLayout;
        @BindView(R.id.imageViewRecycleDay) ImageView imageViewDay;
        @BindView(R.id.imageViewRecycleNight) ImageView imageViewNight;
        @BindView(R.id.textViewRecycleDay) TextView textViewDay;
        @BindView(R.id.textViewRecycleTemp)TextView textViewTemp;
        @BindView(R.id.textViewRecycleLabel) TextView textViewLabel;
        //class and methods to mange linear layout

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }



      } //view holder to set all necssary data
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        //creating a view holder to pass it to the linear layout
        ViewHolder vh = new ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {


        holder.imageViewDay.setImageResource(R.drawable.ic_launcher_background);
        holder.imageViewNight.setImageResource(R.drawable.ic_launcher_background);


        //how do we do this here ?
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hello",String.valueOf(position));
                Intent intent = new Intent(view.getContext(),DayDetail.class);
                intent.putExtra(MainActivity.Companion.getDAY_SELECTED(),position);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {

        if(data.length!=0){
         return data.length;
        }

        return 0;
    }


}
