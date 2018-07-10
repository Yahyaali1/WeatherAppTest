package com.example.a3.testapp.DataModel;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WeeklyWeatherData{

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("Night")
	private Night night;

	@SerializedName("Day")
	private Day day;

	@SerializedName("Date")
	private String date;

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