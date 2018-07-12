package com.example.a3.testapp.DataModel;


import com.google.gson.annotations.SerializedName;


public class Night{

	@SerializedName("IconPhrase")
	private String iconPhrase;

	public Night(String iconPhrase, int icon) {
		this.iconPhrase = iconPhrase;
		this.icon = icon;
	}

	@SerializedName("Icon")
	private int icon;

	public void setIconPhrase(String iconPhrase){
		this.iconPhrase = iconPhrase;
	}

	public String getIconPhrase(){
		return iconPhrase;
	}

	public void setIcon(int icon){
		this.icon = icon;
	}

	public int getIcon(){
		return icon;
	}

	@Override
 	public String toString(){
		return 
			"Night{" + 
			"iconPhrase = '" + iconPhrase + '\'' + 
			",icon = '" + icon + '\'' + 
			"}";
		}
}