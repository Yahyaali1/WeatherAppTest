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
import com.example.a3.testapp.SupportClasses.Conversion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<DailyWeatherData> dailyWeatherData;
    private Locations city;
    private static final String tag = "MyAdap_Class";
    private Context context;


    public MyAdapter(List<DailyWeatherData> dailyWeatherData,Locations locations){
        this.dailyWeatherData=dailyWeatherData;

        this.city =locations;
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
        context=parent.getContext();

        ViewHolder vh = new ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {


        if( position>=0) {
            holder.linearLayout.setTag(position);
            holder.imageViewDay.setImageResource(R.drawable.ic_launcher_background);
            holder.imageViewNight.setImageResource(R.drawable.ic_launcher_background);
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
                    intent.putExtra(ActivityMainActivity.Companion.getDAY_SELECTED(),(int)view.getTag());
                    view.getContext().startActivity(intent);
                }
            });


        }


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

    @Override
    public int getItemCount() {

       if(dailyWeatherData!=null){
           return dailyWeatherData.size();
       }else {
           return 0;
       }
    }


}
