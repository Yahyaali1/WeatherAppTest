package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class TemperatureWeekly {

	@SerializedName("Minimum")
	private Minimum minimum;


	@SerializedName("Maximum")
	private Maximum maximum;

	public TemperatureWeekly(Minimum minimum, Maximum maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public Minimum getMinimum() {
		return minimum;
	}

	public void setMinimum(Minimum minimum) {
		this.minimum = minimum;
	}

	public Maximum getMaximum() {
		return maximum;
	}

	public void setMaximum(Maximum maximum) {
		this.maximum = maximum;
	}
}