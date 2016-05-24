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
	private int loginType = 1; // =��֤��, 2=�����¼
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
					Toast.makeText(UserLoginActivity.this, "��¼�ɹ�", 0).show();
					// TODO ��¼�ɹ�֮��Ӧ����ת��һ��ҳ��
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
					Toast.makeText(UserLoginActivity.this, "���ֻ���δע��", 0).show();
					break;
				case CONST.NOCODE:
				case CONST.NOTSAME:
					Toast.makeText(UserLoginActivity.this, "��֤�벻��ȷ", 0).show();
					break;
				case CONST.NOTINTIME:
					Toast.makeText(UserLoginActivity.this, "��֤�볬ʱ", 0).show();
					break;
				case 1000:
					// ������֤�뷢�ͳɹ�
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
				// Log.e("DEBUG2", "���ָı�");
				int len = editTextLogin2.getText().length();
				if (loginType == 1) { // ֻ����{���ٵ�¼}����{4}λ��֤��󴥷�
					if (len == 4) {
						changeDoLoginBtnTo(true);
					} else {
						changeDoLoginBtnTo(false);
					}
				}else{ //ֻ������6λ+������ܴ���
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
				// Log.e("DEBUG2", "���ָı�");
				int len = editTextLogin1.getText().length();
				if (loginType == 1) { // ֻ����{���ٵ�¼}����{11}λ�ֻ���֮�󴥷�
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
			// ���ٵ绰����-��֤���¼
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
			// ��ͨ�˺�-�����¼
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
				btnInLoginButton.setText("��ȡ��֤��");
				btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode);
			}else{
				btnInLoginButton.setText("��ȡ��֤��");
				btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode2);
			}
		}else{
			//ʼ����hide����ģʽ,���ҿ��Ե��
			btnInLoginButton.setText("");
			btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
			btnInLoginButton.setEnabled(true);
		}
	}
	@Event(value = R.id.btninLogin)
	private void btnInLoginEvent(View v) {
		if (loginType == 2) {
			// change ͼƬ�¼�
			if (codeType == 1) { // show-->hide
				// Log.e("DEBUG2", "�л�����ͨģʽ");
				codeType = 2;
				btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
				editTextLogin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // HIDEģʽ
				Editable etable = editTextLogin2.getText();
				Selection.setSelection(etable, etable.length());
			} else {
				// Log.e("DEBUG2", "�л�������ģʽ");
				codeType = 1;
				btnInLoginButton.setBackgroundResource(R.drawable.show_pwd);
				editTextLogin2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);// ��ͨģʽ
				Editable etable = editTextLogin2.getText();
				Selection.setSelection(etable, etable.length());
			}
		} else {
			// ��ȡ��֤���¼�
			Toast.makeText(this, "���������������֤��", 1).show();
			Entity_Login entity_Login = new Entity_Login(this);
			entity_Login.getCheckCode(CONST.SendCKLoginType, editTextLogin1.getText().toString(), handler);
			// TODO ���֮��Ӧ�ü���ʱ�䵹��ʱ
		}
	}

	@Event(value = R.id.btnDoLogin)
	private void btnDoLoginEvent(View v) {
		Entity_Login entity_Login = new Entity_Login(this);
		if (loginType == 1) {
			// �����ֻ��ź���֤�뼴�ɣ����ܽ���������֤��---->��ֹ����
			entity_Login.doLogin(CONST.CKLoginType, editTextLogin1.getText().toString(), editTextLogin2.getText().toString(), handler);
		} else {
			// �����ֻ��ź����뼴��
			entity_Login.doLoginwithPWD(editTextLogin1.getText().toString(), editTextLogin2.getText().toString(), handler);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// ע��ɹ�������δע��
		try {
			Bundle bundle = intent.getExtras();
			Mod_UserInfo mod_UserInfo = (Mod_UserInfo) bundle.getSerializable("UserInfo");
			Intent intent2 = getIntent();
			Bundle bundle2 = new Bundle();
			bundle2.putSerializable("UserInfo", mod_UserInfo);
			intent2.putExtras(bundle2);
			setResult(1, intent2);
			finish();
		} catch (Exception e) { // �����쳣��ʾû��UserInfo�������
			Log.e("DEBUG2", "û�����ݴ���====δע��");
		}
		super.onActivityResult(requestCode, resultCode, intent);
	}
}
