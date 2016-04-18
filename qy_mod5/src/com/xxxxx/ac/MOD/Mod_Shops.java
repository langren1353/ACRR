package com.xxxxx.ac.MOD;

import java.io.Serializable;

public class Mod_Shops implements Serializable{
	int id;
	String name_sub;
	String detail;
	String score;
	String tel;
	String addr_lat;
	String addr_lon;
	String addr_text;
	String services;
	String pic_main;
	public static String SHOPINFO = "SHOPINFO";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName_sub() {
		return name_sub;
	}
	public void setName_sub(String name_sub) {
		this.name_sub = name_sub;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr_lat() {
		return addr_lat;
	}
	public void setAddr_lat(String addr_lat) {
		this.addr_lat = addr_lat;
	}
	public String getAddr_lon() {
		return addr_lon;
	}
	public void setAddr_lon(String addr_lon) {
		this.addr_lon = addr_lon;
	}
	public String getAddr_text() {
		return addr_text;
	}
	public void setAddr_text(String addr_text) {
		this.addr_text = addr_text;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getPic_main() {
		return pic_main;
	}
	public void setPic_main(String pic_main) {
		this.pic_main = pic_main;
	}
	
	

}
