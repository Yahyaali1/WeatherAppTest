package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Temperature{

	@SerializedName("UnitType")
	private int unitType;

	@SerializedName("Value")
	private double value;

	public Temperature(int unitType, double value, String unit) {
		this.unitType = unitType;
		this.value = value;
		this.unit = unit;
	}

	@SerializedName("Unit")
	private String unit;

	public void setUnitType(int unitType){
		this.unitType = unitType;
	}

	public int getUnitType(){
		return unitType;
	}

	public void setValue(double value){
		this.value = value;
	}

	public double getValue(){
		return value;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}

	public String getUnit(){
		return unit;
	}

	@Override
 	public String toString(){
		return 
			"Temperature{" + 
			"unitType = '" + unitType + '\'' + 
			",value = '" + value + '\'' + 
			",unit = '" + unit + '\'' + 
			"}";
		}
}