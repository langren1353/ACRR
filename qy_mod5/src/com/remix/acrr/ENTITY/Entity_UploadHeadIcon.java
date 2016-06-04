package com.remix.acrr.ENTITY;

import java.io.ByteArrayOutputStream;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

import com.remix.acrr.MOD.CONST;

public class Entity_UploadHeadIcon {
	public void upload(Bitmap bitmap, String UID, String TOKEN){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat. JPEG, 100, bos);
        byte[] bytes = Base64.encode(bos.toByteArray(), Base64. DEFAULT);
        String url = CONST.host+CONST.uploadServlet;
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter(CONST.USERID_STRING, UID);
        requestParams.addParameter(CONST.TOKEN_STRING, TOKEN);
        requestParams.addBodyParameter("", new String(bytes));
        x.http().post(requestParams, new CommonCallback<String>() {
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
				
			}
		});
	}
}
