package com.xxxxx.ac.ENTITY;

import java.security.MessageDigest;
import java.util.List;

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
import com.xxxxx.ac.MOD.Mod_Shops;
import com.xxxxx.ac.MOD.Mod_UserInfo;

public class Entity_Login {
	private Context context;
	
	public Entity_Login(Context context){
		this.context = context;
	}
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
				Log.e("DEBUG2", "发生错误1" + arg0.getMessage());
				Toast.makeText(context, "请检查网络问题", 1).show();
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
				Gson gson = new GsonBuilder().create();
				ResponseObject<Mod_CheckCode> result = gson.fromJson(arg0, new TypeToken<ResponseObject<Mod_CheckCode>>(){}.getType());
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", ((Mod_CheckCode)result.getObject()).getRespObject());
				Message message = new Message();
				message.setData(bundle);
				message.what = 1000;
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
				Log.e("DEBUG2", "发生错误2" + arg0.getMessage());
				Toast.makeText(context, "请检查网络问题", 1).show();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String jsonString) {
				Log.e("DEBUG2", jsonString);
				Gson gson = new GsonBuilder().create();
				ResponseObject<Mod_CheckCode> result = gson.fromJson(jsonString, new TypeToken<ResponseObject<Mod_CheckCode>>(){}.getType());
				Mod_CheckCode mod_CheckCode = (Mod_CheckCode)result.getObject();
				Bundle bundle = new Bundle();
				bundle.putSerializable("checkcode", mod_CheckCode);
				Message message = new Message();
				message.what = mod_CheckCode.getErrIndex();
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
	}
	public void doLoginwithPWD(String tel, String pwd, final Handler handler){
		////???未完成，需要继续处理
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("tel", tel);
		pwd = GetMD5(pwd);
		requestParams.addBodyParameter("pwd", pwd); // 使用MD5传递
		requestParams.setUseCookie(true);
		
		// 数据传递给服务器，服务器返回对应数据
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "发生错误3" + arg0.getMessage());
				Toast.makeText(context, "请检查网络问题", 1).show();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DEBUG2", arg0);
				//返回登录成功的信息
			}
		});
	}
	
	//大写 32位
	public final String GetMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
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
}
