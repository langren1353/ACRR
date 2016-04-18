package com.xxxxx.ac.Activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.fb.util.Log;
import com.xxxxx.ac.R;
import com.xxxxx.ac.MOD.Mod_Shops;
import com.xxxxx.ac.Tools.CommonUtils;

public class ShopInfoActivity extends Activity {
	@ViewInject(R.id.bg)
	ImageView			bgImageView;
	@ViewInject(R.id.title)
	TextView			titleTextView;
	@ViewInject(R.id.services)
	TextView			servicesTextView;
	@ViewInject(R.id.shopLocation)
	TextView			shopLocationTextView;
	@ViewInject(R.id.shopTel)
	TextView			shopTelTextView;
	@ViewInject(R.id.title_back)
	ImageView			backImageView;
	
	private Mod_Shops shop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame1_shop_info);
		x.view().inject(this);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		shop = (Mod_Shops)bundle.getSerializable(Mod_Shops.SHOPINFO);
		try {
			//bgImageView.setBackground(shop.);
			x.image().bind(bgImageView, shop.getPic_main());
			titleTextView.setText(shop.getName_sub());
			servicesTextView.setText(shop.getServices());
			shopLocationTextView.setText(shop.getAddr_text());
			shopTelTextView.setText(shop.getTel());
			backImageView.setOnClickListener(new CommonUtils.MyFinishClickListener(this));
		} catch (Exception e) {
			Log.e("DEBUG2", e.getMessage());
		}
	}
}
