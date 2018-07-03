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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] data;

    public MyAdapter(String[] data){
        this.data=data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        private ImageView imageView;
        private TextView textViewDay;
        private TextView textViewTemp;
        private TextView textViewLabel;


        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout)v.findViewById(R.id.customLinear);
            textViewDay = (TextView)linearLayout.findViewById(R.id.textViewRecycleDay);
            textViewTemp = (TextView) linearLayout.findViewById(R.id.textViewRecycleTemp);
            textViewLabel = (TextView) linearLayout.findViewById(R.id.textViewRecycleLabel);
            imageView =(ImageView) linearLayout .findViewById(R.id.imageViewRecycle);

        }

        public ImageView getImageView() {

            return imageView;
        }

        public TextView getTextViewDay() {
            return textViewDay;
        }

        public TextView getTextViewTemp() {
            return textViewTemp;
        }

        public TextView getTextViewLabel() {
            return textViewLabel;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }
    } //view holder to set all necssary data
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
        ViewHolder vh = new ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.getImageView().setImageResource(R.drawable.ic_launcher_background);
        holder.getTextViewDay().setText("Today");
        holder.getTextViewTemp().setText("21");
        holder.getTextViewLabel().setText(data[position]);
        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hello",String.valueOf(position));
                Intent intent = new Intent(view.getContext(),DayDetail.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.length;
    }


}
