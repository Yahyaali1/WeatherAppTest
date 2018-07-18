package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ApiHourlyWeatherData {

	private Date prepareDate;

	public Date getPrepareDate() {
		return prepareDate;
	}

	public void setPrepareDate(Date prepareDate) {
		this.prepareDate = prepareDate;
	}

	public boolean isDaylight() {
		return isDaylight;
	}

	public void setDaylight(boolean daylight) {
		isDaylight = daylight;
	}

	public ApiHourlyWeatherData(Temperature temperature, int precipitationProbability, int epochDateTime, String iconPhrase, boolean isDaylight, String dateTime, int weatherIcon, String link, String mobileLink) throws ParseException {
		this.temperature = temperature;
		this.precipitationProbability = precipitationProbability;
		this.epochDateTime = epochDateTime;
		this.iconPhrase = iconPhrase;
		this.isDaylight = isDaylight;
		this.dateTime = dateTime;
		this.weatherIcon = weatherIcon;
		this.link = link;
		this.mobileLink = mobileLink;
		prepareDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm").parse(dateTime);


	}

	public void prepDate() throws ParseException {
		prepareDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm").parse(dateTime);
	}

	@SerializedName("Temperature")
	private Temperature temperature;

	@SerializedName("PrecipitationProbability")
	private int precipitationProbability;

	@SerializedName("EpochDateTime")
	private int epochDateTime;

	@SerializedName("IconPhrase")
	private String iconPhrase;

	@SerializedName("IsDaylight")
	private boolean isDaylight;

	@SerializedName("DateTime")
	private String dateTime;

	@SerializedName("WeatherIcon")
	private int weatherIcon;

	@SerializedName("Link")
	private String link;

	@SerializedName("MobileLink")
	private String mobileLink;

	public void setTemperature(Temperature temperature){
		this.temperature = temperature;
	}

	public Temperature getTemperature(){
		return temperature;
	}

	public void setPrecipitationProbability(int precipitationProbability){
		this.precipitationProbability = precipitationProbability;
	}

	public int getPrecipitationProbability(){
		return precipitationProbability;
	}

	public void setEpochDateTime(int epochDateTime){
		this.epochDateTime = epochDateTime;
	}

	public int getEpochDateTime(){
		return epochDateTime;
	}

	public void setIconPhrase(String iconPhrase){
		this.iconPhrase = iconPhrase;
	}

	public String getIconPhrase(){
		return iconPhrase;
	}

	public void setIsDaylight(boolean isDaylight){
		this.isDaylight = isDaylight;
	}

	public boolean isIsDaylight(){
		return isDaylight;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setWeatherIcon(int weatherIcon){
		this.weatherIcon = weatherIcon;
	}

	public int getWeatherIcon(){
		return weatherIcon;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setMobileLink(String mobileLink){
		this.mobileLink = mobileLink;
	}

	public String getMobileLink(){
		return mobileLink;
	}

	@Override
 	public String toString(){
		return 
			"ApiHourlyWeatherData{" +
			"temperature = '" + temperature + '\'' + 
			",precipitationProbability = '" + precipitationProbability + '\'' + 
			",epochDateTime = '" + epochDateTime + '\'' + 
			",iconPhrase = '" + iconPhrase + '\'' + 
			",isDaylight = '" + isDaylight + '\'' + 
			",dateTime = '" + dateTime + '\'' + 
			",weatherIcon = '" + weatherIcon + '\'' + 
			",link = '" + link + '\'' + 
			",mobileLink = '" + mobileLink + '\'' + 
			"}";
		}
}