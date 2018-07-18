package com.example.a3.testapp.ViewModelsGroup;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.Date;

/**
 * Created by IBM on 7/16/2018.
 */

public class DayDetailDataFactory extends ViewModelProvider.NewInstanceFactory {
    private final Repo repo;
    private String locationId;
    private Date today;
    private static final String tag = "DayDet_View_Fact";

    public DayDetailDataFactory(Repo repo, String locationId, Date today) {
        this.repo = repo;
        this.locationId = locationId;
        this.today = today;
        Log.d(tag,"Factory Object Constrcutor");

    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DayDetailViewModel(repo,locationId,today);
    }
}
