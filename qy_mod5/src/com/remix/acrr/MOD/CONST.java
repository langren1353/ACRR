package com.remix.acrr.MOD;

import java.io.File;

import org.xutils.DbManager;

import com.umeng.fb.model.UserInfo;

import android.R.string;

public class CONST {
	public final static String PACKAGENAME_STRING = "com.remix.acrr"; //ע����������޸���Ҫ���ж�Ӧ���޸�
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
	public final static int	 SENDOK 	 =  0; //������֤��ɹ�
	public final static int	 ISREGISTED	 = -1; //��ע��Ĳ����ظ�ע��
	public final static int	 NOTREGISTED = -2; //δע��Ĳ��ܵ�¼
	public final static int	 NOTSAME	 = -3; //��֤����ֻ��Ų�ƥ��
	public final static int	 NOTINTIME	 = -4; //��֤�볬ʱ
	public final static int	 NOCODE		 = -5; //�޸���֤��
	
	public final static int  DELAY		 = 60; //��ʱ
	
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
		public final static String key6_data = "%E5%AE%B6%E7%94%B5%E7%BB%B4%E6%8A%A4";//�ҵ�ά��
	}
	public final static String baiduNews = "http://api.baiyue.baidu.com/sn/api/sentimentlist";
	public final static String baiduJsonNode = "sentiment";
	public final static String baiduNews_data = "dim=0&id=11583&ln=20&mid=94387E91731108DA47E7DE1913507513|000000000000000&display_time=0&name=������ʵ";
*/
	
	public final static String baiduNews = "http://api.baiyue.baidu.com/sn/api/recommendlist";
	
	public final static int newsCount = 80;
	// ����ע�⣺��wiresharkץ��֮������ݣ�����֮��һ��Ҫ�޸� %7C ���������ʽ������ || ���������ڹؼ��֣�an=20
	public final static String baiduNews_data = "wf=1&mid=884D04701F3FDCDDB2BB2C63B015D4EB|0&withtoppic=1&mb=cm_oneplus2&ln=200&from=news_smart&sv=5.9.5.0&an="+newsCount+"&ts=0&cuid=884D04701F3FDCDDB2BB2C63B015D4EB|0&topic=�ҵ�ά��&type=search&manu=OnePlus&ver=4";
	
	public final static String baiduJsonNode = "news";
	public final static String GDTLocation = "http://restapi.amap.com/v3/geocode/regeo?key=4ca2a01ac205a19c2c7431607f5fdd57&location=LON,LAT"; //�м�ע����lon,lat:103.9849932016,30.58311
	//public final static String GGTLocation = "http://maps.google.cn/maps/api/geocode/json?latlng=LAT,LON&language=CN"; //�м�ע����lon,lat:103.9849932016,30.58311
	
	public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
	.setDbName("test.db")
	// ������dbDirʱ, Ĭ�ϴ洢��app��˽��Ŀ¼.
	.setDbDir(new File("data/data/" + PACKAGENAME_STRING + "/databases")) // "sdcard"��д���������ʵ��, ����Ϊ�˼�, ������д��.
	.setDbVersion(2)
	.setDbOpenListener(new DbManager.DbOpenListener() {
		@Override
		public void onDbOpened(DbManager db) {
			// ����WAL, ��д����������޴�
			db.getDatabase().enableWriteAheadLogging();
		}
	})
	.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
		@Override
		public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
		}
	});
}
