package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Minimum{

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
			"Minimum{" + 
			"value = '" + value + '\'' + 
			"}";
		}
}