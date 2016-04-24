package com.xxxxx.ac.frame;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xxxxx.ac.R;
import com.xxxxx.ac.Adapter.ShopsAdapter;
import com.xxxxx.ac.ENTITY.RespData.ResponseObject;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_Shops;
import com.xxxxx.ac.Tools.SharedUtil_SharedPrefs;

public class Fragment1 extends Fragment {
	// Fragment相当于 View+生命周期
	// LayoutInfalter载入xml文件为view
	@ViewInject(R.id.goodslistview)
	PullToRefreshListView listView;
	
	public ShopsAdapter shopsAdapter;
    public List<Mod_Shops> myDataList;
    SharedUtil_SharedPrefs sharedPrefs;
    public String url = CONST.host + CONST.getGoosServlet;
    public boolean is_loadPIC = true;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame1, null);
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
	
	
	@Override
	public void onDestroy() {
//		Log.e("DEBUG2", "ONDestory");
		super.onDestroy();
	}


	@Override
	public void onPause() {
//		Log.e("DEBUG2", "onPause");
		super.onPause();
	}


	@Override
	public void onResume() {
//		Log.e("DEBUG2", "onResume");
		super.onResume();
	}


	@Override
	public void onStart() {
//		Log.e("DEBUG2", "onStart");
		super.onStart();
	}

	public void doRefresh(final boolean is_load){
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		
		Log.e("DEBUG2", url);
		RequestParams requestParams = new RequestParams(url);
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}
			@Override
			public void onError(Throwable arg0, boolean arg1) {
				Toast.makeText(getActivity(), "网络接连错误", Toast.LENGTH_SHORT).show();
				Log.e("DEBUG2", "网络错误？");
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onSuccess(String arg0) {
				
				Gson gson = new Gson();
				ResponseObject<ArrayList<Mod_Shops>> responseObeject = gson.fromJson(arg0, new TypeToken<ResponseObject<ArrayList<Mod_Shops>>>(){}.getType());
//				ResponseListObeject responseObeject = gson.fromJson(arg0, ResponseListObeject.class);
				@SuppressWarnings("unchecked")
				ArrayList<Mod_Shops> list = (ArrayList<Mod_Shops>) responseObeject.getObject();
				if(is_load == true){
					Log.e("DEBUG2", "更新数据");
					myDataList = list;
					shopsAdapter = new ShopsAdapter(myDataList, getContext(), is_loadPIC);
					listView.setAdapter(shopsAdapter);
					shopsAdapter.notifyDataSetChanged();
					listView.setMode(Mode.BOTH);
				}else{
					Log.e("DEBUG2", "增加数据");
					myDataList.addAll(list);
					shopsAdapter.notifyDataSetChanged();
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
