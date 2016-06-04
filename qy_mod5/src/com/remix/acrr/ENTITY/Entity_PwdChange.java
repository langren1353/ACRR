package com.remix.acrr.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;

public class Entity_PwdChange {

	public void changePwd(String MD5PWD_NEW, String MD5PWD_OLD, String userID, String token, final Handler handler){
		RequestParams requestParams = new RequestParams(CONST.host + CONST.pwdChangeServlet);
		requestParams.addBodyParameter(CONST.USERID_STRING, userID);
		requestParams.addBodyParameter(CONST.TOKEN_STRING, token);
		requestParams.addBodyParameter(CONST.PWD_New_STRING, MD5PWD_NEW);
		requestParams.addBodyParameter(CONST.PWD_Old_STRING, MD5PWD_OLD);
		
		requestParams.setUseCookie(true);
		
		// 数据传递给服务器，服务器返回对应数据
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Log.e("DEBUG2", "发生错误2" + arg0.getMessage());
				//Toast.makeText(context, "请检查网络问题", 1).show();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String jsonString) {
				Log.e("DEBUG2", jsonString);
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				ResponseObject result = gson.fromJson(jsonString, ResponseObject.class);
				
				Message message = new Message();
				message.what = result.getState();
				message.obj = result.getMsg();
				handler.sendMessage(message);
			}
		});
	}
}
