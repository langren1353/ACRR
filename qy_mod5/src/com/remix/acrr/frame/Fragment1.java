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
	// Fragment�൱�� View+��������
	// LayoutInfalter����xml�ļ�Ϊview
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
		// ����AMapLocationClient�����
		mLocationClient = null;
		// ������λ�ص�������
		mLocationListener = new AMapLocationListener() {
			@Override
			public void onLocationChanged(AMapLocation amapLocation) {
				String resultString = "";
				if (amapLocation != null) {
					if (amapLocation.getErrorCode() == 0) {
						// ��λ�ɹ��ص���Ϣ�����������Ϣ
						amapLocation.getLocationType();// ��ȡ��ǰ��λ�����Դ�������綨λ����������λ���ͱ�
						resultString += "�ҵ�λ�ã�\r\n\tLat:" + amapLocation.getLatitude();// ��ȡγ��
						CONST.mylatitude = amapLocation.getLatitude();
						resultString += "";
						resultString += " & Lon:" + amapLocation.getLongitude();// ��ȡ����
						CONST.mylontitude = amapLocation.getLongitude();
						amapLocation.getAccuracy();// ��ȡ������Ϣ
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = new Date(amapLocation.getTime());
						//resultString += df.format(date);// ��λʱ��
						//resultString += "---------";
						resultString += "\r\n\t" + amapLocation.getAddress();// ��ַ�����option������isNeedAddressΪfalse����û�д˽�������綨λ����л��е�ַ��Ϣ��GPS��λ�����ص�ַ��Ϣ��
						CONST.myLocationStr = amapLocation.getAddress();
						amapLocation.getCountry();// ������Ϣ
						amapLocation.getProvince();// ʡ��Ϣ
						amapLocation.getCity();// ������Ϣ
						amapLocation.getDistrict();// ������Ϣ
						amapLocation.getStreet();// �ֵ���Ϣ
						amapLocation.getStreetNum();// �ֵ����ƺ���Ϣ
						amapLocation.getCityCode();// ���б���
						amapLocation.getAdCode();// ��������
						amapLocation.getAoiName();// ��ȡ��ǰ��λ���AOI��Ϣ
					} else {
						// ��ʾ������ϢErrCode�Ǵ����룬errInfo�Ǵ�����Ϣ������������
						Log.e("AmapError", "location Error, ErrCode:"
								+ amapLocation.getErrorCode() + ", errInfo:"
								+ amapLocation.getErrorInfo());
					}
				}
				if(resultString!= null && !resultString.equals(""))
				new AlertDialog.Builder(getActivity()).setMessage(resultString).show();
			}
		};
		// ��ʼ����λ
		mLocationClient = new AMapLocationClient(getContext());
		// ���ö�λ�ص�����
		mLocationClient.setLocationListener(mLocationListener);

		// ��ʼ����λ����
		mLocationOption = new AMapLocationClientOption();
		// ���ö�λģʽΪ�߾���ģʽ��Battery_SavingΪ�͹���ģʽ��Device_Sensors�ǽ��豸ģʽ
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		// �����Ƿ񷵻ص�ַ��Ϣ��Ĭ�Ϸ��ص�ַ��Ϣ��
		mLocationOption.setNeedAddress(true);
		// �����Ƿ�ֻ��λһ��,Ĭ��Ϊfalse
		mLocationOption.setOnceLocation(true);
		// �����Ƿ�ǿ��ˢ��WIFI��Ĭ��Ϊǿ��ˢ��
		mLocationOption.setWifiActiveScan(true);
		// �����Ƿ�����ģ��λ��,Ĭ��Ϊfalse��������ģ��λ��
		mLocationOption.setMockEnable(true);
		// ���ö�λ���,��λ����,Ĭ��Ϊ2000ms
		mLocationOption.setInterval(2000);
		// ����λ�ͻ��˶������ö�λ����
		mLocationClient.setLocationOption(mLocationOption);
		// ������λ
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
		if(is_load == true){ // ��������
			CONST.page=1;
		}else{ // ��ȡ��������
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
				// Toast.makeText(getA, "�����������", Toast.LENGTH_SHORT).show();
				Log.e("DEBUG2", "�������");
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
					Log.e("DEBUG2", "��������");
					myDataList = list;
					shopsAdapter = new ShopsAdapter(myDataList, getActivity(), is_loadPIC);
					listView.setAdapter(shopsAdapter);
					shopsAdapter.notifyDataSetChanged();
					listView.setMode(Mode.BOTH);
				} else {
					Log.e("DEBUG2", "��������");
					myDataList.addAll(list);
					shopsAdapter.notifyDataSetChanged();
				}
				listView.onRefreshComplete();
			}
		});
	}

	// Ӧ�Ե�������
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
				//֪ͨ����ˢ��
				doRefresh(true);
			}
		}
	}
}
