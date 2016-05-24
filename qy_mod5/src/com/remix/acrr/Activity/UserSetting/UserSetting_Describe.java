package com.remix.acrr.Activity.UserSetting;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyUtils;

public class UserSetting_Describe extends Activity {
	@ViewInject(R.id.UserSettingDescribeBack)
	ImageView						UserSettingDescribeBackBtn;
	@ViewInject(R.id.UserSettingDescribeSubmit)
	TextView						UserSettingDescribeSubmit;
	@ViewInject(R.id.UserSettingDescribeContext)
	EditText						UserSettingDescribeContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_setting__describe);
		x.view().inject(this);
		UserSettingDescribeBackBtn.setOnClickListener(new MyUtils.MyFinishClickListener(this));
	}
	
	@Event(R.id.UserSettingDescribeSubmit)
	private void onSubmitListener(View v){
		Intent intent = getIntent();
		String Describe = UserSettingDescribeContext.getText().toString();
		intent.putExtra("describe", Describe);
		setResult(103, intent);
		finish();
	}
}
