package com.remix.acrr.MOD.baidu;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Mod_Image implements Serializable{
	@Expose private String url;
	@Expose private int height;
	@Expose private int width;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int wdith) {
		this.width = wdith;
	}
	
	
}
