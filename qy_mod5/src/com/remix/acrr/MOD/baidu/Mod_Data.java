package com.remix.acrr.MOD.baidu;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Mod_Data {
	
	@Expose private ArrayList<Mod_News> news;

	public ArrayList<Mod_News> getNews() {
		return news;
	}

	public void setNews(ArrayList<Mod_News> sentiment) {
		this.news = sentiment;
	}
}
