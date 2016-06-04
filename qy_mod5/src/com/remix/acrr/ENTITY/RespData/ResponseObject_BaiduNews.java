package com.remix.acrr.ENTITY.RespData;

import java.util.ArrayList;

import com.remix.acrr.MOD.Mod_Orders;
import com.remix.acrr.MOD.baidu.Mod_Data;
import com.remix.acrr.MOD.baidu.Mod_News;

public class ResponseObject_BaiduNews {
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
