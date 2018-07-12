package com.example.a3.testapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.DataModel.SearchCity;
import com.example.a3.testapp.StaticVaraibles.weatherApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLocation extends AppCompatActivity {

    private RecyclerView.Adapter viewAdapter;
    private RecyclerView.LayoutManager viewManager;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.search_id)
    AutoCompleteTextView searchView;
    @BindView(R.id.AddLocationRecycleView) RecyclerView recyclerView;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listItems = new ArrayList<String >();
    private ArrayList<String> selectedItems = new ArrayList<String >();

    private List<SearchCity> listSearchCity=null;

    private AlertDialog.Builder builder;
    private int indexSelected;

    private void CreateDialogBox(){

        builder = new AlertDialog.Builder(AddLocation.this);
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
                Toast.makeText(getApplicationContext(),listSearchCity.get(i).getCityName(),Toast.LENGTH_LONG).show();
                indexSelected=i;
                selectedItems.add(listSearchCity.get(i).getCityName());

                //selected index. Add this to data basehere and do further processing here.
                dialogInterface.cancel();
                UpdateRecycleView();
            }
        });

        builder.create();
        builder.show();

    }
    private void SetUpRecycleView(){
        viewAdapter = new MyAdapterAddLocation(selectedItems);
        viewManager = new LinearLayoutManager(this,1,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(viewManager);
        recyclerView.setAdapter(viewAdapter);
    }
    private void UpdateRecycleView(){
         MyAdapterAddLocation myAdapterAddLocation = (MyAdapterAddLocation)viewAdapter;
         myAdapterAddLocation.notifyDataSetChanged();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        SetUpRecycleView();
        //adding the call to the api
        setUpFloatingCalls();






    }

    private void updateDropDownList(List<SearchCity> list){
        listItems.clear();
        arrayAdapter.clear();
        for( int i =0 ; i<list.size();i++){
            listItems.add(list.get(i).getCityName());

            //add items to list


        };
        Toast.makeText(getApplicationContext(),"Results Found",Toast.LENGTH_LONG).show();

        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItems);

//        searchView.setAdapter(arrayAdapter);
        progressBar.setVisibility(View.INVISIBLE);
        CreateDialogBox();

    }
    private void enqueCall(){

        //function to show the progress bar
        progressBar.setVisibility(View.VISIBLE);


        WeatherApiInterface weatherApiInterface = weatherApiClient.getClient().create(WeatherApiInterface.class);
        Call<List<SearchCity>> call = weatherApiInterface.getSearchCityDeatils(weatherApiClient.apiKey,searchView.getText().toString());

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
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
