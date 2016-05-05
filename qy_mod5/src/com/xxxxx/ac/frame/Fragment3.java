package com.xxxxx.ac.frame;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xxxxx.ac.R;
import com.xxxxx.ac.Activity.UserLoginActivity;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_UserInfo;

public class Fragment3 extends Fragment {
	@ViewInject(R.id.loginButton)
	Button 								btn_loginButton;
	@ViewInject(R.id.loginNameTextView)
	TextView							loginNameTextView;
	@ViewInject(R.id.UPic)
	ImageView							uPicImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame3, null);
		x.view().inject(this, view);
		btn_loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getContext(), UserLoginActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		return view;
	}

	// 应对叠层现象
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		try {
			Bundle bundle = intent.getExtras();
			Mod_UserInfo userInfo = (Mod_UserInfo) bundle.getSerializable("UserInfo");
			loginNameTextView.setText(userInfo.getName());
			btn_loginButton.setVisibility(View.GONE);
			String url = userInfo.getPic();
			if(!(url == null || url.equals(""))){
				x.image().bind(uPicImageView, CONST.host + url);
			}
		} catch (Exception e) {
			Log.e("DEBUG2", "没有数据传递====未登录");
		}
	}
}
