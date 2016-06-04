package com.remix.acrr.Activity.UserSetting;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.remix.acrr.R;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;

public class UserSetting_AddressGDMapSelect extends Activity implements OnMapClickListener, LocationSource, AMapLocationListener{
	@ViewInject(R.id.UserSettingGDMapSelectBackBtn)
	ImageView 					userSettingGDMapSelectBackImageView;
	@ViewInject(R.id.UserSettingGDMapSelectSureBtn)
	ImageView 					userSettingGDMapSelectSureBtn;
	@ViewInject(R.id.UserSettingGDMapSelectMapView)
	MapView 					mapView;
	@ViewInject(R.id.UserSettingGDMapLocationStr)
	TextView					UserSettingGDMapLocationStr;
	
	private double lat = 0;
	private double lon = 0;
	private AMap aMap;
	private AMapLocationClient mapLocationClient;
	private AMapLocationClientOption mapLocationClientOption;
	private OnLocationChangedListener mapLocationChangedListener;
	private Marker marker;
	private Handler handler;
	private String locationStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_setting__address_gdmap_select);
		x.view().inject(this);
		mapView.onCreate(savedInstanceState);// �˷���������д
		userSettingGDMapSelectBackImageView.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		init();
	}


	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 100){
					marker.setSnippet(msg.obj.toString());
					locationStr = msg.obj.toString();
					UserSettingGDMapLocationStr.setText(locationStr);
				}
				super.handleMessage(msg);
			}
		};
		//��ʾ��ͼ����������¼�
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.setOnMapClickListener(this);// ��amap��ӵ�����ͼ�¼�������
			setUpMap();
		}
	}
	/**
	 * ����һЩamap������
	 */
	private void setUpMap() {
		// �Զ���ϵͳ��λС����
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// ����С�����ͼ��
		myLocationStyle.strokeColor(Color.BLACK);// ����Բ�εı߿���ɫ
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// ����Բ�ε������ɫ
		// myLocationStyle.anchor(int, int)//����С�����ê��
		myLocationStyle.strokeWidth(1.0f);// ����Բ�εı߿��ϸ
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// ���ö�λ����
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
		aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
		// aMap.setMyLocationType()
		aMap.moveCamera(new CameraUpdateFactory().zoomTo(18));
		
	}
	/**
	 * ����ϵͳĬ�ϵ�1��marker����ͼƬ
	 */
	public void drawMarkers(LatLng latLng) {
		marker = aMap.addMarker(new MarkerOptions()
				.position(latLng)
				.title("������ά��")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.showInfoWindow();// ����Ĭ����ʾһ��infowinfow
	}
	@Event(value = R.id.UserSettingGDMapSelectSureBtn)
	private void btnClickListener(View v) {
		if (v.getId() == R.id.UserSettingGDMapSelectSureBtn) {
			if (lat == 0 && lon == 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("55555").setMessage("��û��ѡ���ַ\r\n���ȷ����ʹ�õ�ǰ��ַ")
						.setIcon(R.drawable.ic_launcher);
				dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() { // ����ȷ����ť
							@Override
							public void onClick(DialogInterface dialog, int which) {
								lat = CONST.mylatitude;
								lon = CONST.mylontitude;
								locationStr = CONST.myLocationStr;
								Intent intent = new Intent();
								intent.putExtra("lat", lat);
								intent.putExtra("lon", lon);
								intent.putExtra("addr", locationStr);
								setResult(1000, intent);
								finishActivity(1000);
								dialog.dismiss();
							}
						});
				dialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() { // ����ȡ����ť
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
			}else{
				String curLocationStr = UserSettingGDMapLocationStr.getText().toString().trim();
				if(! curLocationStr.equals("")){
					locationStr = curLocationStr;
				}
				Intent intent = new Intent();
				intent.putExtra("lat", lat);
				intent.putExtra("lon", lon);
				intent.putExtra("addr", locationStr);
				setResult(1000, intent);
				finish();
			}
		}
	}

	@Override
	public void onMapClick(LatLng latlon) {
		findLocation(latlon);
		aMap.clear();
		//Toast.makeText(this, "����ˣ�"+latlon.latitude+","+latlon.longitude, 0).show();
		this.lat = latlon.latitude;
		this.lon = latlon.longitude;
		drawMarkers(latlon);// ���10������ϵͳĬ��icon��marker
	}
	private void findLocation(LatLng latLng){
		RequestParams requestParams = new RequestParams(CONST.GDTLocation.replace("LAT", latLng.latitude+"").replace("LON", latLng.longitude+"")); //ע��˳�����ͨ�Ƿ��ŵ�
		//requestParams.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}
			@Override
			public void onError(Throwable arg0, boolean arg1) {
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onSuccess(String arg0) {
				Log.v("OUT", arg0);
				String reg = "formatted_address\"\\s*:\\s*\"([^\"]+)";
				try {
					String locationString = MyUtils.reg_GetFirst(arg0, reg);
					Message message = new Message();
					message.what = 100;
					message.obj = locationString;
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onDestroy() {
		Log.e("DEBUG2", "onDesotry");
		mapLocationClient.stopLocation();
		mapView.onDestroy();
		super.onDestroy();
	}

	/**
	 * ��λ�ɹ�
	 */
	@Override
	public void onLocationChanged(AMapLocation arg0) {
		if (mapLocationChangedListener != null && arg0 != null) {
			if (arg0 != null && arg0.getErrorCode() == 0) {
				mapLocationChangedListener.onLocationChanged(arg0); // ��ʾϵͳС����
				// Toast.makeText(this, "��λ�ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				String errorText = "��λʧ��," + arg0.getErrorCode() + ": " + arg0.getErrorInfo();
				// Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
				Log.e("DEBUG2", errorText);
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		mapLocationChangedListener = arg0;
		if (mapLocationClient == null) {
			mapLocationClient = new AMapLocationClient(this);
			mapLocationClient.setLocationListener(this);
			mapLocationClientOption = new AMapLocationClientOption();
			mapLocationClientOption.setOnceLocation(true); // ��λ����Ϊ����
			mapLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy); // ����Ϊ�߾���
			mapLocationClient.setLocationOption(mapLocationClientOption);
			mapLocationClient.startLocation();
		}
	}

	@Override
	public void deactivate() {
		mapLocationChangedListener = null;
		if (mapLocationClient != null) {
			mapLocationClient.stopLocation();
			mapLocationClient.onDestroy();
		}
		mapLocationClient = null;
	}

}
