package com.xxxxx.ac.Tools;

import org.junit.Test;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class CommonUtils {
	/**
	 * 
	 * @param lat 纬度
	 * @param lon 经度
	 * @param raidus 搜索半径 /m
	 * @return 谁入谁出, double【4】 纬度min, 纬度max, 经度min, 经度max
	 */
	public static double[] getAround(double lat, double lon, double raidus){
		// 赤道周长:40076千米
		// 当前纬度的周长 = 赤道周长 * cos纬度
		// 1.计算地球一周每一度占多少米
		double degree = 40076 * 1000 / 360.0;
		// 2.计算纬度上变化一米是多少度
		double dpm_lat = 1 / degree;
		// 3.计算搜索半径在纬度上的度数变化
		double delta_lat = dpm_lat * raidus;
		double min_lat = lat - delta_lat;
		double max_lat = lat + delta_lat;
		
//		double mpd_lon = 40076 * 1000 * Math.cos(Math.PI*lat/180) / 360 ;
		double mpd_lon = degree * Math.cos(Math.PI*lat/180); // 等价
		double dpm_lon = 1/mpd_lon;
		double delta_lon = raidus * dpm_lon;
		double min_lon = lon - delta_lon;
		double max_lon = lon + delta_lon;
		return new double[]{min_lat, max_lat, min_lon, max_lon};
	}
	
	public static String getOldTimetoNow(long timestamp){
		// 1分钟前..24分钟前
		// 1小时前..24小时前
		// 1天前...7天前
		// 一周前
		String text[] = {"分钟前", "小时前", "天前","一周前"};
		long timeDf = System.currentTimeMillis() - timestamp;
		long time;
		int type = 0;
		time = timeDf / (1000 * 60 * 60 * 24);
		if(time == 0){
			time = timeDf / (1000 * 60 * 60);
			if(time == 0){
				time = timeDf / (1000 * 60);
				type = 0;
			}else{
				type = 1;
			}
		}else{
			type = 2;
		}
		if(time > 7)
			type = 3;
		return time+text[type];
	}
	@Test
	public void dksilj(){
		long dd = 1460448046000L;
		System.out.println(getOldTimetoNow(dd));
	}
	
	/**
	 * 带上content之后，直接就能调用返回方法
	 * 
	 */
	public static class MyFinishClickListener implements OnClickListener{
		private Context context;
		public MyFinishClickListener(Context context){
			this.context = context;
		}
		@Override
		public void onClick(View v) {
			 ((Activity)context).finish();
		}
	}
}
