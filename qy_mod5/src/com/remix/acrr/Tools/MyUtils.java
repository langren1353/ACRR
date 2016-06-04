package com.remix.acrr.Tools;

import java.security.MessageDigest;
import java.sql.SQLClientInfoException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

public class MyUtils {
	/**
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @param raidus
	 *            搜索半径 /m
	 * @return 谁入谁出, double【4】 纬度min, 纬度max, 经度min, 经度max
	 */
	public static double[] getAround(double lat, double lon, double raidus) {
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

		// double mpd_lon = 40076 * 1000 * Math.cos(Math.PI*lat/180) / 360 ;
		double mpd_lon = degree * Math.cos(Math.PI * lat / 180); // 等价
		double dpm_lon = 1 / mpd_lon;
		double delta_lon = raidus * dpm_lon;
		double min_lon = lon - delta_lon;
		double max_lon = lon + delta_lon;
		return new double[] { min_lat, max_lat, min_lon, max_lon };
	}

	/**
	 * 
	 * @param lat1
	 * @param lng1
	 *            地址1的经度
	 * @param lat2
	 * @param lng2
	 *            地址2的经度
	 * @return 两点之间的距离
	 */
	public static double GetDistance(String lat1, String lng1, double lat2,
			double lng2) {
		return GetDistance(Double.parseDouble(lat1), Double.parseDouble(lng1),
				lat2, lng2);
	}

	/**
	 * 
	 * @param lat1
	 * @param lng1
	 *            地址1的经度
	 * @param lat2
	 * @param lng2
	 *            地址2的经度
	 * @return 两点之间的距离
	 */
	public static double GetDistance(double lat1, double lon1, double lat2,
			double lon2) {
		double EARTH_RADIUS = 6378137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);

		double radLon1 = rad(lon1);
		double radLon2 = rad(lon2);

		if (radLat1 < 0)
			radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
		if (radLat1 > 0)
			radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
		if (radLon1 < 0)
			radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
		if (radLat2 < 0)
			radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
		if (radLat2 > 0)
			radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
		if (radLon2 < 0)
			radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
		double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
		double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
		double z1 = EARTH_RADIUS * Math.cos(radLat1);

		double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
		double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
		double z2 = EARTH_RADIUS * Math.cos(radLat2);

		double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)
				+ (z1 - z2) * (z1 - z2));
		// 余弦定理求夹角
		double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS
				* EARTH_RADIUS - d * d)
				/ (2 * EARTH_RADIUS * EARTH_RADIUS));
		double dist = theta * EARTH_RADIUS;
		return dist;
	}

	/**
	 * 
	 * @param distance
	 *            实际距离
	 * @return 距离的文字
	 */
	public static String getDisText(double distance) {
		if (distance > 1000) {
			distance /= 1000;
			DecimalFormat dFormat = new DecimalFormat("0.0");
			return dFormat.format(distance) + "km";
		} else {
			int dis = (int) distance;
			return dis + "m";
		}
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static String getOldTimetoNow(long timestamp) {
		// 1分钟前..24分钟前
		// 1小时前..24小时前
		// 1天前...7天前
		// 一周前
		String text[] = { "分钟前", "小时前", "天前", "一周前" };
		long timeDf = System.currentTimeMillis() - timestamp;
		long time;
		int type = 0;
		time = timeDf / (1000 * 60 * 60 * 24);
		System.out.println("now is" + timestamp);
		if (time > 7) {
			type = 3;
			return text[type];
		} else if (time == 0) { // 几天?
			time = timeDf / (1000 * 60 * 60);
			if (time == 0) { // 几小时？
				time = timeDf / (1000 * 60);
				type = 0;
			} else {
				type = 1;
			}
		} else {
			type = 2;
		}
		System.out.println(time + text[type]);
		return time + text[type];
	}

	public static String getPercentageSize(int size) {
		double kk = size * 1.0 / 1024 / 1024;
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		return decimalFormat.format(kk);
	}

	public static String getTimeSize(int size) {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		return decimalFormat.format(size);
	}

	public static String reg_GetFirst(String source, String regStr) {
		Pattern pattern = Pattern.compile(regStr);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			if (matcher.group(1) != null)
				return matcher.group(1);
		}
		return "";
	}

	public static String getFriendlyLength(int lenMeter) {
		if (lenMeter > 10000) // 10 km
		{
			int dis = lenMeter / 1000;
			return dis + "km";
		}
		if (lenMeter > 1000) {
			float dis = (float) lenMeter / 1000;
			DecimalFormat fnum = new DecimalFormat("##0.0");
			String dstr = fnum.format(dis);
			return dstr + "km";
		}
		if (lenMeter > 100) {
			int dis = lenMeter / 50 * 50;
			return dis + "m";
		}
		int dis = lenMeter / 10 * 10;
		if (dis == 0) {
			dis = 10;
		}
		return dis + "m";
	}

	public static String getFriendlyTime(int second) {
		if (second > 3600) {
			int hour = second / 3600;
			int miniate = (second % 3600) / 60;
			return hour + "小时" + miniate + "分钟";
		}
		if (second >= 60) {
			int miniate = second / 60;
			return miniate + "分钟";
		}
		return second + "秒";
	}

	// 大写 32位
	public final static String GetMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str).toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static java.sql.Date getDate(String datestr){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new java.sql.Date(dateFormat.parse(datestr).getTime()) ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new java.sql.Date(System.currentTimeMillis());
	}
	
	public static String getDateStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public static String getDateStr_Full(Timestamp time) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return dateFormat.format(time);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	@Test
	public void dksilj() {
		int dd = 1;
		System.out.println(getTimeSize(dd));
	}

}
