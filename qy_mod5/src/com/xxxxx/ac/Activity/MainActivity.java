package com.xxxxx.ac.Activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.xxxxx.ac.R;
import com.xxxxx.ac.frame.Fragment1;
import com.xxxxx.ac.frame.Fragment2;
import com.xxxxx.ac.frame.Fragment3;
import com.xxxxx.ac.frame.Fragment4;

public class MainActivity extends FragmentActivity {

	@ViewInject(R.id.frame)
	FrameLayout frameLayout;
	@ViewInject(R.id.radio_tabBottom)
	RadioGroup radioGroup;
	FragmentPagerAdapter fragmentPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		x.view().inject(this);
		initRadioGroup();
		fragmentPagerAdapter.setPrimaryItem(frameLayout, 0, (Fragment) fragmentPagerAdapter.instantiateItem(frameLayout, 1));
		fragmentPagerAdapter.finishUpdate(frameLayout);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void initRadioGroup(){
		fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return 4;
			}
			@Override
			public Fragment getItem(int arg0) { // ID就是普通ID计数
				Fragment fragment = null;
				switch (arg0) {
				case 0:
					fragment = new Fragment1();
					break;
				case 1:
					fragment = new Fragment2();
					break;
				case 2:
					fragment = new Fragment3();
					break;
				case 3:
					fragment = new Fragment4();
					break;

				default:
					fragment = new Fragment1();
					break;
				}
				return fragment;
			}
		};

		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 底部的radio点击事件
				// 切换需要使用Adapter
				int index = 0;
				switch (checkedId) {
				case R.id.radio0:
					index = 0;
					break;
				case R.id.radio1:
					index = 1;
					break;
				case R.id.radio2:
					index = 2;
					break;
				case R.id.radio3:
					index = 3;
					break;
				}
				Fragment fragment = (Fragment) fragmentPagerAdapter.instantiateItem(frameLayout, index);
				fragmentPagerAdapter.setPrimaryItem(frameLayout, 0, fragment);
				fragmentPagerAdapter.finishUpdate(frameLayout);
			}
		});
	}
}
