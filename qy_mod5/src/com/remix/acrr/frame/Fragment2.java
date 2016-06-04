package com.remix.acrr.frame;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.remix.acrr.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.remix.acrr.Adapter.NewsAdapter;
import com.remix.acrr.ENTITY.Entity_News;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.baidu.Mod_News;
import com.remix.acrr.Tools.SharedUtil_SharedPrefs;

public class Fragment2 extends Fragment {
	@ViewInject(R.id.VRshowlistview)
	PullToRefreshListView listView;
	
	SharedUtil_SharedPrefs sharedPrefs;
	public NewsAdapter newsAdapter;
    public List<Mod_News> myDataList;
    public boolean is_loadPIC = true;
    public String url = CONST.baiduNews;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame2, null);
		x.view().inject(this, view);
		sharedPrefs = new SharedUtil_SharedPrefs(getContext(), CONST.totalSetting);
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		listView.setMode(Mode.PULL_FROM_START);
		listView.setScrollingWhileRefreshingEnabled(true);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				doRefresh(refreshView.getScrollY() < 0);
			}
		});
		new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				doRefresh(true);
				return true;
			}
		}).sendEmptyMessageDelayed(0, 200);
		return view; 
	}
	public void doRefresh(final boolean is_load){
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		Log.e("DEBUG2", url);
		RequestParams requestParams = new RequestParams(url);
		String allPa[] = CONST.baiduNews_data.split("&");
		for(String i:allPa){
			String one[] = i.split("=");
			System.out.println(one[0]+"="+one[1]);
			requestParams.addBodyParameter(one[0], one[1]);
		}
		x.http().post(requestParams, new Callback.CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				if (arg0 instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) arg0;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					// ...
					Log.e("DEBUG", "错误代码:" + httpEx.getCode());
				}
				Toast.makeText(getActivity(), "网络接连错误", Toast.LENGTH_SHORT).show();
				String errorString = "网络错误？";
				if(arg0 != null)
					errorString += arg0.getMessage();
				Log.e("DEBUG2", errorString);
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				System.out.println(arg0);
				Entity_News entity_News = new Entity_News(arg0);
//				Gson gson = new Gson();
//				ResponseObeject_BaiduNews responseObeject = gson.fromJson(arg0, ResponseObeject_BaiduNews.class);
//				ArrayList<Mod_News> list = responseObeject.getData().getSentiment();
				ArrayList<Mod_News> list = entity_News.getMod_news();
//				Log.e("DEBUG2", list.get(0).getContent().get(0).getData());
				if(is_load == true){
					Log.e("DEBUG2", "更新数据Fragment2");
					myDataList = list;
					newsAdapter = new NewsAdapter(myDataList, getContext(), is_loadPIC);
					listView.setAdapter(newsAdapter);
					newsAdapter.notifyDataSetChanged();
					listView.setMode(Mode.BOTH);
				}else{
					Log.e("DEBUG2", "增加数据Fragment2");
					myDataList.addAll(list);
					newsAdapter.notifyDataSetChanged();
				}
				listView.onRefreshComplete();
			
			}
		});
	}
	// 应对叠层现象
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
}
