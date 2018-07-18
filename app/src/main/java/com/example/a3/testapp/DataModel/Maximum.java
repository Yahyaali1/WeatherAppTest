package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Maximum{



	@SerializedName("Value")
	private double value;

	public Maximum(double value) {
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
			"Maximum{" + 
			"value = '" + value + '\'' + 
			"}";
		}
}