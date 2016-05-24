package com.remix.acrr.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.content.Context;

import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.dialog.DownloadDialog;
import com.remix.acrr.dialog.NoUpdateDialog;

public class Entity_CKUpdate {
	private Context context;
	
	public Entity_CKUpdate(Context context){
		this.context = context;
	}
	public void checkUpdate(){
		RequestParams requestParams = new RequestParams(CONST.host+CONST.updateUrl);
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}
			@Override
			public void onError(Throwable arg0, boolean arg1) {
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onSuccess(String arg0) {
				String version = MyUtils.getVersion(context);
				if(arg0.equals(version)){
					new NoUpdateDialog(context);
				}else{
					new DownloadDialog(context).download(CONST.host+CONST.downloadUpdateUrl);;
				}
			}
		});
	}
}
