package com.example.a3.testapp;

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

public class MyAdapterDayDetail extends RecyclerView.Adapter<MyAdapterDayDetail.ViewHolder> {
    private String[] data;
    private int i = 0;
    public MyAdapterDayDetail(String[] data){
        this.data=data;
    }

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

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewTime() {
            return textViewTime;
        }

        public TextView getTextViewTemp() {
            return textViewTemp;
        }

        public TextView getTextViewLabel() {
            return textViewLabel;
        }
    } //view holder to set all necssary data
    @NonNull
    @Override
    public MyAdapterDayDetail.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutdetailedscreen,parent,false);
        ViewHolder vh = new ViewHolder(linearLayout); //inflating the layout
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterDayDetail.ViewHolder holder, final int position) {
        //holder.getImageView().setImageResource(R.drawable.ic_launcher_background);
        //we need to decide how we are suppose to resize the image here as well
        holder.getTextViewTime().setText(String.valueOf(i)+":00 A.M");
        holder.getTextViewTemp().setText("21");
        holder.getTextViewLabel().setText("It will be raining in Lahore");
        i++;
        // we will bind the view here with the data we need



    }

    @Override
    public int getItemCount() {
        return data.length;
    }


}
