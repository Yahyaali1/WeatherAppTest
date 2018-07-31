package com.example.a3.testapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.SupportClasses.AssetSupport;
import com.example.a3.testapp.SupportClasses.Conversion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDayDetailSrc extends RecyclerView.Adapter<AdapterDayDetailSrc.ViewHolder> {

    private List<HourlyWeatherData> hourlyWeatherData;
    private Context context;
    private AssetSupport assetSupport = new AssetSupport();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.linearLayoutDetail) LinearLayout linearLayout;

        @BindView(R.id.imageViewDetail) ImageView imageView;
        @BindView(R.id.textViewDetailTime) TextView textViewTime;
        @BindView(R.id.textViewDetailTemp) TextView textViewTemp;
        @BindView(R.id.textViewDetailLabel) TextView textViewLabel;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }


    } //view holder to set all necssary data


    @NonNull
    @Override
    public AdapterDayDetailSrc.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutdetailedscreen,parent,false);
        context=parent.getContext();
        ViewHolder vh = new ViewHolder(linearLayout); //inflating the layoutweeklydata
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterDayDetailSrc.ViewHolder holder, final int position) {
        //holder.getImageView().setImageResource(R.drawable.ic_launcher_background);
        //we need to decide how we are suppose to resize the image here as well
        holder.textViewTime.setText(Conversion.setHour(hourlyWeatherData.get(position).dateTime));
        holder.textViewTemp.setText(Conversion.Convert(Conversion.Choice(context),hourlyWeatherData.get(position).getTemperatureValue()));
        holder.textViewLabel.setText(hourlyWeatherData.get(position).getIconPhrase());
        holder.imageView.setImageResource(assetSupport.getId(hourlyWeatherData.get(position).getIconId()));

        // we will bind the view here with the data we need



    }
    @Override
    public int getItemCount() {

        if(hourlyWeatherData!=null && hourlyWeatherData.size()!=0){
            return hourlyWeatherData.size();
        }else{
            return 0;
        }
    }
    public AdapterDayDetailSrc(List<HourlyWeatherData> data){
        this.hourlyWeatherData=data;
    }
    public AdapterDayDetailSrc(){

    }
    public void updateData(List<HourlyWeatherData> hourlyWeatherData){
        this.hourlyWeatherData=hourlyWeatherData;
    }




}
