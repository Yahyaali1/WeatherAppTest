package com.example.a3.testapp.DataModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by IBM on 7/23/2018.
 */

public class GeoLocation {

    @SerializedName("ParentCity")
    public ParentCity parentCity;

    @SerializedName("Key")
    private String CityCode;
    @SerializedName("LocalizedName")
    private String CityName;

    public GeoLocation(String cityCode, String cityName) {
        CityCode = cityCode;
        CityName = cityName;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
