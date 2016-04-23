package com.xxxxx.ac.ENTITY;

import java.io.UnsupportedEncodingException;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_CheckCode;
import com.xxxxx.ac.Tools.CommonUtils;

public class Entity_Login {

	/**
	 * 
	 * @param type
	 * @param code
	 */
	public void getCheckCode(String type, String tel, final Handler handler) {
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("checkType", type);
		requestParams.addBodyParameter("tel", tel);
		requestParams.setUseCookie(true);
		// 数据传递给服务器，服务器返回对应数据
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "发生错误" + arg0.getMessage());
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
//				String result = "";
//				try {
//					result = new String(arg0.getBytes("ISO-8859-1"), "UTF-8");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				Log.e("DEBUG2", arg0);
				
				Mod_CheckCode mod_CheckCode = new Gson().fromJson(arg0, Mod_CheckCode.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", mod_CheckCode);
				Message message = new Message();
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
	}

	public void doLogin(String type, String tel, String code, final Handler handler){
		////???未完成，需要继续处理
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("checkType", type);
		requestParams.addBodyParameter("tel", tel);
		requestParams.addBodyParameter("code", code);
		requestParams.setUseCookie(true);
		
		// 数据传递给服务器，服务器返回对应数据
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "发生错误" + arg0.getMessage());
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DEBUG2", arg0);
				Mod_CheckCode mod_CheckCode = new Gson().fromJson(arg0, Mod_CheckCode.class);
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", mod_CheckCode);
				Message message = new Message();
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
	}
}
