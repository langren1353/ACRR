package com.remix.acrr.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_Orders;

public class Entity_MakeOrder {
private Context context;
	
	public Entity_MakeOrder(Context context){
		this.context = context;
	}
	public void SubmitOrder(Mod_Orders mod_Order, final Handler handler){
		//POST
		String urlString = CONST.host+CONST.makeOrderServlet;
		RequestParams requestParams = new RequestParams(urlString);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		requestParams.setBodyContent(gson.toJson(mod_Order));
		// 数据传递给服务器，服务器返回对应数据
		x.http().post(requestParams, new CommonCallback<String>() {
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
				//Log.e("DEBUG3", arg0);
				Log.e("DEBUG2", arg0);
				Gson gson = new GsonBuilder().create();
				ResponseObject result = gson.fromJson(arg0, new TypeToken<ResponseObject>(){}.getType());
				Message message = new Message();
				message.obj = result.getMsg();
				message.what = 1000;
				handler.sendMessage(message);
			}
		});
	}
}
