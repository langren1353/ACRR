package com.remix.acrr.Activity.UserOrders;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.remix.acrr.R;
import com.remix.acrr.Adapter.ShopsAdapter;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_Orders;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.SharedUtil_SharedPrefs;

public class UserOrdersNeedMe extends Activity {

	@ViewInject(R.id.UserOrdersNotCommentBack)
	ImageView							UserOrdersNotCommentBackBtn;
	
	@ViewInject(R.id.NeedMeListView)
	PullToRefreshListView							NeedMeListView;
	public boolean is_loadPIC = true;
	SharedUtil_SharedPrefs sharedPrefs;
	public List<Mod_Orders> myDataList;
	public ShopsAdapter shopsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orders_need_me);
		x.view().inject(this);
		UserOrdersNotCommentBackBtn.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		init();
		
	}
	public void init(){
		NeedMeListView.setMode(Mode.PULL_FROM_START);
		NeedMeListView.setScrollingWhileRefreshingEnabled(true);
		NeedMeListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				doRefresh(refreshView.getScrollY() < 0);
			}
		});
		sharedPrefs = new SharedUtil_SharedPrefs(this, CONST.totalSetting);
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		doRefresh(true);
	}
	private void doRefresh(boolean upORdown){
		if(upORdown == true){ // 更新数据
			initData();
		}else{ // 获取更多数据
		}
	}
	private void initData(){
		String url = CONST.host + CONST.getNeedMeOrders + "?" + CONST.MYTELID_STRING + "=" + CONST.userInfo.getTel();
		Log.e("DEBUG2", url);
		RequestParams requestParams = new RequestParams(url);
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
				// Toast.makeText(getA, "网络接连错误", Toast.LENGTH_SHORT).show();
				Log.e("DEBUG2", "网络错误？");
				//listView.onRefreshComplete();
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				ResponseObject<ArrayList<Mod_Orders>> responseObeject = gson.fromJson(arg0, new TypeToken<ResponseObject<ArrayList<Mod_Orders>>>() {
				}.getType());
				// ResponseListObeject responseObeject = gson.fromJson(arg0, ResponseListObeject.class);
				@SuppressWarnings("unchecked")
				ArrayList<Mod_Orders> list = (ArrayList<Mod_Orders>) responseObeject.getObject();
				if(list.size() == 0) Toast.makeText(UserOrdersNeedMe.this, "没有相关数据", 0).show();
				myDataList = list;
				shopsAdapter = new ShopsAdapter(myDataList, UserOrdersNeedMe.this, is_loadPIC, true);//已经处理中的单子
				NeedMeListView.setAdapter(shopsAdapter);
				shopsAdapter.notifyDataSetChanged();
				NeedMeListView.onRefreshComplete();
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(resultCode == 400){
			boolean refresh = intent.getBooleanExtra("refresh", false);
			doRefresh(refresh);
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
}
