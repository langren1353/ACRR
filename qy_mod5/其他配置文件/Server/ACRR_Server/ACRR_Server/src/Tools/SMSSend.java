package Tools;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSDK;

public class SMSSend {

	public static String sendTo(long tel, String code){
		return sendTo(tel+"", code);
	}
	public static String sendTo(String tel, String code){
		/*花钱申请的东西*/
		HashMap<String, Object> result = null;
		String serverIP = "sandboxapp.cloopen.com";//服务器地址
		String serverPort = "8883";//端口
		String accountSid = "8a48b5515427d27601542cc8216506a7";//主帐号名称 ACCOUNT SID
		String accountToken = "83317e1109cd4510b87c3dd54e800c40";//主帐号令牌 AUTH TOKEN
		String appId = "aaf98f895427cf5001542ccac36a0765";//应用ID APP ID
		String templateId = "1";//模板Id
		
		CCPRestSDK restAPI = new CCPRestSDK();	
		restAPI.init(serverIP, serverPort);
		restAPI.setAccount(accountSid, accountToken);
		restAPI.setAppId(appId);
		//result = restAPI.sendTemplateSMS("号码1,号码2等","模板Id" ,new String[]{"模板内容1","模板内容2"});
		//测试短信模板id：1,模板内容1：88888，模板内容2：10
		result = restAPI.sendTemplateSMS(tel, templateId, new String[]{code, "5"});
		String resultString;
		if ("000000".equals(result.get("statusCode"))){
			resultString = result.toString();
		}else {
			String errorMsg = "错误码：" + result.get("statusCode") + "错误信息 = " + result.get("statusMsg");
			resultString = errorMsg;
		}
		System.out.println(resultString);
		return resultString;
	}
}
