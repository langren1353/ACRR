package com.xxxxx.ac.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxxxx.ac.R;

public class Fragment3 extends Fragment {
	// Fragment相当于 View+生命周期
	// LayoutInfalter载入xml文件为view
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame3, null);
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
