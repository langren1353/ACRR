package com.xxxxx.ac.MOD;

public class CONST {
//	public final static String host = "http://abscoin.iask.in/QYMod";
	public final static String host = "http://indarkness.imwork.net/ACRR_Server";
	public final static String defaultShopUrl = "/ShopPics/default.jpg";
	
	public final static String uploadServlet = "/upload";
	public final static String getGoosServlet = "/getShops";
	public final static String getNearBySearlet = "/getNearBy";
	public final static String getCheckCode = "/CodeCheck";
	public final static String updateUrl = "/update.jsp";
	public final static String downloadUpdateUrl = "/apk/ACRR.apk";
	
	public final static String totalSetting = "TOTALSETTING";
	public final static String setting_gprs = "IS_GPRS_LOADPIC";
	
	public static int screenWidth = 0;
	public static int screenHeight = 0;
	
	
	public final static String SendCKLoginType 	= "1";
	public final static String CKLoginType 		= "3";
	
	public final static String SendCKRegisteType 	= "2";
	public final static String CKRegisterType		= "4";
	
	public final static int	 OK 		 =  1;
	public final static int	 SENDOK 	 =  0; //发送验证码成功
	public final static int	 ISREGISTED	 = -1; //已注册的不能重复注册
	public final static int	 NOTREGISTED = -2; //未注册的不能登录
	public final static int	 NOTSAME	 = -3; //验证码和手机号不匹配
	public final static int	 NOTINTIME	 = -4; //验证码超时
	public final static int	 NOCODE		 = -5; //无该验证码
	
	public final static String baiduNews = "http://api.baiyue.baidu.com/sn/api/sentimentlist";
	public final static class baiduNewsData{
		public final static String key1 = "dim";
		public final static String key1_data = "0";
		public final static String key2 = "id";
		public final static String key2_data = "11583";
		public final static String key3 = "ln";
		public final static String key3_data = "20";
		public final static String key4 = "mid";
		public final static String key4_data = "94387E91731108DA47E7DE1913507513%7C000000000000000";
		public final static String key5 = "display_time";
		public final static String key5_data = "0";
		public final static String key6 = "name";
		public final static String key6_data = "VR";
	}
	//public final static String baiduNews_data = "dim=0&id=11583&ln=20&mid=94387E91731108DA47E7DE1913507513%7C000000000000000&display_time=0&name=%E8%99%9A%E6%8B%9F%E7%8E%B0%E5%AE%9E";
}
