package com.remix.acrr.ENTITY;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyUtils;

public class Entity_NearBy {
	public void findNearBy(String lat, String lon, String raidus) {
		double[] around = MyUtils.getAround(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(raidus));
		RequestParams requestParams = new RequestParams(CONST.host + CONST.getNearBySearlet);
		// 数据传递给服务器，服务器返回对应数据
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
				
			}
		});
		
	}
}
