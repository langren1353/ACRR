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
	 * ���ڵ����⣺�˳�֮��λû�йر�-> client.StopLo..
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gdmap);
		x.view().inject(this);;
		mapView.onCreate(savedInstanceState);// �˷���������д
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
	 * ��ʼ��AMap����
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
		aMap.moveCamera(new CameraUpdateFactory().zoomTo(14));
		addMarkersToMap();// ����ͼ�����marker
	}

	private void addMarkersToMap() {
		aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
				.position(destinationLatLng).title("Ŀ�ĵ�").draggable(true));
		// ����Ч��
		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
		giflist.add(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_RED));

		drawMarkers();// ���10������ϵͳĬ��icon��marker
	}

	/**
	 * ����ϵͳĬ�ϵ�1��marker����ͼƬ
	 */
	public void drawMarkers() {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.position(destinationLatLng)
				.title("Ŀ��ά�޵�")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
				.draggable(true));
		marker.showInfoWindow();// ����Ĭ����ʾһ��infowinfow
		RouteFind();
	}

	public void RouteFind() {
		final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
				new LatLonPoint(CONST.mylatitude, CONST.mylontitude), new LatLonPoint(destinationLatLng.latitude, destinationLatLng.longitude));
		DriveRouteQuery query = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
				null, "");// ��һ��������ʾ·���滮�������յ㣬�ڶ���������ʾ�ݳ�ģʽ��������������ʾ;���㣬���ĸ�������ʾ�������򣬵����������ʾ���õ�·
		mRouteSearch.calculateDriveRouteAsyn(query);// �첽·���滮�ݳ�ģʽ��ѯ
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

	@Event(value = R.id.GDMapUseBaiduMap)
	private void BtnClickListener(View v) {
		try {
			Intent intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ CONST.mylatitude
							+ ","
							+ CONST.mylontitude
							+ "|name:�Ҽ�&destination=latlng:"
							+ destinationLatLng.latitude
							+ ", "
							+ destinationLatLng.longitude
							+ "|name:Ŀ�ĵ�&mode=driving&region=����&coord_type=gcj02&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				startActivity(intent); // ��������
				Toast.makeText(this, "���������ٶȵ�ͼ", 0).show();
			} else {
				Toast.makeText(this, "û�а�װ�ٶȵ�ͼ�ͻ���", 0).show();
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
		//aMap.clear();// �����ͼ�ϵ����и�����
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
					GDMapDriveBtn.setText("ȫ��" + MyUtils.getFriendlyLength(dis) + "��Ԥ��" + MyUtils.getFriendlyTime(dur));
					mRotueTimeDes.setText(des);
					mRouteDetailDes.setVisibility(View.VISIBLE);
					int taxiCost = (int) mDriveRouteResult.getTaxiCost();
					mRouteDetailDes.setText("��Լ" + taxiCost + "Ԫ");

				} else if (result != null && result.getPaths() == null) {
					Toast.makeText(this, "û�й滮�ɹ�", 0).show();
				}

			} else {
				Toast.makeText(this, "û�й滮�ɹ�", 0).show();
			}
		} else {
			Toast.makeText(this, "û�й滮�ɹ�", 0).show();
		}
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {
	}
}
