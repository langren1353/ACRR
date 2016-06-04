package MOD;

public class CONST {
//	public final static String host = "http://remix.ac.cn/QYMod";
	public final static String host = "http://indarkness.imwork.net/ACRR_Server";
	public final static String defaultShopUrl = "/ShopPics/default.jpg";
	
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
	
	public final static String WORKERID_STRING = "workerID";
	public final static String ORDERID_STRING  = "orderID";
	public final static String USERID_STRING = "userID";
	public final static String TOKEN_STRING = "token";
	public final static String PWD_New_STRING = "pwdNew";
	public final static String PWD_Old_STRING = "pwdOld";
	
	public final static String PAGEINDEX_STRING = "currentPage";
	
	public final static String MYTELID_STRING = "myid";
}
