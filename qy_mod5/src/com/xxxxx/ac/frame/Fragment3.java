package com.xxxxx.ac.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxxxx.ac.R;

public class Fragment3 extends Fragment {
	// Fragment�൱�� View+��������
	// LayoutInfalter����xml�ļ�Ϊview
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame3, null);
		return view;
	}
	// Ӧ�Ե�������
		@Override
		public void setMenuVisibility(boolean menuVisible) {
		    super.setMenuVisibility(menuVisible);
		    if (this.getView() != null)
		        this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
		}
}
