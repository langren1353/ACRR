package com.remix.acrr.MOD;

import android.R.string;

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
	
	/*public final static class baiduNewsData{
		public final static String key1 = "dim";
		public final static String key1_data = "0";
		public final static String key2 = "id";
		public final static String key2_data = "11583";
		public final static String key3 = "ln";
		public final static String key3_data = "20";
		public final static String key4 = "mid";
		public final static String key4_data = "884D04701F3FDCDDB2BB2C63B015D4EB|0";
		public final static String key5 = "display_time";
		public final static String key5_data = "0";
		public final static String key6 = "name";
		public final static String key6_data = "%E5%AE%B6%E7%94%B5%E7%BB%B4%E6%8A%A4";//家电维护
	}
	public final static String baiduNews = "http://api.baiyue.baidu.com/sn/api/sentimentlist";
	public final static String baiduJsonNode = "sentiment";
	public final static String baiduNews_data = "dim=0&id=11583&ln=20&mid=94387E91731108DA47E7DE1913507513|000000000000000&display_time=0&name=虚拟现实";
*/
	
	public final static String baiduNews = "http://api.baiyue.baidu.com/sn/api/recommendlist";
	
	// 严重注意：在wireshark抓包之后的数据，拷贝之后一定要修改 %7C 这种特殊格式的数据
	public final static String baiduNews_data = "wf=1&mid=884D04701F3FDCDDB2BB2C63B015D4EB|0&withtoppic=1&mb=cm_oneplus2&ln=200&from=news_smart&sv=5.9.5.0&an=20&ts=0&cuid=884D04701F3FDCDDB2BB2C63B015D4EB|0&topic=家电维护&type=search&manu=OnePlus&ver=4";
	
	public final static String baiduJsonNode = "news";
}
