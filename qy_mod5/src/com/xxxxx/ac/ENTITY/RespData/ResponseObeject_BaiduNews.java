package com.xxxxx.ac.ENTITY.RespData;

import java.util.ArrayList;

import com.xxxxx.ac.MOD.Mod_Shops;
import com.xxxxx.ac.MOD.baidu.Mod_Data;
import com.xxxxx.ac.MOD.baidu.Mod_News;

public class ResponseObeject_BaiduNews {
	private int errno;
	private Mod_Data data;
	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public Mod_Data getData() {
		return data;
	}
	public void setData(Mod_Data data) {
		this.data = data;
	}
}
