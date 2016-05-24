package com.remix.acrr.Activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.remix.acrr.R;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.remix.acrr.Tools.MyUtils;

public class GDMapActivity extends Activity implements AMapLocationListener, LocationSource{
	@ViewInject(R.id.MapView)
	MapView 		mapView;
	@ViewInject(R.id.GDmap_back)
	ImageView		GDmap_back;
	
	public AMap aMap;
	public AMapLocationClient mapLocationClient;
	public AMapLocationClientOption mapLocationClientOption;
	public OnLocationChangedListener mapLocationChangedListener;
	
	/**
	 * 存在的问题：退出之后定位没有关闭-> client.StopLo..
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gdmap);
		x.view().inject(this);
		mapView = (MapView) findViewById(R.id.MapView);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		GDmap_back.setOnClickListener(new MyUtils.MyFinishClickListener(this));
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
		aMap.moveCamera(new CameraUpdateFactory().zoomTo(14));
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
		if(mapLocationChangedListener != null && arg0 != null){
			if(arg0 != null && arg0.getErrorCode() == 0){
				mapLocationChangedListener.onLocationChanged(arg0); // 显示系统小蓝点
				Toast.makeText(this, "定位成功", Toast.LENGTH_SHORT).show();
			}else{
				String errorText = "定位失败," + arg0.getErrorCode()+ ": " + arg0.getErrorInfo();
				Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show();
				Log.e("DEBUG2", errorText);
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		mapLocationChangedListener = arg0;
		if(mapLocationClient == null){
			mapLocationClient = new AMapLocationClient(this);
			mapLocationClient.setLocationListener(this);
			mapLocationClientOption = new AMapLocationClientOption();
			mapLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy); //设置为高精度
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
