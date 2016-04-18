package com.xxxxx.ac.frame;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.xxxxx.ac.R;
import com.xxxxx.ac.Activity.UserLoginActivity;

public class Fragment3 extends Fragment {
	@ViewInject(R.id.loginButton)
	Button							btn_loginButton;
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
				startActivity(intent);
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
}
