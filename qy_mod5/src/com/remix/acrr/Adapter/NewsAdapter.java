package com.remix.acrr.Adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

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
import com.remix.acrr.Activity.NewsDetailActivity;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.baidu.Mod_News;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;

public class NewsAdapter extends BaseAdapter {
	private List<Mod_News> news;
	private Context context;
	private boolean is_loadpic;
	public NewsAdapter(List<Mod_News> myDataList, Context context, boolean is_loadPIC) {
		news = myDataList;
		this.context = context;
		this.is_loadpic = is_loadPIC;
	}

	@Override
	public int getCount() {
		//return news.size();
		return CONST.newsCount;
	}

	@Override
	public Object getItem(int position) {
		if (news == null || position > news.size())
			return null;
		return news.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame2_oneitem, null);
			x.view().inject(holder, convertView);
			convertView.setTag(holder);
//		}
//		Log.e("DEBUG2", shops.size()+"||");
		Mod_News onenew = news.get(position);
		if(is_loadpic == true){
			try{
				x.image().bind(holder.news_bgImageView, onenew.getImageurls().get(0).getUrl(), MyAndUtils.getImageOption());
			}catch (Exception e) {
				holder.news_bgImageView.setVisibility(View.GONE);
			}
		}
		holder.news_location.setText(onenew.getSite());
		holder.news_title.setText(onenew.getTitle());
		holder.news_timeStamp.setText(MyUtils.getOldTimetoNow(Long.parseLong(onenew.getTs())));
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
			Log.e("DEBUG", "µã»÷Î»ÖÃ " + position);
			//Toast.makeText(context, "µã»÷"+position, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(context, NewsDetailActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("key", news.get(position));
			intent.putExtras(bundle);
			context.startActivity(intent);
		}
	}

	public class ViewHolder {
		@ViewInject(R.id.VRSItem)
		View 						layout;
		@ViewInject(R.id.bg)
		ImageView 					news_bgImageView;
		@ViewInject(R.id.news_location)
		TextView 					news_location;
		@ViewInject(R.id.news_title)
		TextView 					news_title;
		@ViewInject(R.id.view_timeStamp)
		TextView 					news_timeStamp;
	}
}
