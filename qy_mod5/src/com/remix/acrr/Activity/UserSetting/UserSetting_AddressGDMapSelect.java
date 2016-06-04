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
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		userSettingGDMapSelectBackImageView.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		init();
	}


	/**
	 * 初始化AMap对象
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
		//显示地图，监听点击事件
		if (aMap == null) {
			aMap = mapView.getMap();
			aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
			setUpMap();
		}
	}
	/**
	 * 设置一些amap的属性
	 */
	private void setUpMap() {
		// 自定义系统定位小蓝点
		MyLocationStyle myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));// 设置小蓝点的图标
		myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
		myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
		// myLocationStyle.anchor(int, int)//设置小蓝点的锚点
		myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
		aMap.setMyLocationStyle(myLocationStyle);
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// aMap.setMyLocationType()
		aMap.moveCamera(new CameraUpdateFactory().zoomTo(18));
		
	}
	/**
	 * 绘制系统默认的1种marker背景图片
	 */
	public void drawMarkers(LatLng latLng) {
		marker = aMap.addMarker(new MarkerOptions()
				.position(latLng)
				.title("在这里维修")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.showInfoWindow();// 设置默认显示一个infowinfow
	}
	@Event(value = R.id.UserSettingGDMapSelectSureBtn)
	private void btnClickListener(View v) {
		if (v.getId() == R.id.UserSettingGDMapSelectSureBtn) {
			if (lat == 0 && lon == 0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("55555").setMessage("您没有选择地址\r\n点击确定将使用当前地址")
						.setIcon(R.drawable.ic_launcher);
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() { // 设置确定按钮
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
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() { // 设置取消按钮
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
		//Toast.makeText(this, "点击了："+latlon.latitude+","+latlon.longitude, 0).show();
		this.lat = latlon.latitude;
		this.lon = latlon.longitude;
		drawMarkers(latlon);// 添加10个带有系统默认icon的marker
	}
	private void findLocation(LatLng latLng){
		RequestParams requestParams = new RequestParams(CONST.GDTLocation.replace("LAT", latLng.latitude+"").replace("LON", latLng.longitude+"")); //注意顺序和普通是反着的
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
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		Log.e("DEBUG2", "onDesotry");
		mapLocationClient.stopLocation();
		mapView.onDestroy();
		super.onDestroy();
	}

	/**
	 * 定位成功
	 */
	@Override
	public void onLocationChanged(AMapLocation arg0) {
		if (mapLocationChangedListener != null && arg0 != null) {
			if (arg0 != null && arg0.getErrorCode() == 0) {
				mapLocationChangedListener.onLocationChanged(arg0); // 显示系统小蓝点
				// Toast.makeText(this, "定位成功", Toast.LENGTH_SHORT).show();
			} else {
				String errorText = "定位失败," + arg0.getErrorCode() + ": " + arg0.getErrorInfo();
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
			mapLocationClientOption.setOnceLocation(true); // 定位次数为单次
			mapLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy); // 设置为高精度
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
