package com.example.a3.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.DataModelDataBase.WeatherDatabase;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapterAddLocation extends RecyclerView.Adapter<MyAdapterAddLocation.ViewHolder> {

    private List<Locations> data;
    private Context context;
    private Repo repo;


    Boolean hasCity(Locations locations){
        if(data!=null){
            for (int i =0;i<data.size();i++){
                if(data.get(i).getLocationName().equals(locations.getLocationName()) && data.get(i).getLocationId().equals(locations.getLocationId())){
                    return true;
                }
            }
        }
            return false;
    }

    public MyAdapterAddLocation(Context context){
        this.context=context;
    }

    public MyAdapterAddLocation(List<Locations> data){
        this.data=data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
       @BindView(R.id.textViewRecycleAddLocation) TextView textView;
       @BindView(R.id.buttonRecycleAddLocation)
       FloatingActionButton button;

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
        return  new ViewHolder(linearLayout);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterAddLocation.ViewHolder holder, final int position) {

        holder.textView.setText(data.get(position).getLocationName());
        holder.button.setTag(position);

        holder.button.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int positionTemp = (int) view.getTag();
                //update the data base
                //update the list of data attached
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        repo=Repo.getRepo(context);
                        repo.getDb().deleteLocation(data.get(positionTemp));
                        repo.DeleteDataByCityId(data.get(positionTemp).getLocationId());
                    }
                };

                thread.start();
                notifyDataSetChanged();
            }
        });




    }

    @Override
    public int getItemCount() {

        if(data!=null){
         return data.size();
        }

        return 0;
    }

    public void ChangeData(List<Locations> data){
        if(data!=null){
            this.data=data;
            notifyDataSetChanged();
        }
    }


}
