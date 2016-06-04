package com.remix.acrr.Adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.remix.acrr.R;
import com.remix.acrr.Activity.OrderInfoActivity;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_Orders;
import com.remix.acrr.Tools.MyUtils;

public class ShopsAdapter extends BaseAdapter {
	private List<Mod_Orders> shops;
	private final Activity context;
	private boolean is_loadpic;
	private boolean hasFinished = false; // 标识是否已经接收

	public ShopsAdapter(List<Mod_Orders> myDataList, Activity context, boolean is_loadPIC, boolean hasFinished) {
		shops = myDataList;
		this.context = context;
		this.is_loadpic = is_loadPIC;
		this.hasFinished = hasFinished;
	}

	public ShopsAdapter(List<Mod_Orders> myDataList, Activity context, boolean is_loadPIC) {
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
		// if(convertView == null){
		holder = new ViewHolder();
		convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame1_oneitem, null);
		x.view().inject(holder, convertView);
		convertView.setTag(holder);
		// }
		// Log.e("DEBUG2", shops.size()+"||");
		Mod_Orders shop = shops.get(position);
		if (shop.getPic_main() != null && !shop.getPic_main().equals("")) {
			Log.e("DEBUG2", shop.getPic_main() + "||");
			if (is_loadpic == true)
				x.image().bind(holder.bgImageView, shop.getPic_main());
		} else {
			holder.bgImageView.setImageResource(R.drawable.fram1_oneitem_pic);
		}
		if (shop.getIs_Rapid() == false) {
			holder.isRapidImageView.setVisibility(View.INVISIBLE);
		}
		holder.titleTextView.setText(shop.getName_sub());
		holder.MoneytextTextView.setText(shop.getMoneyText());
		holder.locationTextView.setText(shop.getAddr_text());
		holder.servicesTextView.setText(shop.getServices());
		holder.pubTimeTextView.setText(MyUtils.getOldTimetoNow(shop.getPubtime().getTime()));
		holder.farawayTextView.setText(MyUtils.getDisText(MyUtils.GetDistance(shop.getAddr_lat(), shop.getAddr_lon(), CONST.mylatitude, CONST.mylontitude)));
			if (shop.getStatus() == 2) {
				holder.HasFinishedImg.setVisibility(View.VISIBLE);
				holder.HasFinishedImg.setBackgroundResource(R.drawable.has_finished);
			} else if (shop.getStatus() == 1) {
					holder.HasFinishedImg.setVisibility(View.VISIBLE);
					holder.HasFinishedImg.setBackgroundResource(R.drawable.inprogress);
			}else if(shop.getStatus() == 0){
				holder.HasFinishedImg.setVisibility(View.VISIBLE);
				holder.HasFinishedImg.setBackgroundResource(R.drawable.needhandle);
			}
		// holder.farawayTextView 特殊，暂时不设置
		holder.layout.setOnClickListener(new myCLickListener(position));
		return convertView;
	}

	private class myCLickListener implements OnClickListener {
		int position;

		public myCLickListener(int position) {
			Log.e("DEBUG", position + " +++ ");
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Log.e("DEBUG", "点击位置 " + position);
			Intent intent = new Intent();
			intent.setClass(context, OrderInfoActivity.class);
			intent.putExtra("ordered", hasFinished);
			intent.putExtra("distance", MyUtils.getDisText(MyUtils.GetDistance(shops.get(position).getAddr_lat(), shops.get(position).getAddr_lon(),
					CONST.mylatitude, CONST.mylontitude)));
			Bundle bundle = new Bundle();
			bundle.putSerializable(Mod_Orders.SHOPINFO, shops.get(position));
			intent.putExtras(bundle);
			context.startActivityForResult(intent, 400);
		}
	}

	public class ViewHolder {
		@ViewInject(R.id.gooditem)
		View layout;
		// 搞定对应的view信息

		@ViewInject(R.id.frameOneBg)
		ImageView bgImageView;
		@ViewInject(R.id.frameOneTitle)
		TextView titleTextView;
		@ViewInject(R.id.frameOneLocation)
		TextView locationTextView;
		@ViewInject(R.id.frameOneServices)
		TextView servicesTextView;
		@ViewInject(R.id.frameOnePubTime)
		TextView pubTimeTextView;
		@ViewInject(R.id.frameOneFaraway)
		TextView farawayTextView;
		@ViewInject(R.id.frameOneMoneytext)
		TextView MoneytextTextView;
		@ViewInject(R.id.frameOneIsRapid)
		ImageView isRapidImageView;

		@ViewInject(R.id.frameOneHasFinishedImg)
		ImageView HasFinishedImg;
	}
}
