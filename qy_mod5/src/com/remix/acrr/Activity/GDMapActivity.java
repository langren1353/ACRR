package com.remix.acrr.Activity;

import java.io.File;
import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.remix.acrr.R;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;

public class GDMapActivity extends Activity implements AMapLocationListener, LocationSource, OnRouteSearchListener {
	@ViewInject(R.id.MapView)
	MapView mapView;
	@ViewInject(R.id.GDmap_back)
	ImageView GDmap_back;
	@ViewInject(R.id.GDMapDriveBtn)
	RadioButton GDMapDriveBtn;

	public AMap aMap;
	public AMapLocationClient mapLocationClient;
	public AMapLocationClientOption mapLocationClientOption;
	public OnLocationChangedListener mapLocationChangedListener;
	private RouteSearch mRouteSearch;
	private DriveRouteResult mDriveRouteResult;
	private TextView mRotueTimeDes, mRouteDetailDes;

	private LatLng destinationLatLng;

	/**
	 * 存在的问题：退出之后定位没有关闭-> client.StopLo..
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gdmap);
		x.view().inject(this);;
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		GDmap_back.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		try {
			Intent intent = getIntent();
			String desString = intent.getStringExtra("destination");
			String all[] = desString.split(",");
			destinationLatLng = new LatLng(Double.parseDouble(all[0]), Double.parseDouble(all[1]));
		} catch (Exception e) {
		}
		init();
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			mRouteSearch = new RouteSearch(this);
			mRouteSearch.setRouteSearchListener(this);
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
		aMap.moveCamera(new CameraUpdateFactory().zoomTo(14));
		addMarkersToMap();// 往地图上添加marker
	}

	private void addMarkersToMap() {
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(destinationLatLng).title("目的地").draggable(true));
		// 动画效果
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));

		drawMarkers();// 添加10个带有系统默认icon的marker
	}

	/**
	 * 绘制系统默认的1种marker背景图片
	 */
	public void drawMarkers() {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.position(destinationLatLng)
				.title("目标维修点")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.showInfoWindow();// 设置默认显示一个infowinfow
		RouteFind();
	}

	public void RouteFind() {
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				new LatLonPoint(CONST.mylatitude, CONST.mylontitude), new LatLonPoint(destinationLatLng.latitude, destinationLatLng.longitude));
		DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
				null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
		mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
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

	@Event(value = R.id.GDMapUseBaiduMap)
	private void BtnClickListener(View v) {
		try {
			Intent intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ CONST.mylatitude
							+ ","
							+ CONST.mylontitude
							+ "|name:我家&destination=latlng:"
							+ destinationLatLng.latitude
							+ ", "
							+ destinationLatLng.longitude
							+ "|name:目的地&mode=driving&region=西安&coord_type=gcj02&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				startActivity(intent); // 启动调用
				Toast.makeText(this, "正在启动百度地图", 0).show();
			} else {
				Toast.makeText(this, "没有安装百度地图客户端", 0).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isInstallByread(String packageName)
	{
		return new File("/data/data/" + packageName).exists();
	}

	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1) {
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
		//aMap.clear();// 清理地图上的所有覆盖物
		if (errorCode == 1000) {
			if (result != null && result.getPaths() != null) {
				if (result.getPaths().size() > 0) {
					mDriveRouteResult = result;
					final DrivePath drivePath = mDriveRouteResult.getPaths()
							.get(0);
					DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
							this, aMap, drivePath,
							mDriveRouteResult.getStartPos(),
							mDriveRouteResult.getTargetPos());
					drivingRouteOverlay.removeFromMap();
					drivingRouteOverlay.addToMap();
					drivingRouteOverlay.zoomToSpan();
					int dis = (int) drivePath.getDistance();
					int dur = (int) drivePath.getDuration();
					String des = MyUtils.getFriendlyTime(dur) + "(" + MyUtils.getFriendlyLength(dis) + ")";
					GDMapDriveBtn.setText("全程" + MyUtils.getFriendlyLength(dis) + "，预计" + MyUtils.getFriendlyTime(dur));
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.VISIBLE);
					int taxiCost = (int) mDriveRouteResult.getTaxiCost();
					mRouteDetailDes.setText("打车约" + taxiCost + "元");

				} else if (result != null && result.getPaths() == null) {
					Toast.makeText(this, "没有规划成功", 0).show();
				}

			} else {
				Toast.makeText(this, "没有规划成功", 0).show();
			}
		} else {
			Toast.makeText(this, "没有规划成功", 0).show();
		}
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {
	}
}
