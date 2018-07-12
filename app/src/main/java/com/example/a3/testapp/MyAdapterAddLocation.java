package com.example.a3.testapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapterAddLocation extends RecyclerView.Adapter<MyAdapterAddLocation.ViewHolder> {
    private ArrayList<String> data;

    public MyAdapterAddLocation(ArrayList<String> data){
        this.data=data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
       @BindView(R.id.textViewRecycleAddLocation) TextView textView;
       @BindView(R.id.buttonRecycleAddLocation)
        Button button;

        //class and methods to mange linear layout

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }



      } //view holder to set all necssary data
    @NonNull
    @Override
    public MyAdapterAddLocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutaddlocation,parent,false);
        //creating a view holder to pass it to the linear layout
        ViewHolder vh = new ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterAddLocation.ViewHolder holder, final int position) {

        holder.textView.setText(data.get(position));
        holder.button.setTag(position);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionTemp = (int) view.getTag();
                //update the data base
                //update the list of data attached
                data.remove(data.get(positionTemp));
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {

        if(data.size()!=0){
         return data.size();
        }

        return 0;
    }

    public void ChangeData(ArrayList<String> data){
        if(data!=null){
            this.data=data;
            notifyDataSetChanged();
        }
        else {
            /// lets do something here
        }
    }


}
