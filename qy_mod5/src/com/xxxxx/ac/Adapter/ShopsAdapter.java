package com.xxxxx.ac.Adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import com.google.gson.Gson;
import com.xxxxx.ac.R;
import com.xxxxx.ac.Activity.GDMapActivity;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_Shops;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopsAdapter extends BaseAdapter {
	private List<Mod_Shops> shops;
	private Context context;
	private boolean is_loadpic;
	public ShopsAdapter(List<Mod_Shops> myDataList, Context context, boolean is_loadPIC) {
		shops = myDataList;
		this.context = context;
		this.is_loadpic = is_loadPIC;
	}

	@Override
	public int getCount() {
		return shops.size();
	}

	@Override
	public Object getItem(int position) {
		if (shops == null || position > shops.size())
			return null;
		return shops.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
//		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame1_oneitem, null);
			x.view().inject(holder, convertView);
			convertView.setTag(holder);
//		}
//		Log.e("DEBUG2", shops.size()+"||");
		Mod_Shops shop = shops.get(position);
		if(shop.getPic_main() != null && !shop.getPic_main().equals("")){
			Log.e("DEBUG2", shop.getPic_main()+"||");
			if(is_loadpic == true)
				x.image().bind(holder.bgImageView, shop.getPic_main());
		}else {
			holder.bgImageView.setImageResource(R.drawable.fram1_oneitem_pic);
		}
		holder.titleTextView.setText(shop.getName_sub());
		// holder.starImageView.set 特殊，暂时不设置
		holder.locationTextView.setText(shop.getAddr_text());
		holder.servicesTextView.setText(shop.getDiscript());
		// holder.farawayTextView 特殊，暂时不设置
		holder.layout.setOnClickListener(new myCLickListener(position));
		return convertView;
	}

	public class myCLickListener implements OnClickListener {
		int position;
		public myCLickListener(int position) {
			Log.e("DEBUG", position + " +++ ");
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Log.e("DEBUG", "点击位置 " + position);
			Intent intent = new Intent();
			intent.setClass(context, GDMapActivity.class);
			context.startActivity(intent);
		}
	}

	public class ViewHolder {
		@ViewInject(R.id.gooditem)
		View layout;
		// 搞定对应的view信息
		@ViewInject(R.id.bg)
		ImageView 					bgImageView;
		@ViewInject(R.id.title)
		TextView 					titleTextView;
		@ViewInject(R.id.star)
		ImageView 					starImageView;
		@ViewInject(R.id.location)
		TextView 					locationTextView;
		@ViewInject(R.id.services)
		TextView 					servicesTextView;
		@ViewInject(R.id.faraway)
		TextView 					farawayTextView;
	}
}
