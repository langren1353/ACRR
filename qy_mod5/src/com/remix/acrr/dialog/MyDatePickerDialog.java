package com.remix.acrr.dialog;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;

public class MyDatePickerDialog extends DatePickerDialog {
    public MyDatePickerDialog(Context context, int theme, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
		super(context, theme, listener, year, monthOfYear, dayOfMonth);
	}
    public MyDatePickerDialog(Context context, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
		super(context, listener, year, monthOfYear, dayOfMonth);
	}
	@Override
    protected void onStop() {
        // ×¢ÊÍµô£¬·ÀÖ¹onTimeSet()Ö´ÐÐÁ½´Î
        // super.onStop();
    }
}
