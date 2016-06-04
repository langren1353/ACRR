package com.remix.acrr.dialog;

import org.xutils.x;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.remix.acrr.R;

public class AboutDialog {
	private Context context;
	private Dialog dialog;
	public AboutDialog(Context context){
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
		TextView titleTextView = (TextView) view.findViewById(R.id.noupdatetitle);
		titleTextView.setText("关于说明");
		contextText.setText("关于说明，还没想好.......！\t\t\t");
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}
}
