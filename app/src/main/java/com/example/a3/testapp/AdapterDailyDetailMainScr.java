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

import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.SupportClasses.AssetSupport;
import com.example.a3.testapp.SupportClasses.Conversion;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterDailyDetailMainScr extends RecyclerView.Adapter<AdapterDailyDetailMainScr.ViewHolder> {

    private List<DailyWeatherData> dailyWeatherData;
    private Locations city;
    private static final String tag = "MyAdap_Class";
    private Context context;
    private AssetSupport assetSupport;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.customLinear) LinearLayout linearLayout;
        @BindView(R.id.imageViewRecycleDay) ImageView imageViewDay;
        @BindView(R.id.imageViewRecycleNight) ImageView imageViewNight;
        @BindView(R.id.textViewRecycleDay) TextView textViewDay;
        @BindView(R.id.textViewRecycleTemp)TextView textViewTemp;
        @BindView(R.id.textViewRecycleLabel) TextView textViewLabel;

        //class and methods to mange linear layoutweeklydata

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }


    } //view holder to set all necssary data
    @Override
    public int getItemCount() {

        if(dailyWeatherData!=null){
            return dailyWeatherData.size();
        }else {
            return 0;
        }
    }
    @NonNull
    @Override
    public AdapterDailyDetailMainScr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutweeklydata,parent,false);
        //creating a view holder to pass it to the linear layoutweeklydata
        context=parent.getContext();

        return new ViewHolder(linearLayout);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterDailyDetailMainScr.ViewHolder holder, final int position) {


        if( position>=0) {
            holder.linearLayout.setTag(position);
            holder.linearLayout.setTag(R.id.Date,dailyWeatherData.get(position).dateTime);
            holder.imageViewDay.setImageResource(assetSupport.getId(dailyWeatherData.get(position).getIconIdDay()));
            holder.imageViewNight.setImageResource(assetSupport.getId(dailyWeatherData.get(position).getIconIdNight()));
            holder.textViewTemp.setText(prepareString(position));
            holder.textViewLabel.setText(prepareStringPhrase(position));
            holder.textViewDay.setText(Conversion.setDay(dailyWeatherData.get(position).getDateTime()));



            //how do we do this here ?
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Hello",String.valueOf(position));
                    Intent intent = new Intent(view.getContext(),ActivityDayDetail.class);
                    intent.putExtra("Location",city);

                    intent.putExtra("Date",((Date)view.getTag(R.id.Date)).getTime());
                    intent.putExtra(ActivityMainActivity.Companion.getDAY_SELECTED(),(int)view.getTag());
                    view.getContext().startActivity(intent);
                }
            });


        }


    }
    public AdapterDailyDetailMainScr(List<DailyWeatherData> dailyWeatherData, Locations locations){
        this.dailyWeatherData=dailyWeatherData;

        assetSupport=new AssetSupport();
        this.city =locations;

    }
    private String prepareString(int position)
    {
        return Conversion.Convert(Conversion.Choice(context),dailyWeatherData.get(position).getTemperatureValueDay())+" \\"+Conversion.Convert(Conversion.Choice(context),dailyWeatherData.get(position).getTemperatureValueNight());
    }
    private String prepareStringPhrase(int pos){
        return "Day: "+dailyWeatherData.get(pos).getIconPhraseDay()+" \nNight: "+dailyWeatherData.get(pos).getIconPhraseNight();
    }
    public void updateData(List<DailyWeatherData> dailyWeatherData){
        this.dailyWeatherData=dailyWeatherData;
        if(dailyWeatherData.size()!=0){
            Log.d(tag,dailyWeatherData.get(0).getDateTime().toString());
        }

    }



}
