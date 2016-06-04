package com.remix.acrr.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.os.Handler;

import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.dialog.AlertDialog_MY;
import com.remix.acrr.dialog.DownloadDialog;
import com.remix.acrr.dialog.NoUpdateDialog;

public class Entity_CKUpdate {
	private Context context;
	
	public Entity_CKUpdate(Context context){
		this.context = context;
	}
	public void checkUpdate(final Handler handler){
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
				String version = MyAndUtils.getVersion(context);
				if(arg0.equals(version)){
					new NoUpdateDialog(context).showDialog();
				}else{
					String textString = "��鵽���°汾ΪV"+arg0+",��ǰ�汾��V"+MyAndUtils.getVersion(context)+"���ȷ�����и���";
					new AlertDialog_MY(context).getDialog("����",textString, "ȷ��", "ȡ��", handler).show();
				}
			}
		});
	}
}
