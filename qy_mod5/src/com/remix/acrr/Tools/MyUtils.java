package com.remix.acrr.Tools;

import java.text.DecimalFormat;

import org.junit.Test;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyUtils {
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
	public static String getVersion(Context context) {
		PackageInfo manger = null;
		try {
			manger = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return manger.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "Unknown";
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
		System.out.println("now is"+timestamp);
		if(time > 7){
			type = 3;
			return text[type];
		}else if(time == 0){ //几天?
			time = timeDf / (1000 * 60 * 60);
			if(time == 0){ //几小时？
				time = timeDf / (1000 * 60);
				type = 0;
			}else{
				type = 1;
			}
		}else{
			type = 2;
		}
		System.out.println(time+text[type]);
		return time+text[type];
	}
	public static String getPercentageSize(int size){
		double kk = size*1.0/1024/1024;
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		return decimalFormat.format(kk);
	}
	public static String getTimeSize(int size){
		DecimalFormat decimalFormat = new DecimalFormat("00");
		return decimalFormat.format(size);
	}
	@Test
	public void dksilj(){
		int dd = 1;
		System.out.println(getTimeSize(dd));
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
	public static ImageOptions getImageOption(){
		return new ImageOptions.Builder()
        .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
        .setRadius(DensityUtil.dip2px(5))
        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
        .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
        // 加载中或错误图片的ScaleType
        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        .build();
	}
}
