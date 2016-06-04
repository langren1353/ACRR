package com.remix.acrr.Activity;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.remix.acrr.R;
import com.remix.acrr.ENTITY.Entity_AcceptOrder;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_Orders;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.dialog.NotLoginDialog;
import com.remix.acrr.dialog.OrderStatusChangeDialog;
import com.umeng.fb.util.Log;

public class OrderInfoActivity extends Activity {

	@ViewInject(R.id.OrderInfoTitle)
	TextView titleTextView;
	@ViewInject(R.id.OrderInfoTitleServices)
	TextView servicesTextView;
	@ViewInject(R.id.shopLocation)
	TextView shopLocationTextView;
	@ViewInject(R.id.OrderInfoTitlePrice)
	TextView OrderInfoTitlePriceTextView;
	@ViewInject(R.id.OrderInfoDescribe)
	TextView OrderInfoDescribeTextView;
	@ViewInject(R.id.OrderInfoPubTime)
	TextView orderInfoPubTimeTextView;
	@ViewInject(R.id.OrderInfoTitleTime)
	TextView OrderInfoTitleTimeTextView;
	@ViewInject(R.id.OrderInfoTelTextView)
	TextView shopTelTextView;
	@ViewInject(R.id.OrderInfoBackBtn)
	ImageView backImageView;
	@ViewInject(R.id.shopLocationForMe)
	TextView shopLocationForMeTextView;
	Button OrderInfoMainBtn;
	private Mod_Orders curOrder;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getBooleanExtra("ordered", false) == true) {
			setContentView(R.layout.need_me_order_info);
		} else {
			setContentView(R.layout.frame1_shop_info);
		}
		x.view().inject(this);
		if (getIntent().getBooleanExtra("ordered", false) == true) {
			OrderInfoMainBtn = (Button) findViewById(R.id.OrderInfoFinishBtn);
			OrderInfoMainBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					new OrderStatusChangeDialog().getDialog(
							OrderInfoActivity.this, handler).show();
				}
			});
		} else {
			OrderInfoMainBtn = (Button) findViewById(R.id.OrderInfoAcceptBtn);
			OrderInfoMainBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (CONST.userInfo == null) {
						new NotLoginDialog(OrderInfoActivity.this).getDialog()
								.show();
					} else {
						new Entity_AcceptOrder(OrderInfoActivity.this)
								.getResult(CONST.userInfo.getTel() + "",
										curOrder.getId() + "", handler);
					}
				}
			});
		}
		Intent intent = getIntent();
		shopLocationForMeTextView.setText(intent.getStringExtra("distance"));
		Bundle bundle = intent.getExtras();
		curOrder = (Mod_Orders) bundle.getSerializable(Mod_Orders.SHOPINFO);
		try {
			titleTextView.setText(curOrder.getName_sub());
			servicesTextView.setText(curOrder.getServices());
			shopLocationTextView.setText(curOrder.getAddr_text());
			shopTelTextView.setText(curOrder.getTel());
			OrderInfoTitlePriceTextView.setText(curOrder.getMoneyText());
			OrderInfoDescribeTextView.setText(curOrder.getDescribe()); // 设置详细信息
			orderInfoPubTimeTextView.setText(MyUtils.getDateStr_Full(curOrder
					.getPubtime()));
			OrderInfoTitleTimeTextView.setText(MyUtils.getDateStr_Full(curOrder
					.getExptime())); // 设置预期维修时间
			backImageView
					.setOnClickListener(new MyAndUtils.MyFinishClickListener(
							this));
			if (curOrder.getStatus() == 2)
				OrderInfoMainBtn.setEnabled(false);
		} catch (Exception e) {
			Log.e("DEBUG2", e.getMessage());
		}
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1000) {
					Toast.makeText(OrderInfoActivity.this, msg.obj.toString(),
							0).show(); // 接单成功|失败
					if (msg.obj.toString().equals("接单成功")) {
						Intent intent = new Intent();
						intent.putExtra("refresh", true);
						setResult(400, intent);
						OrderInfoMainBtn.setEnabled(false);
					}
				} else if (msg.what == 300) {
					finishOrder();
				}
				super.handleMessage(msg);
			}
		};
	}

	private void finishOrder() {
		String url = CONST.host + CONST.OrderFinishServlet + "?"
				+ CONST.WORKERID_STRING + "=" + CONST.userInfo.getTel() + "&"
				+ CONST.ORDERID_STRING + "=" + curOrder.getId() + "&"
				+ CONST.TOKEN_STRING + "=" + CONST.userInfo.getToken();
		RequestParams requestParams = new RequestParams(url);
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
				Log.e("DEBUG2", arg0);
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
						.create();
				ResponseObject responseObject = gson.fromJson(arg0,
						ResponseObject.class);
				Toast.makeText(OrderInfoActivity.this, responseObject.getMsg(),
						0).show();
				if (responseObject.getState() == 1) {
					OrderInfoMainBtn.setEnabled(false);
					Intent intent = new Intent();
					intent.putExtra("refresh", true);
					setResult(400, intent);
				}
			}
		});
	}

	@Event(value = { R.id.OrderInfoTelBtn, R.id.OrderInfoMapBtn,
			R.id.OrderInfoAcceptBtn })
	private void BtnClickListener(View v) {
		switch (v.getId()) {
		case R.id.OrderInfoTelBtn:
			Intent callintent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
					+ curOrder.getTel()));
			startActivity(callintent);
			break;
		case R.id.OrderInfoMapBtn:
			Intent intent = new Intent();
			intent.putExtra("destination", curOrder.getAddr_lat() + ","
					+ curOrder.getAddr_lon());
			intent.setClass(this, GDMapActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
