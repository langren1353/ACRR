package com.xxxxx.ac.ENTITY;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.alibaba.sdk.android.security.impl.i;
import com.amap.api.mapcore2d.da;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xxxxx.ac.MOD.baidu.Mod_Data;
import com.xxxxx.ac.MOD.baidu.Mod_Image;
import com.xxxxx.ac.MOD.baidu.Mod_News;

public class Entity_News {
	String jsonString;
	String jsonSentiment;
	int flagSuccess = -1;
	
	public Entity_News(String jsonString){
		this.jsonString = jsonString;
		init();
	}
	public void init(){
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonString);
			int errno = jsonObject.getInt("errno");
			if(errno == -1){
				flagSuccess = -1;
				return;
			}
			jsonObject = jsonObject.getJSONObject("data");
			jsonSentiment = jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Mod_News> getMod_news(){
		Gson gson = new GsonBuilder()
	    .excludeFieldsWithoutExposeAnnotation() // <---
	    .create();
		Mod_Data data = gson.fromJson(jsonSentiment, Mod_Data.class);
		ArrayList<Mod_News> arrayList = data.getSentiment();
		JSONArray jsonArray = null;
		try {
			jsonArray = (JSONArray)new JSONObject(jsonSentiment).get("sentiment");
			for(int i = 0; i < jsonArray.length(); i++){
				if(((JSONObject)jsonArray.get(i)).get("type").equals("text")){
					JSONArray contentArray = (JSONArray)(jsonArray.getJSONObject(i)).get("content");
					for(int j = 0; j < contentArray.length(); j++){
						Object object = ((JSONObject)contentArray.get(j)).get("data");
						if(((JSONObject)contentArray.get(j)).get("type").equals("text")){
							String dataString = (String)object;
							arrayList.get(i).getContent().get(j).setData(dataString);
						}
						else{
							JSONObject imgJson = (JSONObject)object;
							imgJson = imgJson.getJSONObject("original_third");
							String url = (imgJson.getString("url"));
							int height = (imgJson.getInt("height"));
							int width  = (imgJson.getInt("width"));
							arrayList.get(i).getContent().get(j).setImage(new Mod_Image());
							arrayList.get(i).getContent().get(j).getImage().setUrl(url);;
							arrayList.get(i).getContent().get(j).getImage().setHeight(height);
							arrayList.get(i).getContent().get(j).getImage().setWidth(width);
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
}
