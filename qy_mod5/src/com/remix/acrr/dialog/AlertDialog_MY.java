package com.remix.acrr.dialog;

import com.remix.acrr.R;
import com.remix.acrr.Activity.User.UserLoginActivity;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class AlertDialog_MY {
	private Context context;
	public AlertDialog_MY(Context context){
		this.context = context;
	}
	
	public AlertDialog.Builder getDialog(String title, String text, String btnA, String BtnB, final Handler handler){
		AlertDialog.Builder dialog = new AlertDialog.Builder(context); // �ȵõ�������
		dialog.setTitle(title); // ���ñ���
		dialog.setMessage(text); // ��������
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton(btnA, new DialogInterface.OnClickListener() { // ����ȷ����ť
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Message message = new Message();
						message.obj = "1";
						handler.sendMessage(message);
					}
				});
		dialog.setNegativeButton(BtnB, new DialogInterface.OnClickListener() { //����ȡ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
            	Message message = new Message();
				message.obj = "2";
				handler.sendMessage(message);
            }
        });
		return dialog;
	}
}
