package com.example.a3.testapp.DataModel;

import com.google.gson.annotations.SerializedName;

public class SearchCity {

    @SerializedName("LocalizedName")
    private String CityName;
    @SerializedName("Key")
    private String CityCode;



    public SearchCity(String cityName, String cityCode) {
        CityName = cityName;
        CityCode = cityCode;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }
}
