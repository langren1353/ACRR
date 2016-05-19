package com.xxxxx.ac.dialog;

import org.xutils.x;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xxxxx.ac.R;

public class NoUpdateDialog {
	private Context context;
	private Dialog dialog;
	public NoUpdateDialog(Context context){
		this.context = context;
	}
	public Dialog showDialog() {
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_noupdate, null);
		dialog = new AlertDialog.Builder(context).create();
		x.view().inject(dialog, view);
		dialog.show(); //这个必须要有，之后才能getWindow
		dialog.getWindow().setContentView(view);
		TextView btnOK = (TextView) view.findViewById(R.id.updateOKBtn);
		TextView contextText = (TextView) view.findViewById(R.id.textView2);
		contextText.setText("当前已经是最新版本！\t\t\t");
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		return dialog;
	}
}
