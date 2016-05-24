package com.remix.acrr.Activity.User;

import java.util.Calendar;
import java.util.Locale;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.remix.acrr.R;
import com.remix.acrr.Activity.UserSetting.UserSetting_AddressAll;
import com.remix.acrr.Activity.UserSetting.UserSetting_Describe;
import com.remix.acrr.Activity.UserSetting.UserSetting_PwdChange;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.Tools.SelectPicUtil;
import com.remix.acrr.View.BottomPopView;

public class UserInfoActivity extends Activity {
	@ViewInject(R.id.UserInfoCLickHeadIcon)
	View								ChangeHeadIconView;
	@ViewInject(R.id.UserInfoCLickSex)
	View								UserInfoCLickSexView;
	@ViewInject(R.id.UserInfoCLickSexTextView)
	TextView								UserInfoCLickSexTextView;
	@ViewInject(R.id.UserInfoCLickBirth)
	View								UserInfoCLickBirthView;
	@ViewInject(R.id.UserInfoCLickBirthTextView)
	TextView								UserInfoCLickBirthTextView;
	@ViewInject(R.id.UserInfoCLickDescribe)
	View								UserInfoCLickDescribeView;
	@ViewInject(R.id.UserInfoCLickDescribeTextView)
	TextView								UserInfoCLickDescribeTextView;
	@ViewInject(R.id.UserInfoCLickAddr)
	View								UserInfoCLickAddrView;
	@ViewInject(R.id.UserInfoCLickChangePwd)
	View								UserInfoCLickChangePwdView;
	@ViewInject(R.id.UserInfoCLickQuit)
	Button								UserInfoCLickQuitBtn;
	
	@ViewInject(R.id.UserInfoBackBtn)
	ImageView							UserInfoBackBtn;

	@ViewInject(R.id.UserInfo_userHeadIcon)
	ImageView userInfo_userHeadIconImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info2);
		x.view().inject(this);
		UserInfoBackBtn.setOnClickListener(new MyUtils.MyFinishClickListener(this));
	}

	@Event(value = { R.id.UserInfoCLickHeadIcon, R.id.UserInfoCLickSex, R.id.UserInfoCLickBirth, R.id.UserInfoCLickDescribe,
			R.id.UserInfoCLickAddr, R.id.UserInfoCLickChangePwd, R.id.UserInfoCLickQuit })
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
		BottomPopView bottomPopView = new BottomPopView(this, ChangeHeadIconView) {
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
		BottomPopView bottomPopView = new BottomPopView(this, ChangeHeadIconView) {
			@Override
			public void onTopButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("sex", "男");
				onActivityResult(101, 101, intent);
				dismiss();
			}

			@Override
			public void onBottomButtonClick() {
				Intent intent = getIntent();
				intent.putExtra("sex", "女");
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
		DatePickerDialog dateDlg = new DatePickerDialog(activity,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						String bithString = year + "-" + MyUtils.getTimeSize(monthOfYear) + "-" + MyUtils.getTimeSize(dayOfMonth);
						Intent intent = new Intent();
						intent.putExtra("birth", bithString);
						onActivityResult(102, 102, intent);
						Toast.makeText(activity, bithString, 1).show();
					}
				},
				dateAndTime.get(Calendar.YEAR),
				dateAndTime.get(Calendar.MONTH),
				dateAndTime.get(Calendar.DAY_OF_MONTH));
		dateDlg.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap bm = SelectPicUtil.onActivityResult(this, requestCode, resultCode, data);
		if(requestCode == 125 && bm != null){ // 相册成功事件---》上传
			userInfo_userHeadIconImageView.setImageBitmap(bm);
		}else if(requestCode == 101){ // 修改性别成功---》上传
			String describeString = data.getStringExtra("sex");
			UserInfoCLickSexTextView.setText(describeString);
		}else if(requestCode == 102){ // 修改生日成功---》上传
			String describeString = data.getStringExtra("birth");
			UserInfoCLickBirthTextView.setText(describeString);
		}else if(requestCode == 103){ // 修改describe成功----》上传
			String describeString = data.getStringExtra("describe");
			UserInfoCLickDescribeTextView.setText(describeString);
		}else if(requestCode == 104){ // 修改常用地址成功
			
		}else if(requestCode == 105){ // 修改密码成功
			
		}
	}
}
