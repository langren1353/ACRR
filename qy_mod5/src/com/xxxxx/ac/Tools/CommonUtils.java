package com.xxxxx.ac.Tools;

import org.junit.Test;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class CommonUtils {
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
}
