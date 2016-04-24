package com.xxxxx.ac.ENTITY.RespData;

import java.io.Serializable;

public class ResponseObject<T> implements Serializable{
	private String msg;
	private int state = 1;
	private T object; //存放实际的获得数据
	
	public ResponseObject(int state, String msg){
		this.state = state;
		this.msg = msg;
	}
	public ResponseObject(int state, T obj){
		this.state = state;
		this.object = obj;
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
	public Object getObject() {
		return object;
	}
	public void setObject(T list) {
		this.object = list;
	}
	
}
