package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Minimum{

	@SerializedName("Value")
	private double value;

	public Minimum(double value) {
		this.value = value;
	}

	public void setValue(double value){
		this.value = value;
	}

	public double getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Minimum{" + 
			"value = '" + value + '\'' + 
			"}";
		}
}