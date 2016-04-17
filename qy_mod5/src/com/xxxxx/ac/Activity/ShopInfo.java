package com.xxxxx.ac.Activity;

import com.xxxxx.ac.R;
import com.xxxxx.ac.MOD.Mod_Shops;
import com.xxxxx.ac.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ShopInfo extends Activity {
	
	
	private Mod_Shops shop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame1_shop_info);
	}
}
