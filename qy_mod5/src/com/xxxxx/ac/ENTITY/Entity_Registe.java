package com.xxxxx.ac.ENTITY;

import java.security.MessageDigest;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xxxxx.ac.ENTITY.RespData.ResponseObject;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_CheckCode;
import com.xxxxx.ac.Tools.CommonUtils;

public class Entity_Registe {

//	String cookie;
	Context context;
	
	public Entity_Registe(Context context){
		this.context = context;
	}
	/**
	 * 
	 * @param type CONST�е�TYPE
	 * @param Tel �ֻ�����
	 */
	public void getCheckCode(String type, String Tel, final Handler handler) {
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("checkType", type);
		requestParams.addBodyParameter("tel", Tel);
		requestParams.setUseCookie(true);
//		if (cookie != null)
//			requestParams.addBodyParameter("code", code);
		// ���ݴ��ݸ������������������ض�Ӧ����
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "��������1" + arg0.getMessage());
				Toast.makeText(context, "������������", 1).show();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DEBUG2", arg0); 
				Gson gson = new GsonBuilder().create();
				ResponseObject<Mod_CheckCode> result = gson.fromJson(arg0, new TypeToken<ResponseObject<Mod_CheckCode>>(){}.getType());
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", (Mod_CheckCode)result.getObject());
				Message message = new Message();
				message.setData(bundle);
				message.what = ((Mod_CheckCode)result.getObject()).getErrIndex();
				handler.sendMessage(message);
			}
		});
	}

	public void doRegister(String type, String tel, String code, String pwd, final Handler handler) {
		//TODO δ��ɣ������ע��δʵ��
		//׼����ʽע�᣺�ϴ��ֻ��š���֤�롢����
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("checkType", type);
		requestParams.addBodyParameter("tel", tel);
		requestParams.addBodyParameter("code", code);
		pwd = GetMD5(pwd);
		requestParams.addBodyParameter("pwd", pwd);
		requestParams.setUseCookie(true);
//		if (cookie != null)
//			requestParams.addBodyParameter("code", code);
		// ���ݴ��ݸ������������������ض�Ӧ����
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "��������2" + arg0.getMessage());
				Toast.makeText(context, "������������", 1).show();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DEBUG2", arg0);
				Gson gson = new GsonBuilder().create();
				ResponseObject<Mod_CheckCode> result = gson.fromJson(arg0, new TypeToken<ResponseObject<Mod_CheckCode>>(){}.getType());
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", (Mod_CheckCode)result.getObject());
				Message message = new Message();
				message.setData(bundle);
				message.what = ((Mod_CheckCode)result.getObject()).getErrIndex();
				handler.sendMessage(message);
			}
		});
	}

	//��д 32λ
	public final String GetMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// ���MD5ժҪ�㷨�� MessageDigest ����
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// ʹ��ָ�����ֽڸ���ժҪ
			mdInst.update(btInput);
			// �������
			byte[] md = mdInst.digest();
			// ������ת����ʮ�����Ƶ��ַ�����ʽ
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
}
