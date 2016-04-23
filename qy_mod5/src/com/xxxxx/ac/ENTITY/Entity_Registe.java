package com.xxxxx.ac.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.util.Log;

import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.Tools.CommonUtils;

public class Entity_Registe {

	String cookie;

	/**
	 * 
	 * @param type
	 * @param code
	 */
	public void getCheckCode(String type, String code) {
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("type", type);
		requestParams.addBodyParameter("code", code);
		requestParams.setUseCookie(true);
		if (cookie != null)
			requestParams.addBodyParameter("code", code);
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
			}
		});
	}

	public void doRegister(String type, String tel, String code, String pwd) {
		//TODO 未完成，密码等注册未实现
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getCheckCode);
		requestParams.addBodyParameter("type", type);
		requestParams.addBodyParameter("code", code);
		requestParams.setUseCookie(true);
		if (cookie != null)
			requestParams.addBodyParameter("code", code);
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
			}
		});
	}
}
