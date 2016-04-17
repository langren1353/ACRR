package com.xxxxx.ac.MOD.baidu;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Mod_Data {
	
	@Expose private ArrayList<Mod_News> sentiment;

	public ArrayList<Mod_News> getSentiment() {
		return sentiment;
	}

	public void setSentiment(ArrayList<Mod_News> sentiment) {
		this.sentiment = sentiment;
	}
}
