package com.xxxxx.ac.Activity;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xxxxx.ac.R;
import com.xxxxx.ac.Tools.CommonUtils;

public class UserLoginActivity extends Activity {
	@ViewInject(R.id.GoRegister)
	TextView					goRegisterBtn;
	@ViewInject(R.id.LoginChooseBar)
	View						loginChooseBarView;
	@ViewInject(R.id.editLogin1)
	EditText					editTextLogin1;
	@ViewInject(R.id.editLogin2)
	EditText					editTextLogin2;
	@ViewInject(R.id.btninLogin)
	Button						btnInLoginButton;
	@ViewInject(R.id.login_title_back)
	Button						backButton;
	
	private Animation			aniMovetoRight, aniMovetoLeft;
	private int loginType = 1;
	private int codeType = 1; //=show, 2=hide
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		x.view().inject(this);
		aniMovetoRight = AnimationUtils.loadAnimation(this, R.anim.move_to_right);
		aniMovetoLeft  = AnimationUtils.loadAnimation(this, R.anim.move_to_left);
		init();
	}
	
	public void init(){
		backButton.setOnClickListener(new CommonUtils.MyFinishClickListener(this));
		goRegisterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserLoginActivity.this, UserRegisterActivity.class);
				startActivity(intent);
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
				Log.e("DEBUG2", "���ָı�");
				int len = editTextLogin1.getText().length();
				if(loginType == 1){ //ֻ����{���ٵ�¼}����{11}λ�ֻ���֮�󴥷�
					if(len == 11){
						btnInLoginButton.setEnabled(true);
						btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode);
					}else{
						btnInLoginButton.setEnabled(false);
						btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode2);
					}
				}
			}
		});
	}
	
	@Event(value=R.id.radioGroup1, type=RadioGroup.OnCheckedChangeListener.class)
	private void LoginMethodChangeEvent(RadioGroup group, int checkedId){
		Log.e("DEBUG2", "change login type");
		switch (checkedId) {
		case R.id.radio0:
			loginType = 1;
			codeType = 1;
			loginChooseBarView.startAnimation(aniMovetoLeft);
			// ���ٵ绰����-��֤���¼
			editTextLogin1.setHint(R.string.login_tel_hint);
			editTextLogin2.setHint(R.string.login_code_hint);
			btnInLoginButton.setBackgroundResource(R.drawable.register_btn_getcode2);
			btnInLoginButton.setText("��ȡ��֤��");
			btnInLoginButton.setEnabled(false);
			break;
		case R.id.radio1:
			loginType = 2;
			codeType = 2;
			loginChooseBarView.startAnimation(aniMovetoRight);
			// ��ͨ�˺�-�����¼
			editTextLogin1.setHint(R.string.login_account_hint);
			editTextLogin2.setHint(R.string.login_pwd_hint);
			btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
			editTextLogin2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); //hide
			btnInLoginButton.setText("");
			btnInLoginButton.setEnabled(true);
			break;
		default:
			break;
		}
	}
	@Event(value=R.id.btninLogin)
	private void btnInLoginEvent(View v){
		if(loginType == 2){
			// change ͼƬ�¼�
			if(codeType == 1){ //show-->hide
				codeType = 2;
				btnInLoginButton.setBackgroundResource(R.drawable.hide_pwd);
				editTextLogin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	            Editable etable = editTextLogin2.getText();
	            Selection.setSelection(etable, etable.length());
			}else{
				codeType = 1;
				btnInLoginButton.setBackgroundResource(R.drawable.show_pwd);
				editTextLogin2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				Editable etable = editTextLogin2.getText();
	            Selection.setSelection(etable, etable.length());
			}
		}else{
			// ��ȡ��֤���¼�
			Toast.makeText(this, "�����������ȡ��֤��", 1).show();
		}
	}
}
