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
		AlertDialog.Builder dialog = new AlertDialog.Builder(context); // �ȵõ�������
		dialog.setTitle("��ʾ"); // ���ñ���
		dialog.setMessage("����û�е�¼���Ƿ��¼?"); // ��������
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("��¼", new DialogInterface.OnClickListener() { // ����ȷ����ť
					@Override
					public void onClick(DialogInterface dialog, int which) {
		                dialog.dismiss();
						Intent intent = new Intent();
						intent.setClass(context, UserLoginActivity.class);
						context.startActivity(intent);
					}
				});
		dialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { //����ȡ����ť  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();
            }
        });
		return dialog;
	}
}
