package Tools;

import java.util.HashMap;

import com.cloopen.rest.sdk.CCPRestSDK;

public class SMSSend {

	public static String sendTo(long tel, String code){
		return sendTo(tel+"", code);
	}
	public static String sendTo(String tel, String code){
		/*��Ǯ����Ķ���*/
		HashMap<String, Object> result = null;
		String serverIP = "sandboxapp.cloopen.com";//��������ַ
		String serverPort = "8883";//�˿�
		String accountSid = "8a48b5515427d27601542cc8216506a7";//���ʺ����� ACCOUNT SID
		String accountToken = "83317e1109cd4510b87c3dd54e800c40";//���ʺ����� AUTH TOKEN
		String appId = "aaf98f895427cf5001542ccac36a0765";//Ӧ��ID APP ID
		String templateId = "1";//ģ��Id
		
		CCPRestSDK restAPI = new CCPRestSDK();	
		restAPI.init(serverIP, serverPort);
		restAPI.setAccount(accountSid, accountToken);
		restAPI.setAppId(appId);
		//result = restAPI.sendTemplateSMS("����1,����2��","ģ��Id" ,new String[]{"ģ������1","ģ������2"});
		//���Զ���ģ��id��1,ģ������1��88888��ģ������2��10
		result = restAPI.sendTemplateSMS(tel, templateId, new String[]{code, "5"});
		String resultString;
		if ("000000".equals(result.get("statusCode"))){
			resultString = result.toString();
		}else {
			String errorMsg = "�����룺" + result.get("statusCode") + "������Ϣ = " + result.get("statusMsg");
			resultString = errorMsg;
		}
		System.out.println(resultString);
		return resultString;
	}
}
