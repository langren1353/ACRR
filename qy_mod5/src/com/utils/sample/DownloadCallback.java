package com.utils.sample;

import java.io.File;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;

import android.os.Handler;
import android.os.Message;

/**
 * Created by wyouflf on 15/11/10.
 */
public class DownloadCallback implements Callback.ProgressCallback<File> {
	private DownloadInfo downloadInfo;
	private Handler handler;
	public DownloadCallback(Handler handler) {
		this.handler = handler;
		downloadInfo = new DownloadInfo();
	}
	
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
	public void onSuccess(File file) {
		Message message = new Message();
		message.what = 1;
		message.obj = file.getPath();
		handler.sendMessage(message);
	}

	@Override
	public void onLoading(long total, long current, boolean isDownloading) {
		if (isDownloading) {
			try {
				downloadInfo.setState(DownloadState.STARTED);
				downloadInfo.setFileLength(total);
				downloadInfo.setProgress((int) (current * 100 / total));
				Message message = new Message();
				message.what = 0;
				message.obj = current+"-"+total;
				handler.sendMessage(message);
			} catch (Exception ex) {
				LogUtil.e(ex.getMessage(), ex);
			}
		}
	}

	@Override
	public void onStarted() {
	}

	@Override
	public void onWaiting() {
	}
}
