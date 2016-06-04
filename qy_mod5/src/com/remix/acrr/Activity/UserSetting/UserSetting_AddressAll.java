package com.remix.acrr.Activity.UserSetting;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyAndUtils;

public class UserSetting_AddressAll extends Activity {

	@ViewInject(R.id.UserSettingAddressBack)
	ImageView							UserSettingAddressBack;
	@ViewInject(R.id.UserSettingAddressAdd)
	ImageView							UserSettingAddressAdd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_setting__address_all);
		x.view().inject(this);
		UserSettingAddressBack.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
	}
	@Event(value=R.id.UserSettingAddressAdd)
	private void BtnClickListener(View v){
		Intent intent = new Intent();
		intent.setClass(this, UserSetting_AddressGDMapSelect.class);
		startActivityForResult(intent, 1000);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
