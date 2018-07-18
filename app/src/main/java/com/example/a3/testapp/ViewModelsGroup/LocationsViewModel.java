package com.example.a3.testapp.ViewModelsGroup;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.List;

/**
 * Created by IBM on 7/16/2018.
 */

public class LocationsViewModel extends AndroidViewModel {
    private LiveData<List<Locations>> activeLocations;
    private List<Locations> cities;
    private static final String tag = "Loc_View_Model";

    public List<Locations> getCities() {
        return cities;
    }

    public LiveData<List<Locations>> getActiveLocations() {
        return activeLocations;
    }


    public LocationsViewModel(@NonNull Application application) {
        super(application);
        Repo repo = Repo.getRepo(this.getApplication());
        activeLocations = repo.getAllLocations();
        if(activeLocations==null){
            Log.d(tag,"Active locations are null");
        }else {
            Log.d(tag,"Locations Found");
        }

    }

}
