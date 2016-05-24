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
	 * @param lat γ��
	 * @param lon ����
	 * @param raidus �����뾶 /m
	 * @return ˭��˭��, double��4�� γ��min, γ��max, ����min, ����max
	 */
	public static double[] getAround(double lat, double lon, double raidus){
		// ����ܳ�:40076ǧ��
		// ��ǰγ�ȵ��ܳ� = ����ܳ� * cosγ��
		// 1.�������һ��ÿһ��ռ������
		double degree = 40076 * 1000 / 360.0;
		// 2.����γ���ϱ仯һ���Ƕ��ٶ�
		double dpm_lat = 1 / degree;
		// 3.���������뾶��γ���ϵĶ����仯
		double delta_lat = dpm_lat * raidus;
		double min_lat = lat - delta_lat;
		double max_lat = lat + delta_lat;
		
//		double mpd_lon = 40076 * 1000 * Math.cos(Math.PI*lat/180) / 360 ;
		double mpd_lon = degree * Math.cos(Math.PI*lat/180); // �ȼ�
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
		// 1����ǰ..24����ǰ
		// 1Сʱǰ..24Сʱǰ
		// 1��ǰ...7��ǰ
		// һ��ǰ
		String text[] = {"����ǰ", "Сʱǰ", "��ǰ","һ��ǰ"};
		long timeDf = System.currentTimeMillis() - timestamp;
		long time;
		int type = 0;
		time = timeDf / (1000 * 60 * 60 * 24);
		System.out.println("now is"+timestamp);
		if(time > 7){
			type = 3;
			return text[type];
		}else if(time == 0){ //����?
			time = timeDf / (1000 * 60 * 60);
			if(time == 0){ //��Сʱ��
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
	 * ����content֮��ֱ�Ӿ��ܵ��÷��ط���
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
        // ���ImageView�Ĵ�С���Ƕ���Ϊwrap_content, ��Ҫcrop.
        .setCrop(true) // �ܶ�ʱ�������˺��ʵ�scaleTypeҲ����Ҫ��.
        // �����л����ͼƬ��ScaleType
        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
        .build();
	}
}
