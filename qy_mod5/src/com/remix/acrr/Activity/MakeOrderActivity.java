package com.remix.acrr.Activity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.remix.acrr.R;
import com.remix.acrr.Activity.UserSetting.UserSetting_AddressGDMapSelect;
import com.remix.acrr.ENTITY.Entity_MakeOrder;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_Orders;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.View.BottomPopView;
import com.remix.acrr.dialog.MyDatePickerDialog;
import com.remix.acrr.dialog.MyTimePickerDialog;
import com.remix.acrr.dialog.NotLoginDialog;

public class MakeOrderActivity extends Activity {
	@ViewInject(R.id.makeOrder_backBtn)
	ImageView						makeOrderBackBtnImageView;
	
	
	@ViewInject(R.id.MakeOrder_Mod_NameSub)
	EditText						MakeOrder_Mod_NameSub;
	@ViewInject(R.id.MakeOrder_Mod_ExpTime)
	TextView						MakeOrder_Mod_ExpTime;
	@ViewInject(R.id.MakeOrder_Mod_Addr)
	TextView						MakeOrder_Mod_Addr;
	@ViewInject(R.id.MakeOrder_Mod_IsRapid)
	TextView						MakeOrder_Mod_IsRapid;
	@ViewInject(R.id.MakeOrder_Mod_Tel)
	EditText						MakeOrder_Mod_Tel;
	@ViewInject(R.id.MakeOrder_Mod_Money)
	EditText						MakeOrder_Mod_Money;
	@ViewInject(R.id.MakeOrder_Mod_Describe)
	EditText						MakeOrder_Mod_Describe;
	@ViewInject(R.id.MakeOrderSubmitBtn)
	Button							MakeOrderSubmitBtn;
	
	private String timeStr;
	private int    timeStrFlag = -1; // ���ڴ���TImePickerDialog���������ε����
	private Mod_Orders mod_Order;
	private Handler handler;
	private double latitude;
	private double lontitude;
	private boolean hasSubmit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_make_order);
		x.view().inject(this);
		mod_Order = new Mod_Orders();
		makeOrderBackBtnImageView.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		initHandler();
		//Ĭ��ֵ
		Intent intent = getIntent();
		intent.putExtra("isRapid", false);
		onActivityResult(102, 102, intent);
	}
	public void initHandler(){
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == 1000){
					new AlertDialog.Builder(MakeOrderActivity.this).setIcon(R.drawable.ic_launcher).setTitle("�ɹ�").setMessage(msg.obj.toString()+"\r\n"+"ˢ��ά�޵��б��ɿ���").show();
					hasSubmit = true;
					MakeOrderSubmitBtn.setEnabled(false);
					Intent intent = new Intent();
					intent.putExtra("hassuccessed", true);
					setResult(101, intent);
				}
				super.handleMessage(msg);
			}
		};
	}
	@Event(value={R.id.MakeOrderTimeSet, R.id.MakeOrderAddrSet, R.id.MakeOrderSubmitBtn, R.id.MakeOrderIsRapid})
	private void BtnClickListener(View v){
		switch (v.getId()) {
		case R.id.MakeOrderTimeSet:
			changeTimeA(this);
			break;
		case R.id.MakeOrderAddrSet:
			changeAddr(this);
			break;
		case R.id.MakeOrderSubmitBtn:
			//Toast.makeText(this, "�ύ���ݵ�������", 0).show();
			if(CONST.userInfo == null){
				new NotLoginDialog(this).getDialog().show();
				return;
			}
			if(hasSubmit == true){
				Toast.makeText(this, "�Ѿ��ύ�ˣ������ظ��ύ", 0).show();
			}
			/**
			 * NameSub
			 * id_user
			 * moneytext
			 * tel
			 * is_rapid
			 * exptime
			 * pubtime��ǰʱ��
			 * addr_lat
			 * addr_lon
			 * addr_text
			 * services
			 * describe
			 * */
			mod_Order.setName_sub(MakeOrder_Mod_NameSub.getText().toString());
			mod_Order.setId_User(CONST.userInfo.getTel()+"");
			mod_Order.setMoneyText(MakeOrder_Mod_Money.getText().toString());
			mod_Order.setTel(MakeOrder_Mod_Tel.getText().toString());
			//mod_Order.setIs_Rapid(MakeOrder_Mod_IsRapid.); �����ط�
			try {
				mod_Order.setExptime(Timestamp.valueOf(MakeOrder_Mod_ExpTime.getText().toString()+":00"));
			} catch (Exception e) {
			}
			mod_Order.setPubtime(new Timestamp(System.currentTimeMillis()));
			mod_Order.setAddr_lat(this.latitude+"");
			mod_Order.setAddr_lon(this.lontitude+"");
			mod_Order.setAddr_text(MakeOrder_Mod_Addr.getText().toString());
			mod_Order.setServices("����");
			mod_Order.setDescribe(MakeOrder_Mod_Describe.getText().toString());
			if(is_NULL(mod_Order.getName_sub()) == true){
				// �ύ֮ǰ�ж϶��������Ƿ�����
				MakeOrder_Mod_NameSub.setError(Html.fromHtml("<font color=red>����д������������!</font>"));
				return;
			}
			if(is_NULL(MakeOrder_Mod_ExpTime.toString()) == true){
				MakeOrder_Mod_ExpTime.setError(Html.fromHtml("<font color=red>��ѡ�����Ԥ��ά��ʱ��!</font>"));
				return;
			}
			if(is_NULL(mod_Order.getTel()) == true){
				MakeOrder_Mod_Tel.setError(Html.fromHtml("<font color=red>����д��ϵ�绰!</font>"));
				return;
			}
			new Entity_MakeOrder(this).SubmitOrder(mod_Order, handler);
			break;
		case R.id.MakeOrderIsRapid:
			changeIsRapid(this);
			break;
		default:
			break;
		}
	}
	public boolean is_NULL(String text){
		if(text == null || text.trim().equals("")) return true;
		return false;
	}
	public void changeAddr(final Activity activity){
		Intent intent = new Intent();
		intent.setClass(activity, UserSetting_AddressGDMapSelect.class);
		startActivityForResult(intent, 200);
	}
	public void changeTimeA(final Activity activity) {
		Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
		DatePickerDialog dateDlg = new MyDatePickerDialog(activity,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) { //�·���Ҫ+1������
						String bithString = year + "-" + MyUtils.getTimeSize(monthOfYear+1) + "-" + MyUtils.getTimeSize(dayOfMonth);
						Intent intent = new Intent();
						intent.putExtra("timeA", bithString);
						onActivityResult(100, 100, intent);
						//Toast.makeText(activity, bithString, 1).show();
					}
				},
				dateAndTime.get(Calendar.YEAR),
				dateAndTime.get(Calendar.MONTH),
				dateAndTime.get(Calendar.DAY_OF_MONTH));
		dateDlg.show();
	}
	public void changeTimeB(final Activity activity) {
		Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
		TimePickerDialog timeDlg = new MyTimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String bithString = MyUtils.getTimeSize(hourOfDay) + ":" + MyUtils.getTimeSize(minute);
				Intent intent = new Intent();
				intent.putExtra("timeB", bithString);
				onActivityResult(101, 101, intent);
				//Toast.makeText(activity, bithString, 1).show();
			}
		}, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true);
		timeDlg.show();
	}
	public void changeIsRapid(final Activity activity) {
		BottomPopView bottomPopView = new BottomPopView(this, MakeOrder_Mod_Money) {
			@Override
			public void onTopButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("isRapid", true);
				onActivityResult(102, 102, intent);
				dismiss();
			}

			@Override
			public void onBottomButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("isRapid", false);
				onActivityResult(102, 102, intent);
				dismiss();
			}
		};
		bottomPopView.setTopText("��������");
		bottomPopView.setBottomText("����̫��");
		bottomPopView.setCancelVisible(View.GONE);
		bottomPopView.setGravity(Gravity.CENTER);
		bottomPopView.setColorAll(android.graphics.Color.BLACK);
		bottomPopView.show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		switch (requestCode) {
		case 100:
			if(timeStrFlag == -1){
				timeStrFlag = 1;
				timeStr = intent.getStringExtra("timeA");
				changeTimeB(this); // ת�������101
			}
			break;
		case 101: //ʱ���޸�
			if(timeStrFlag == 1){
				timeStrFlag = -1;
				timeStr += " " + intent.getStringExtra("timeB");
				//Toast.makeText(this, "ʱ�����"+timeStr, 0).show();
				MakeOrder_Mod_ExpTime.setText(timeStr);
			}
			break;
		case 102: //�Ƿ����޸�
			boolean is_rapid = intent.getBooleanExtra("isRapid", false);
			MakeOrder_Mod_IsRapid.setText(is_rapid==true?"��������":"����̫��");
			mod_Order.setIs_Rapid(is_rapid);
			break;
		case 200: //λ����Ϣ�޸�
			try {
				double lat = intent.getDoubleExtra("lat", 0);
				double lon = intent.getDoubleExtra("lon", 0);
				this.latitude = lat;
				this.lontitude = lon;
				String locaton = intent.getStringExtra("addr");
				MakeOrder_Mod_Addr.setText(locaton);
			} catch (Exception e) {
			}
			break;
		default:
			break;
		}
	}
}
