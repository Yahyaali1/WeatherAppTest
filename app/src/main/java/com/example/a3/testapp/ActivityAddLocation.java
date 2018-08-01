package com.example.a3.testapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.DataModel.SearchCity;
import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;
import com.example.a3.testapp.StaticVaraibles.weatherApiClient;
import com.example.a3.testapp.SupportClasses.PrefHandle;
import com.example.a3.testapp.ViewModelsGroup.LocationsViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddLocation extends AppCompatActivity  {

    private RecyclerView.Adapter viewAdapter;
    private Repo repo;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listItems = new ArrayList<>();
    private ArrayList<String> selectedItems = new ArrayList< >();
    private List<SearchCity> listSearchCity=null;
    private AdapterAddLocation myAdapterAddLocation;
    private int indexSelected;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.search_id)
    AutoCompleteTextView searchView;
    @BindView(R.id.AddLocationRecycleView) RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);



        repo=Repo.getRepo(this.getApplicationContext());

        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        viewAdapter = new AdapterAddLocation(this);
        setUpObserverData();
        setUpRecycleView();
        //adding the call to the api
        setUpFloatingCalls();


    }
    private void createDialogBox(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddLocation.this);
        builder.setCancelable(false);
        builder.setTitle("Select the city");
        builder.setNegativeButton("Search Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                indexSelected=i;
                final Locations newLocation = new Locations(listSearchCity.get(i).getCityCode(),listSearchCity.get(i).getCityName());

                if(myAdapterAddLocation.hasCity(newLocation)){
                    Toast.makeText(getApplicationContext(),"Already exist in the list",Toast.LENGTH_LONG).show();

                }else{
                    Thread thread = new Thread() {
                        @Override
                        public void run() {

                            repo.getDb().insertLocation(newLocation);
                            Gson gson = new Gson();
                            String json = gson.toJson(new HourlyWeatherData(newLocation.getLocationId()));
                            PrefHandle.Companion.saveWidgetData(getApplication(),newLocation.getLocationId(),json);
                            repo.UpdateCompleteDataForCity(newLocation.getLocationId());
                        }
                    };
                    thread.start();
                    Toast.makeText(getApplicationContext(),newLocation.getLocationName()+" added to the list ",Toast.LENGTH_LONG).show();
                }

                //selected index. Add this to data basehere and do further processing here.
                dialogInterface.cancel();
                updateRecycleView();
            }
        });

        builder.create();
        builder.show();

    }
    private void setUpRecycleView(){

        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this, 1, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);
    }

    private void updateRecycleView(){
         AdapterAddLocation myAdapterAddLocation = (AdapterAddLocation)viewAdapter;
         myAdapterAddLocation.notifyDataSetChanged();



    }


    private void setUpObserverData(){
        LocationsViewModel locationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel.class);
       locationsViewModel.getActiveLocations().observe(this, new Observer<List<Locations>>() {
           @Override
           public void onChanged(@Nullable List<Locations> locations) {
               myAdapterAddLocation = (AdapterAddLocation) viewAdapter;
               myAdapterAddLocation.changeData(locations);
               Log.d("in Activity Location","Updating the items");
           }
       });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateDropDownList(List<SearchCity> list){
        listItems.clear();
        arrayAdapter.clear();
        for( int i =0 ; i<list.size();i++){
            listItems.add(list.get(i).getCityName());
            //add items to list
        }
        Toast.makeText(getApplicationContext(),"Results Found",Toast.LENGTH_LONG).show();

        arrayAdapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);

//        searchView.setAdapter(arrayAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setForegroundGravity(Gravity.BOTTOM);
        createDialogBox();

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void enqueCall(){

        //function to show the progress bar
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setForegroundGravity(Gravity.TOP);


        WeatherApiInterface weatherApiInterface = weatherApiClient.getClient().create(WeatherApiInterface.class);
        Call<List<SearchCity>> call = weatherApiInterface.apiGetSearchCityDeatils(weatherApiClient.apiKey,searchView.getText().toString());

        call.enqueue(new Callback<List<SearchCity>>() {
            @Override
            public void onResponse(Call<List<SearchCity>> call, Response<List<SearchCity>> response) {
                 listSearchCity = response.body();
                if(listSearchCity==null){

                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
                else if( listSearchCity.size()==0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No Such results found, try again",Toast.LENGTH_LONG).show();

                } else {
                   updateDropDownList(listSearchCity);
                }



            }

            @Override
            public void onFailure(Call<List<SearchCity>> call, Throwable t) {
                Log.d("Error","Error");

            }
        });
    }
    private void setUpFloatingCalls(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if(searchView.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"Please enter some name",Toast.LENGTH_LONG).show();
                }else{
                    enqueCall();
                }


            }
        });
    }


}
