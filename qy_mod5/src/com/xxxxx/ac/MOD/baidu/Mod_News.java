package com.xxxxx.ac.MOD.baidu;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Mod_News implements Serializable{
	@Expose private String title;
	@Expose private String ts;
	@Expose private String url;
	@Expose private ArrayList<Mod_Image> imageurls;
	@Expose private String site;
	@Expose private ArrayList<Mod_Content> content;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<Mod_Image> getImageurls() {
		return imageurls;
	}
	public void setImageurls(ArrayList<Mod_Image> imageurls) {
		this.imageurls = imageurls;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public ArrayList<Mod_Content> getContent() {
		return content;
	}
	public void setContent(ArrayList<Mod_Content> content) {
		this.content = content;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
}
