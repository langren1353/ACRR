package com.remix.acrr.MOD;

import java.io.File;

import org.xutils.DbManager;

import com.umeng.fb.model.UserInfo;

import android.R.string;

public class CONST {
	public final static String PACKAGENAME_STRING = "com.remix.acrr"; //注意如果包名修改需要进行对应的修改
//	public final static String host = "http://abscoin.iask.in/QYMod";
	public final static String host = "http://indarkness.imwork.net/ACRR_Server";
//	public final static String host = "http://10.0.3.2:81/ACRR_Server";
	public final static String defaultShopUrl = "/ShopPics/default.jpg";
	
	public final static String uploadServlet = "/upload";
	public final static String getGoosServlet = "/getOrders";
	public final static String getNearBySearlet = "/getNearBy";
	public final static String getCheckCode = "/CodeCheck";
	public final static String updateUrl = "/update.jsp";
	public final static String downloadUpdateUrl = "/apk/ACRR.apk";
	public final static String acceptOrderUrl = "/AcceptOrder";
	public final static String getMyOrders = "/getMyOrders";
	public final static String getNeedMeOrders = "/getNeedMeOrders";
	public final static String makeOrderServlet = "/MakeOrderServlet";
	public final static String getUserInfo = "/getUserInfo";
	public final static String OrderFinishServlet = "/OrderFinishServlet";
	public final static String Login2Servlet = "/Login2Servlet";
	public final static String UpdateUserInfoServlet = "/UpdateUserInfoServlet";
	public final static String pwdChangeServlet = "/PwdChangeServlet";
	
	public final static String totalSetting = "TOTALSETTING";
	public final static String setting_gprs = "IS_GPRS_LOADPIC";
	
	public static int screenWidth = 0;
	public static int screenHeight = 0;

    public static double mylatitude; // 30.58311,103.9849932016
    public static double mylontitude; // 103.9849932016
    public static String myLocationStr;
	
	public final static String SendCKLoginType 	= "1";
	public final static String CKLoginType 		= "3";
	
	public final static String SendCKRegisteType 	= "2";
	public final static String CKRegisterType		= "4";
	
	public final static String WORKERID_STRING = "workerID";
	public final static String ORDERID_STRING  = "orderID";
	public final static String USERID_STRING = "userID";
	public final static String TOKEN_STRING = "token";
	public final static String PWD_New_STRING = "pwdNew";
	public final static String PWD_Old_STRING = "pwdOld";
	
	public final static String PAGEINDEX_STRING = "currentPage";
	public final static String MYTELID_STRING = "myid";
	public static int		page = 1;
	
	public final static int	 OK 		 =  1;
	public final static int	 SENDOK 	 =  0; //发送验证码成功
	public final static int	 ISREGISTED	 = -1; //已注册的不能重复注册
	public final static int	 NOTREGISTED = -2; //未注册的不能登录
	public final static int	 NOTSAME	 = -3; //验证码和手机号不匹配
	public final static int	 NOTINTIME	 = -4; //验证码超时
	public final static int	 NOCODE		 = -5; //无该验证码
	
	public final static int  DELAY		 = 60; //延时
	
	public static Mod_UserInfo userInfo;
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
	
	public final static int newsCount = 80;
	// 严重注意：在wireshark抓包之后的数据，拷贝之后一定要修改 %7C 这种特殊格式的数据 || 控制数量在关键字：an=20
	public final static String baiduNews_data = "wf=1&mid=884D04701F3FDCDDB2BB2C63B015D4EB|0&withtoppic=1&mb=cm_oneplus2&ln=200&from=news_smart&sv=5.9.5.0&an="+newsCount+"&ts=0&cuid=884D04701F3FDCDDB2BB2C63B015D4EB|0&topic=家电维护&type=search&manu=OnePlus&ver=4";
	
	public final static String baiduJsonNode = "news";
	public final static String GDTLocation = "http://restapi.amap.com/v3/geocode/regeo?key=4ca2a01ac205a19c2c7431607f5fdd57&location=LON,LAT"; //切记注意是lon,lat:103.9849932016,30.58311
	//public final static String GGTLocation = "http://maps.google.cn/maps/api/geocode/json?latlng=LAT,LON&language=CN"; //切记注意是lon,lat:103.9849932016,30.58311
	
	public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
	.setDbName("test.db")
	// 不设置dbDir时, 默认存储在app的私有目录.
	.setDbDir(new File("data/data/" + PACKAGENAME_STRING + "/databases")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
	.setDbVersion(2)
	.setDbOpenListener(new DbManager.DbOpenListener() {
		@Override
		public void onDbOpened(DbManager db) {
			// 开启WAL, 对写入加速提升巨大
			db.getDatabase().enableWriteAheadLogging();
		}
	})
	.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
		@Override
		public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
		}
	});
}
