package com.remix.acrr.dialog;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;

public class MyTimePickerDialog extends TimePickerDialog {
    public MyTimePickerDialog(Context context, OnTimeSetListener callBack,
            int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
    }
    public MyTimePickerDialog(Context context, int theme,
            OnTimeSetListener callBack, int hourOfDay, int minute,
            boolean is24HourView) {
        super(context, theme, callBack, hourOfDay, minute, is24HourView);
    }
    @Override
    protected void onStop() {
        // ×¢ÊÍµô£¬·ÀÖ¹onTimeSet()Ö´ÐÐÁ½´Î
        // super.onStop();
    }
}
