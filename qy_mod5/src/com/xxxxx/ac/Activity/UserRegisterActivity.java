package com.xxxxx.ac.Activity;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.umeng.fb.util.Log;
import com.xxxxx.ac.R;
import com.xxxxx.ac.ENTITY.Entity_Registe;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.MOD.Mod_CheckCode;
import com.xxxxx.ac.MOD.Mod_UserInfo;
import com.xxxxx.ac.Tools.CommonUtils;

public class UserRegisterActivity extends Activity {
	@ViewInject(R.id.IsShowPwd)
	ToggleButton isShowPwd;
	@ViewInject(R.id.editTel)
	EditText editTelText;
	@ViewInject(R.id.editCheckCode)
	EditText editCheckCodeText;
	@ViewInject(R.id.editPwd)
	EditText editPwdText;
	@ViewInject(R.id.editPwd2)
	EditText editPwd2Text;
	@ViewInject(R.id.BtnCheckCode)
	Button btnGetCheckCode;
	@ViewInject(R.id.register_title_back)
	ImageView btnBack;
	@ViewInject(R.id.btnRegister)
	Button btnDoRegister;

	private String checkCode;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		x.view().inject(this);
		init();
	}

	public void init() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 未注册则可以注册，注册过的请登录，注册失败返回原因
				Bundle bundle = msg.getData();
				Mod_CheckCode modcheckCode = (Mod_CheckCode) bundle.getSerializable("checkcode");
				// Log.e("DEBUG2", modcheckCode.getErrInfo());
				checkCode = modcheckCode.getCheckCode();
				switch (msg.what) {
				case CONST.OK:
					Toast.makeText(UserRegisterActivity.this, "注册成功", 0).show();
					// TODO 登录成功之后应该跳转到一个页面
					Intent intent = getIntent();
					Bundle returnBundle = new Bundle();
					returnBundle.putSerializable("UserInfo", (Mod_UserInfo) modcheckCode.getRespObject());
					intent.putExtras(returnBundle);
					setResult(1, intent);
					finish();
					break;
				case CONST.SENDOK:
					break;
				case CONST.ISREGISTED:
					Toast.makeText(UserRegisterActivity.this, "该手机号已注册", 0).show();
					editTelText.setError(Html.fromHtml("<font color=red>该手机号已注册!</font>"));
					break;
				case CONST.NOTREGISTED:
					// Toast.makeText(UserRegisterActivity.this, "该手机号未注册", 0).show();
					break;
				case CONST.NOCODE:
				case CONST.NOTSAME:
					Toast.makeText(UserRegisterActivity.this, "验证码不正确", 0).show();
					break;
				case CONST.NOTINTIME:
					Toast.makeText(UserRegisterActivity.this, "验证码超时", 0).show();
					break;
				case 1000:
					// 请求验证码成功
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		btnBack.setOnClickListener(new CommonUtils.MyFinishClickListener(this));
		editCheckCodeText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.e("DEBUG2", "验证码文字改变");
				int len = editCheckCodeText.getText().length();
				if (len == 4) {
					btnDoRegister.setEnabled(true);
					btnDoRegister.setBackgroundResource(R.drawable.register_btn_bg);
				} else {
					btnDoRegister.setEnabled(false);
					btnDoRegister.setBackgroundResource(R.drawable.register_btn_bg_click);
				}
			}
		});
		editTelText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.e("DEBUG2", "手机号码文字改变");
				int len = editTelText.getText().length();
				if (len == 11) {
					btnGetCheckCode.setEnabled(true);
					btnGetCheckCode.setBackgroundResource(R.drawable.register_btn_getcode);
				} else {
					btnGetCheckCode.setEnabled(false);
					btnGetCheckCode.setBackgroundResource(R.drawable.register_btn_getcode2);
				}
			}
		});
	}

	@Event(value = R.id.IsShowPwd, type = CompoundButton.OnCheckedChangeListener.class)
	private void IsShowPwdChangedEvent(CompoundButton buttonView, boolean isChecked) {
		Log.e("DEBUG2", "显示/隐藏明文密码");
		if (isShowPwd.isChecked() == true) {
			isShowPwd.setBackgroundResource(R.drawable.show_pwd);
			editPwdText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			Editable etable = editPwdText.getText();
			Selection.setSelection(etable, etable.length());
		} else {
			isShowPwd.setBackgroundResource(R.drawable.hide_pwd);
			editPwdText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			Editable etable = editPwdText.getText();
			Selection.setSelection(etable, etable.length());
		}
	}

	@Event(value = { R.id.BtnCheckCode, R.id.btnRegister }, type = View.OnClickListener.class)
	private void GetCheckCodeEvent(View v) {
		Entity_Registe checkCode = new Entity_Registe(this);
		switch (v.getId()) {
		case R.id.BtnCheckCode:
			Log.e("DEBUG2", "请求服务器发送验证码");
			// Toast.makeText(this, "准备请求服务器发送验证码", 1).show();
			checkCode.getCheckCode(CONST.SendCKRegisteType, editTelText.getText().toString(), handler);
			break;
		case R.id.btnRegister:
			Log.e("DEBUG2", "准备正式注册：上传手机号、验证码、密码");
			// Toast.makeText(this, "正式注册", 1).show();
			if (validateCanRegister() == true)
				checkCode.doRegister(CONST.CKRegisterType, editTelText.getText().toString(), editCheckCodeText.getText().toString(), editPwdText.getText()
						.toString(), handler);
			break;
		}
	}

	private boolean validateCanRegister() {
		// 如果密码少于6位不能注册
		// 如果密码1 != 密码2 不能注册
		// 如果验证码不正确不能注册

		// 如果手机号已经存在不能注册，请登录
		if (editPwdText.getText().toString().length() < 6) {
			editPwdText.setError(Html.fromHtml("<font color=red>请填写6位以上的密码!</font>"));
			return false;
		}
		if (!editPwdText.getText().toString().equals(editPwd2Text.getText().toString())) {
			editPwd2Text.setError(Html.fromHtml("<font color=red>两次输入的密码不一致!</font>"));
			return false;
		}
		if (!editCheckCodeText.getText().toString().equals(checkCode)) {
			editCheckCodeText.setError(Html.fromHtml("<font color=red>验证码不正确!</font>"));
			return false;
		}
		return true;
	}
}
