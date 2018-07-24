package com.example.a3.testapp.DataModel;

import com.google.gson.annotations.SerializedName;

public class ParentCity {



    @SerializedName("Key")
    private String CityCode;
    @SerializedName("LocalizedName")
    private String CityName;

    @SerializedName("EnglishName")
    private String EnglishName;

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public ParentCity(String cityCode, String cityName, String englishName) {
        CityCode = cityCode;
        CityName = cityName;
        EnglishName = englishName;
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
