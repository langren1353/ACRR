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
	 * ���ڵ����⣺�˳�֮��λû�йر�-> client.StopLo..
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gdmap);
		x.view().inject(this);
		mapView = (MapView) findViewById(R.id.MapView);
		mapView.onCreate(savedInstanceState);// �˷���������д
		init();
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
		GDmap_back.setOnClickListener(new MyUtils.MyFinishClickListener(this));
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
		if(mapLocationChangedListener != null && arg0 != null){
			if(arg0 != null && arg0.getErrorCode() == 0){
				mapLocationChangedListener.onLocationChanged(arg0); // ��ʾϵͳС����
				Toast.makeText(this, "��λ�ɹ�", Toast.LENGTH_SHORT).show();
			}else{
				String errorText = "��λʧ��," + arg0.getErrorCode()+ ": " + arg0.getErrorInfo();
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
			mapLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy); //����Ϊ�߾���
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
