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
		AlertDialog.Builder dialog = new AlertDialog.Builder(context); // �ȵõ�������
		dialog.setTitle("��ʾ"); // ���ñ���
		dialog.setMessage("���ں��û���ϵ��ȷ���Ѿ������ά��������ٵ��"); // ��������
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { // ����ȷ����ť
					@Override
					public void onClick(DialogInterface dialog, int which) {
		                Message message = new Message();
		                message.what = 300;
		                message.obj = "�Ѿ��������";
		                handler.sendMessage(message);
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
