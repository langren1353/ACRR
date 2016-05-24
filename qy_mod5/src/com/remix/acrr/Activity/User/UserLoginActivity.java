package com.remix.acrr.Activity.User;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.remix.acrr.R;
import com.remix.acrr.ENTITY.Entity_Login;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_CheckCode;
import com.remix.acrr.MOD.Mod_UserInfo;
import com.remix.acrr.Tools.MyUtils;

public class UserLoginActivity extends Activity {
	@ViewInject(R.id.GoRegister)
	TextView goRegisterBtn;
	@ViewInject(R.id.LoginChooseBar)
	View loginChooseBarView;
	@ViewInject(R.id.editLogin1)
	EditText editTextLogin1;
	@ViewInject(R.id.editLogin2)
	EditText editTextLogin2;
	@ViewInject(R.id.btninLogin)
	Button btnInLoginButton;
	@ViewInject(R.id.btnDoLogin)
	Button btnDoLoginButton;
	@ViewInject(R.id.login_title_back)
	ImageView backButton;

	private Animation aniMovetoRight, aniMovetoLeft;
	private int loginType = 1; // =验证码, 2=密码登录
	private int codeType = 1; // =show, 2=hide
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		x.view().inject(this);
		aniMovetoRight = AnimationUtils.loadAnimation(this, R.anim.move_to_right);
		aniMovetoLeft = AnimationUtils.loadAnimation(this, R.anim.move_to_left);
		init();
	}

	public void init() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				Mod_CheckCode modcheckCode =  (Mod_CheckCode) bundle.getSerializable("checkcode");
//				Log.e("DEBUG2", modcheckCode.getErrInfo());
				switch (msg.what) {
				case CONST.OK:
					Toast.makeText(UserLoginActivity.this, "登录成功", 0).show();
					// TODO 登录成功之后应该跳转到一个页面
					Intent intent = getIntent();
					Bundle returnBundle = new Bundle();
					returnBundle.putSerializable("UserInfo", (Mod_UserInfo)modcheckCode.getRespObject());
					intent.putExtras(returnBundle);
					setResult(100, intent);
					finish();
					break;
				case CONST.SENDOK:
					break;
				case CONST.ISREGISTED:
					break;
				case CONST.NOTREGISTED:
					Toast.makeText(UserLoginActivity.this, "该手机号未注册", 0).show();
					break;
				case CONST.NOCODE:
				case CONST.NOTSAME:
					Toast.makeText(UserLoginActivity.this, "验证码不正确", 0).show();
					break;
				case CONST.NOTINTIME:
					Toast.makeText(UserLoginActivity.this, "验证码超时", 0).show();
					break;
				case 1000:
					// 请求：验证码发送成功
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}

		};
		backButton.setOnClickListener(new MyUtils.MyFinishClickListener(this));
		goRegisterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserLoginActivity.this, UserRegisterActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		editTextLogin2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Log.e("DEBUG2", "文字改变");
				int len = editTextLogin2.getText().length();
				if (loginType == 1) { // 只有在{快速登录}输入{4}位验证码后触发
					if (len == 4) {
						changeDoLoginBtnTo(true);
					} else {
						changeDoLoginBtnTo(false);
					}
				}else{ //只有输入6位+密码才能触发
					if (len >= 6) {
						changeDoLoginBtnTo(true);
					} else {
						changeDoLoginBtnTo(false);
					}
				}
			}
		});
		editTextLogin1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Log.e("DEBUG2", "文字改变");
				int len = editTextLogin1.getText().length();
				if (loginType == 1) { // 只有在{快速登录}输入{11}位手机号之后触发
					if (len == 11) {
						changeInLoginBtnTo(true, loginType);
					} else {
						changeInLoginBtnTo(false, loginType);
					}
				}
			}
		});
	}

	@Event(value = R.id.radioGroup1, type = RadioGroup.OnCheckedChangeListener.class)
	private void LoginMethodChangeEvent(RadioGroup group, int checkedId) {
		// Log.e("DEBUG2", "change login type");
		Editable etable = null;
		switch (checkedId) {
		case R.id.radio0:
			loginType = 1;
			codeType = 1;
			editTextLogin2.setText("");
			editTextLogin2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
			loginChooseBarView.startAnimation(aniMovetoLeft);
			// 快速电话号码-验证码登录
			editTextLogin1.setHint(R.string.login_tel_hint);
			editTextLogin2.setHint(R.string.login_code_hint);
			editTextLogin2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD); // hide
			etable = editTextLogin2.getText();
			Selection.setSelection(etable, etable.length());
			break;
		case R.id.radio1:
			loginType = 2;
			codeType = 2;
			editTextLogin2.setText("");
			editTextLogin2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
			loginChooseBarView.startAnimation(aniMovetoRight);
			// 普通账号-密码登录
			editTextLogin1.setHint(R.string.login_account_hint);
			editTextLogin2.setHint(R.string.login_pwd_hint);
			editTextLogin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			etable = editTextLogin2.getText();
			Selection.setSelection(etable, etable.length());
			break;
		default:
			break;
		}
		changeInLoginBtnTo(editTextLogin1.getText().length() == 11, loginType);
	}
	public void changeDoLoginBtnTo(boolean isEnable){
		btnDoLoginButton.setEnabled(isEnable);
		if (isEnable == true) {
			btnDoLoginButton.setBackgroundResource(R.drawable.register_btn_bg);
		} else {
			btnDoLoginButton.setBackgroundResource(R.drawable.register_btn_bg_click);
		}
	}
	public void changeInLoginBtnTo(boolean isEnable, int loginType){
		if(loginType == 1){
			btnInLoginButton.setEnabled(isEnable);
			if(isEnable == true){
				btnInLoginButton.setText("获取验证码");
				btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode);
			}else{
				btnInLoginButton.setText("获取验证码");
				btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode2);
			}
		}else{
			//始终是hide密码模式,并且可以点击
			btnInLoginButton.setText("");
			btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
			btnInLoginButton.setEnabled(true);
		}
	}
	@Event(value = R.id.btninLogin)
	private void btnInLoginEvent(View v) {
		if (loginType == 2) {
			// change 图片事件
			if (codeType == 1) { // show-->hide
				// Log.e("DEBUG2", "切换到普通模式");
				codeType = 2;
				btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
				editTextLogin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // HIDE模式
				Editable etable = editTextLogin2.getText();
				Selection.setSelection(etable, etable.length());
			} else {
				// Log.e("DEBUG2", "切换到密码模式");
				codeType = 1;
				btnInLoginButton.setBackgroundResource(R.drawable.show_pwd);
				editTextLogin2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// 普通模式
				Editable etable = editTextLogin2.getText();
				Selection.setSelection(etable, etable.length());
			}
		} else {
			// 获取验证码事件
			Toast.makeText(this, "请求服务器发送验证码", 1).show();
			Entity_Login entity_Login = new Entity_Login(this);
			entity_Login.getCheckCode(CONST.SendCKLoginType, editTextLogin1.getText().toString(), handler);
			// TODO 完成之后应该加上时间倒计时
		}
	}

	@Event(value = R.id.btnDoLogin)
	private void btnDoLoginEvent(View v) {
		Entity_Login entity_Login = new Entity_Login(this);
		if (loginType == 1) {
			// 传递手机号和验证码即可，不能仅仅传递验证码---->防止串号
			entity_Login.doLogin(CONST.CKLoginType, editTextLogin1.getText().toString(), editTextLogin2.getText().toString(), handler);
		} else {
			// 传递手机号和密码即可
			entity_Login.doLoginwithPWD(editTextLogin1.getText().toString(), editTextLogin2.getText().toString(), handler);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// 注册成功，或者未注册
		try {
			Bundle bundle = intent.getExtras();
			Mod_UserInfo mod_UserInfo = (Mod_UserInfo) bundle.getSerializable("UserInfo");
			Intent intent2 = getIntent();
			Bundle bundle2 = new Bundle();
			bundle2.putSerializable("UserInfo", mod_UserInfo);
			intent2.putExtras(bundle2);
			setResult(1, intent2);
			finish();
		} catch (Exception e) { // 出现异常表示没有UserInfo这个数据
			Log.e("DEBUG2", "没有数据传递====未注册");
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
}
