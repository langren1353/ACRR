package com.remix.acrr.dialog;

import java.io.File;
import java.text.DecimalFormat;

import org.xutils.x;
import org.xutils.http.RequestParams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.remix.acrr.R;
import com.kince.saundprogressbar.widget.SaundProgressBar;
import com.utils.sample.DownloadCallback;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyUtils;

public class DownloadDialog {
	private Context context;
	private Dialog dialog;
	private SaundProgressBar mPbar;
	private int progress = 0;
	private int totalSize;
	private TextView DownPercent;
	private TextView DownDegtree;
	private String APKurl;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				String valueText = (String) msg.obj;
				String[] lengthString  = valueText.split("-");
				int curSize = Integer.parseInt(lengthString[0]);
				int maxSize = Integer.parseInt(lengthString[1]);
				totalSize = maxSize;
				mPbar.setMax(maxSize);
				Log.e("DEBUG2", "下载中 " + curSize + "/" + maxSize);
				mPbar.setProgress(curSize);
				double kk = 0;
				if (maxSize != 0)
					kk = curSize*1.0 / maxSize * 100;
				DecimalFormat format = new DecimalFormat("#0.00");
				DownPercent.setText(format.format(kk)+"%");
				DownDegtree.setText(MyUtils.getPercentageSize(curSize) + "/" + MyUtils.getPercentageSize(maxSize));
				break;
			case 1:
				// 下载完成
				String path = (String) msg.obj;
				Log.e("DEBUG2", "下载完成 "+ path);
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
				context.startActivity(intent);
				mPbar.setMax(totalSize);
				dialog.dismiss();
				break;
			default:
				break;
			}

		}
	};

	public DownloadDialog(Context context) {
		this.context = context;
	}

	public void download(String APK_url) {
		this.APKurl = APK_url;
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null);
		dialog = new AlertDialog.Builder(context).create();
		x.view().inject(dialog, view);
		dialog.show();
		dialog.getWindow().setContentView(view);
		dialog.getWindow().setLayout((int)(CONST.screenWidth * 0.9), (int)(CONST.screenWidth * 0.6));
		DownPercent = (TextView) view.findViewById(R.id.downloadPercent);
		DownDegtree = (TextView) view.findViewById(R.id.downloadDegredd);
		init(view);
	}

	public void init(View view) {
		mPbar = (SaundProgressBar) view.findViewById(R.id.downloadProgressBar);
		mPbar.setMax(0);
		Drawable indicator = view.getResources().getDrawable(R.drawable.progress_indicator);
		Rect bounds = new Rect(0, 0, indicator.getIntrinsicWidth() + 5, indicator.getIntrinsicHeight());
		indicator.setBounds(bounds);
		mPbar.setProgressIndicator(indicator);
		mPbar.setProgress(0);
		mPbar.setVisibility(View.VISIBLE);
		runnable.start();
	}

	Thread runnable = new Thread() {
		@Override
		public void run() {
			Log.e("DEBUG2", "准备下载Thread");
			RequestParams params = new RequestParams(APKurl);
			params.setSaveFilePath("/storage/emulated/0/Download/ACRR.apk");
			x.http().get(params, new DownloadCallback(handler));
//			Download load = new Download(
//					"http://124.161.253.16/dd.myapp.com/16891/0B24D2420CE34621B8DCB22E87A09AED.apk?mkey=571f09e2317f342b&f=2320&c=0&fsname=com.youdao.course_1.3.1_1030100.apk&p=.apk", context);
//			totalSize = load.getLength();
//			load.down2sd("/Download/", "vr.apk", handler);
		}
	};
}
