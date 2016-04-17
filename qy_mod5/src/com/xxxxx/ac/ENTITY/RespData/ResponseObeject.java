package com.xxxxx.ac.ENTITY.RespData;

import java.util.ArrayList;

import com.xxxxx.ac.MOD.Mod_Shops;

public class ResponseObeject {
	private String msg;
	private int state = 1;
	private ArrayList<Mod_Shops> list; //存放实际的获得数据
	
	public ResponseObeject(int state, String msg){
		this.state = state;
		this.msg = msg;
	}
	public ResponseObeject(int state, ArrayList<Mod_Shops> obj){
		this.state = state;
		this.list = obj;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public ArrayList<Mod_Shops> getList() {
		return list;
	}
	public void setList(ArrayList<Mod_Shops> list) {
		this.list = list;
	}
	
}
