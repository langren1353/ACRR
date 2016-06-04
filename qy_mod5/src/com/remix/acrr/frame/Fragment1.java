package com.remix.acrr.frame;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
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
import com.remix.acrr.MOD.Mod_UserInfo;
import com.remix.acrr.MOD.UserStore;
import com.remix.acrr.Tools.SharedUtil_SharedPrefs;

public class Fragment1 extends Fragment {
	// Fragment相当于 View+生命周期
	// LayoutInfalter载入xml文件为view
	@ViewInject(R.id.goodslistview)
	PullToRefreshListView listView;

	public ShopsAdapter shopsAdapter;
	public List<Mod_Orders> myDataList;
	SharedUtil_SharedPrefs sharedPrefs;
	public boolean is_loadPIC = true;

	public AMapLocationClient mLocationClient = null;
	public AMapLocationListener mLocationListener = null;
	public AMapLocationClientOption mLocationOption = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		initMap();
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame1, null);
		x.view().inject(this, view);
		sharedPrefs = new SharedUtil_SharedPrefs(getContext(), CONST.totalSetting);
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		listView.setMode(Mode.PULL_FROM_START);
		listView.setScrollingWhileRefreshingEnabled(true);
		findHasLogin();
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

	public boolean findHasLogin() {
		DbManager dbm = x.getDb(CONST.daoConfig);
		try {
			UserStore userStore = dbm.selector(UserStore.class).findFirst();
			CONST.userInfo = new Mod_UserInfo();
			CONST.userInfo.setTel(userStore.getUserId()+"");
			CONST.userInfo.setDescribe(userStore.getDescribe());
			CONST.userInfo.setPic(userStore.getPic());
			CONST.userInfo.setName(userStore.getName());
			CONST.userInfo.setToken(userStore.getToken());
			dbm.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			dbm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void initMap() {
		// 声明AMapLocationClient类对象
		mLocationClient = null;
		// 声明定位回调监听器
		mLocationListener = new AMapLocationListener() {
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				String resultString = "";
				if (amapLocation != null) {
					if (amapLocation.getErrorCode() == 0) {
						// 定位成功回调信息，设置相关消息
						amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
						resultString += "我的位置：\r\n\tLat:" + amapLocation.getLatitude();// 获取纬度
						CONST.mylatitude = amapLocation.getLatitude();
						resultString += "";
						resultString += " & Lon:" + amapLocation.getLongitude();// 获取经度
						CONST.mylontitude = amapLocation.getLongitude();
						amapLocation.getAccuracy();// 获取精度信息
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = new Date(amapLocation.getTime());
						//resultString += df.format(date);// 定位时间
						//resultString += "---------";
						resultString += "\r\n\t" + amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
						CONST.myLocationStr = amapLocation.getAddress();
						amapLocation.getCountry();// 国家信息
						amapLocation.getProvince();// 省信息
						amapLocation.getCity();// 城市信息
						amapLocation.getDistrict();// 城区信息
						amapLocation.getStreet();// 街道信息
						amapLocation.getStreetNum();// 街道门牌号信息
						amapLocation.getCityCode();// 城市编码
						amapLocation.getAdCode();// 地区编码
						amapLocation.getAoiName();// 获取当前定位点的AOI信息
					} else {
						// 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
						Log.e("AmapError", "location Error, ErrCode:"
								+ amapLocation.getErrorCode() + ", errInfo:"
								+ amapLocation.getErrorInfo());
					}
				}
				if(resultString!= null && !resultString.equals(""))
				new AlertDialog.Builder(getActivity()).setMessage(resultString).show();
			}
		};
		// 初始化定位
		mLocationClient = new AMapLocationClient(getContext());
		// 设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);

		// 初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// 设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		// 设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(true);
		// 设置是否强制刷新WIFI，默认为强制刷新
		mLocationOption.setWifiActiveScan(true);
		// 设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(true);
		// 设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		// 给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocationClient.startLocation();
	}

	@Override
	public void onDestroy() {
		// Log.e("DEBUG2", "ONDestory");
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// Log.e("DEBUG2", "onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		// Log.e("DEBUG2", "onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		// Log.e("DEBUG2", "onStart");
		super.onStart();
	}

	public void doRefresh(final boolean is_load) {
		if(is_load == true){ // 更新数据
			CONST.page=1;
		}else{ // 获取更多数据
			CONST.page++;
		}
		is_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_loadPIC);
		String url = CONST.host + CONST.getGoosServlet + "?" + CONST.PAGEINDEX_STRING + "=" + CONST.page;
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
				listView.onRefreshComplete();
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
				String pageString = responseObeject.getMsg();
				CONST.page = Integer.parseInt(pageString);
				if (is_load == true) {
					Log.e("DEBUG2", "更新数据");
					myDataList = list;
					shopsAdapter = new ShopsAdapter(myDataList, getActivity(), is_loadPIC);
					listView.setAdapter(shopsAdapter);
					shopsAdapter.notifyDataSetChanged();
					listView.setMode(Mode.BOTH);
				} else {
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if(resultCode == 101){
			boolean isMakeOrderSuccess = intent.getBooleanExtra("hassuccessed", false);
			if(isMakeOrderSuccess == true){
				//通知下拉刷新
				doRefresh(true);
			}
		}
	}
}
