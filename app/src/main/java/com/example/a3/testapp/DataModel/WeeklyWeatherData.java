package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WeeklyWeatherData{
	private Date prepareDate;

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("Night")
	private Night night;

	@SerializedName("Day")
	private Day day;

	@SerializedName("Date")
	private String date;

	public WeeklyWeatherData(Temperature temperature, Night night, Day day, String date) throws ParseException {
		this.temperature = temperature;
		this.night = night;
		this.day = day;
		this.date = date;
		prepareDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
	}

	public void setTemperature(Temperature temperature){
		this.temperature = temperature;
	}

	public Temperature getTemperature(){
		return temperature;
	}

	public void setNight(Night night){
		this.night = night;
	}

	public Night getNight(){
		return night;
	}

	public void setDay(Day day){
		this.day = day;
	}

	public Day getDay(){
		return day;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	@Override
 	public String toString(){
		return 
			"WeeklyWeatherData{" + 
			"temperature = '" + temperature + '\'' + 
			",night = '" + night + '\'' + 
			",day = '" + day + '\'' + 
			",date = '" + date + '\'' + 
			"}";
		}
}