package com.remix.acrr.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.remix.acrr.R;
import com.remix.acrr.Activity.User.UserLoginActivity;

public class NotLoginDialog {
	private Context context;
	public NotLoginDialog(Context context){
		this.context = context;
	}
	public AlertDialog.Builder getDialog(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context); // 先得到构造器
		dialog.setTitle("提示"); // 设置标题
		dialog.setMessage("您还没有登录，是否登录?"); // 设置内容
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("登录", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss();
						Intent intent = new Intent();
						intent.setClass(context, UserLoginActivity.class);
						context.startActivity(intent);
					}
				});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();
            }
        });
		return dialog;
	}
}
