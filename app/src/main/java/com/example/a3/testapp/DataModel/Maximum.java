package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Maximum{

	@SerializedName("Value")
	private int value;

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
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