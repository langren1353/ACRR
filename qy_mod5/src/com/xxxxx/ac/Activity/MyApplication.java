package com.xxxxx.ac.Activity;

import org.xutils.x;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始
		x.Ext.init(this);
		// 设置是否输出debug
		x.Ext.setDebug(true);
	}

}
