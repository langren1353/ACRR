package com.remix.acrr.Tools;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.xutils.common.Callback.CacheCallback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MyAndUtils {

	public static String getVersion(Context context) {
		PackageInfo manger = null;
		try {
			manger = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return manger.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "Unknown";
	}

	/**
	 * 带上content之后，直接就能调用返回方法
	 * 
	 */
	public static class MyFinishClickListener implements OnClickListener {
		private Context context;

		public MyFinishClickListener(Context context) {
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			((Activity) context).finish();
		}
	}
	public static ImageOptions getImageOption() {
		return new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
				.setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(false) // 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.build();
	}
	public static CacheCallback<Drawable> getCacheCallBack(){
		 return new CacheCallback<Drawable>() {
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
			public void onSuccess(Drawable arg0) {
			}
			@Override
			public boolean onCache(Drawable arg0) {
				return false;
			}
		};
	}
}
