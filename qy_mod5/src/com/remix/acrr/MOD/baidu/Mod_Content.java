package com.remix.acrr.MOD.baidu;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Mod_Content implements Serializable{
	@Expose private String type;
	private String data; //�����Զ������ȡ��������� ��Ҫʹ��transient�ؼ���
	private Mod_Image image;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Mod_Image getImage() {
		return image;
	}
	public void setImage(Mod_Image image) {
		this.image = image;
	}
}
