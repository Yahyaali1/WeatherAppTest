package com.example.a3.testapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterAddLocation extends RecyclerView.Adapter<AdapterAddLocation.ViewHolder> {

    private List<Locations> data;
    private Context context;
    private Repo repo;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.textViewRecycleAddLocation) TextView textView;

        @BindView(R.id.buttonRecycleAddLocation)
        FloatingActionButton button;

        //class and methods to mange linear layoutweeklydata

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }


    } //view holder to set all necssary data
    @Override
    public void onBindViewHolder(@NonNull AdapterAddLocation.ViewHolder holder, final int position) {

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
                                repo.dbDeleteDataByCityId(data.get(positionTemp).getLocationId());
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
    @NonNull
    @Override
    public AdapterAddLocation.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutaddlocation,parent,false);
        //creating a view holder to pass it to the linear layoutweeklydata
        return  new ViewHolder(linearLayout);

    }

    //HELPER FUNCTION TO FIND IF THE LOCATIONS ALREADY EXIST BEFORE INSERTION INTO DATABASE
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
    public AdapterAddLocation(Context context){
        this.context=context;
    }
    public AdapterAddLocation(List<Locations> data){
        this.data=data;
    }
    public void changeData(List<Locations> data){
        if(data!=null){
            this.data=data;
            notifyDataSetChanged();
        }
    }

}
