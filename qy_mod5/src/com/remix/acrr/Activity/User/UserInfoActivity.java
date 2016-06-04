package com.remix.acrr.Activity.User;

import java.util.Calendar;
import java.util.Locale;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.bu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.remix.acrr.R;
import com.remix.acrr.Activity.UserSetting.UserSetting_AddressAll;
import com.remix.acrr.Activity.UserSetting.UserSetting_Describe;
import com.remix.acrr.Activity.UserSetting.UserSetting_PwdChange;
import com.remix.acrr.ENTITY.Entity_UploadHeadIcon;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_UserInfo;
import com.remix.acrr.MOD.UserStore;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.Tools.SelectPicUtil;
import com.remix.acrr.View.BottomPopView;
import com.remix.acrr.dialog.MyDatePickerDialog;

public class UserInfoActivity extends Activity {
	@ViewInject(R.id.UserInfoCLickHeadIcon)
	View ChangeHeadIconView;
	@ViewInject(R.id.UserInfoCLickNameTextView)
	EditText UserInfoCLickNameTextView;
	@ViewInject(R.id.UserInfoCLickSex)
	View UserInfoCLickSexView;
	@ViewInject(R.id.UserInfoCLickSexTextView)
	TextView UserInfoCLickSexTextView;
	@ViewInject(R.id.UserInfoCLickBirth)
	View UserInfoCLickBirthView;
	@ViewInject(R.id.UserInfoCLickBirthTextView)
	TextView UserInfoCLickBirthTextView;
	@ViewInject(R.id.UserInfoCLickDescribe)
	View UserInfoCLickDescribeView;
	@ViewInject(R.id.UserInfoCLickDescribeTextView)
	TextView UserInfoCLickDescribeTextView;
	@ViewInject(R.id.UserInfoCLickAddr)
	View UserInfoCLickAddrView;
	@ViewInject(R.id.UserInfoCLickChangePwd)
	View UserInfoCLickChangePwdView;
	@ViewInject(R.id.UserInfoCLickQuit)
	Button UserInfoCLickQuitBtn;

	@ViewInject(R.id.UserInfoBackBtn)
	ImageView UserInfoBackBtn;

	@ViewInject(R.id.UserInfo_userHeadIcon)
	ImageView userInfo_userHeadIconImageView;
	private Mod_UserInfo userinfo;
	private Bitmap bm = null;
	private boolean hasChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info2);
		x.view().inject(this);
		UserInfoBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		init();
	}

	private void init() {
		userinfo = (Mod_UserInfo) getIntent().getSerializableExtra("userinfo");
		if (userinfo != null) {
			try {
				x.image().bind(userInfo_userHeadIconImageView,
						CONST.host + userinfo.getPic(),
						MyAndUtils.getImageOption(),
						MyAndUtils.getCacheCallBack());
				UserInfoCLickSexTextView.setText(userinfo.getSex());
				UserInfoCLickBirthTextView.setText(MyUtils.getDateStr(userinfo
						.getBirth()));
				UserInfoCLickDescribeTextView.setText(userinfo.getDescribe());
				UserInfoCLickNameTextView.setText(userinfo.getName());
			} catch (Exception e) {
			}
		}
	}

	@Event(value = { R.id.UserInfoCLickHeadIcon, R.id.UserInfoCLickSex,
			R.id.UserInfoCLickBirth, R.id.UserInfoCLickDescribe,
			R.id.UserInfoCLickAddr, R.id.UserInfoCLickChangePwd,
			R.id.UserInfoCLickQuit })
	private void justClickListener(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.UserInfoCLickHeadIcon: // 切换头像
			showPicChange(UserInfoActivity.this);
			break;
		case R.id.UserInfoCLickSex: // 切换性别
			changeSex(UserInfoActivity.this);
			break;
		case R.id.UserInfoCLickBirth: // 切换生日
			changeBirth(UserInfoActivity.this);
			break;
		// 下面的都进行了Activity跳转
		case R.id.UserInfoCLickDescribe: // 切换个人简介
			intent.setClass(UserInfoActivity.this, UserSetting_Describe.class);
			intent.putExtra("describe", UserInfoCLickDescribeTextView.getText());
			startActivityForResult(intent, 103);
			break;
		case R.id.UserInfoCLickAddr:// 切换住址
			intent.setClass(UserInfoActivity.this, UserSetting_AddressAll.class);
			startActivityForResult(intent, 104);
			break;
		case R.id.UserInfoCLickChangePwd: // 切换密码
			intent.setClass(UserInfoActivity.this, UserSetting_PwdChange.class);
			startActivityForResult(intent, 105);
			break;
		case R.id.UserInfoCLickQuit: // 退出登录
			CONST.userInfo = null;
			DbManager dbm = x.getDb(CONST.daoConfig);
			try {
				dbm.dropTable(UserStore.class);
			} catch (Exception e) {
			}
			setResult(101, getIntent());
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 切换头像
	 * 
	 * @param activity
	 */
	private void showPicChange(final Activity activity) {
		BottomPopView bottomPopView = new BottomPopView(this,
				ChangeHeadIconView) {
			@Override
			public void onTopButtonClick() {
				SelectPicUtil.getByAlbum(context);
				dismiss();
			}

			@Override
			public void onBottomButtonClick() {
				SelectPicUtil.getByCamera(context);
				dismiss();
			}
		};
		bottomPopView.setTopText("从相册中选择");
		bottomPopView.setBottomText("拍张吧~");
		bottomPopView.show();
	}

	/**
	 * 切换性别
	 */
	public void changeSex(final Activity activity) {
		BottomPopView bottomPopView = new BottomPopView(this,
				ChangeHeadIconView) {
			@Override
			public void onTopButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("sex", "男");
				intent.putExtra("result", true);
				onActivityResult(101, 101, intent);
				dismiss();
			}

			@Override
			public void onBottomButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("sex", "女");
				intent.putExtra("result", true);
				onActivityResult(101, 101, intent);
				dismiss();
			}
		};
		bottomPopView.setTopText("男");
		bottomPopView.setBottomText("女");
		bottomPopView.setCancelVisible(View.GONE);
		bottomPopView.setGravity(Gravity.CENTER);
		bottomPopView.setColorAll(android.graphics.Color.BLACK);
		bottomPopView.show();
	}

	public void changeBirth(final Activity activity) {
		Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
		DatePickerDialog dateDlg = new MyDatePickerDialog(activity,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						String bithString = year + "-"
								+ MyUtils.getTimeSize(monthOfYear) + "-"
								+ MyUtils.getTimeSize(dayOfMonth);
						Intent intent = new Intent();
						intent.putExtra("result", true);
						intent.putExtra("birth", bithString);
						onActivityResult(102, 102, intent);
						Toast.makeText(activity, bithString, 1).show();
					}
				}, dateAndTime.get(Calendar.YEAR),
				dateAndTime.get(Calendar.MONTH),
				dateAndTime.get(Calendar.DAY_OF_MONTH));
		dateDlg.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		bm = SelectPicUtil
				.onActivityResult(this, requestCode, resultCode, data);
		Boolean isSuccess = false;
		try {
			isSuccess = data.getBooleanExtra("result", false);
		} catch (Exception e) {
		}
		hasChanged = true;
		if (requestCode == 125 && bm != null) { // 相册成功事件---》上传
			userInfo_userHeadIconImageView.setImageBitmap(bm);
		} else if (requestCode == 101) { // 修改性别成功---》上传
			if (isSuccess == true) {
				String describeString = data.getStringExtra("sex");
				UserInfoCLickSexTextView.setText(describeString);
			}
		} else if (requestCode == 102) { // 修改生日成功---》上传
			if (isSuccess == true) {
				String describeString = data.getStringExtra("birth");
				UserInfoCLickBirthTextView.setText(describeString);
			}
		} else if (requestCode == 103) { // 修改describe成功----》上传
			if (isSuccess == true) {
				String describeString = data.getStringExtra("describe");
				UserInfoCLickDescribeTextView.setText(describeString);
			}
		} else if (requestCode == 104) { // 修改常用地址成功

		} else if (requestCode == 105) { // 修改密码成功
			hasChanged = false;
		} else {
			hasChanged = false;
		}
	}

	@Override
	public void onBackPressed() {
		try {
			if (!userinfo.getName().equals(UserInfoCLickNameTextView.getText().toString()))
				hasChanged = true;
			// 上传用户数据+图片上传
			if (hasChanged == true) {
				if (bm != null) {
					new Entity_UploadHeadIcon().upload(bm, userinfo.getTel(),
							userinfo.getToken());
				}
				// ----------------上传数据-----------------
				userinfo.setName(UserInfoCLickNameTextView.getText().toString());
				userinfo.setSex(UserInfoCLickSexTextView.getText().toString());
				userinfo.setBirth(MyUtils.getDate(UserInfoCLickBirthTextView
						.getText().toString()));
				userinfo.setDescribe(UserInfoCLickDescribeTextView.getText()
						.toString());
				String url = CONST.host + CONST.UpdateUserInfoServlet;
				RequestParams requestParams = new RequestParams(url);
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
						.create();
				requestParams.setBodyContent(gson.toJson(userinfo));
				x.http().post(requestParams, new CommonCallback<String>() {
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
						Gson gson = new GsonBuilder().setDateFormat(
								"yyyy-MM-dd").create();
						ResponseObject responseObject = gson.fromJson(arg0,
								ResponseObject.class);
						String result = responseObject.getMsg();
						Toast.makeText(UserInfoActivity.this, result, 0).show();
					}
				});
			}
			Intent intent = getIntent();
			if (bm != null) {
				intent.putExtra("image", bm);
			}
			setResult(500, intent);
			finish();
		} catch (Exception e) {
		}
		super.onBackPressed();
	}
}
