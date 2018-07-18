package com.example.a3.testapp.DataModelDataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity (tableName = "locations")
public class Locations implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "locationId")
    @NonNull
    private String locationId;
    @ColumnInfo(name = "locationName")
    private String locationName;

    public Locations(String locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
