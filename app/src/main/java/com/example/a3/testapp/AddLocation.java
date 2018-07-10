package com.example.a3.testapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.search_id)
    AutoCompleteTextView searchView;
    @BindView(R.id.recycle_city_location) RecyclerView recyclerView;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listItems = new ArrayList<String >();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        searchView.setAdapter(arrayAdapter);
        //adding the call to the api
        setUpFloatingCalls();
        ReteriveValueSelected();





    }
    private void ReteriveValueSelected(){

        searchView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("Found it",String.valueOf(adapterView.getItemAtPosition(i)));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

        searchView.setAdapter(arrayAdapter);
        progressBar.setVisibility(View.INVISIBLE);

    }
    private void enqueCall(){

        //function to show the progress bar
        progressBar.setVisibility(View.VISIBLE);


        WeatherApiInterface weatherApiInterface = weatherApiClient.getClient().create(WeatherApiInterface.class);
        Call<List<SearchCity>> call = weatherApiInterface.getSearchCityDeatils(weatherApiClient.apiKey,searchView.getText().toString());

        call.enqueue(new Callback<List<SearchCity>>() {
            @Override
            public void onResponse(Call<List<SearchCity>> call, Response<List<SearchCity>> response) {
                List<SearchCity> list = response.body();
                if(list==null){

                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
                else if( list.size()==0) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No Such results found, try again",Toast.LENGTH_LONG).show();

                } else {
                   updateDropDownList(list);
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
