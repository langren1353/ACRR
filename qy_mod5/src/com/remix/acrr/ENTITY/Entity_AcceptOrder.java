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

public class Entity_AcceptOrder {
	private Context context;
	public Entity_AcceptOrder(Context context){
		this.context = context;
	}
	public void getResult(String workerID, String OrderID, final Handler handler) {
		String urlString = CONST.host+CONST.acceptOrderUrl+"?"+CONST.WORKERID_STRING+"="+workerID+"&"+CONST.ORDERID_STRING+"="+OrderID;
		RequestParams requestParams = new RequestParams(urlString);
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
