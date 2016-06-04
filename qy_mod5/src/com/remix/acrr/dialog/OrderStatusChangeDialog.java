package com.remix.acrr.dialog;

import com.remix.acrr.R;
import com.remix.acrr.Activity.User.UserLoginActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class OrderStatusChangeDialog {
	
	public AlertDialog.Builder getDialog(final Context context, final Handler handler){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context); // 先得到构造器
		dialog.setTitle("提示"); // 设置标题
		dialog.setMessage("请在和用户联系后确认已经完成了维修任务后再点击"); // 设置内容
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() { // 设置确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {
		                Message message = new Message();
		                message.what = 300;
		                message.obj = "已经处理完成";
		                handler.sendMessage(message);
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
