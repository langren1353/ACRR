package com.xxxxx.ac.Activity;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
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
import com.xxxxx.ac.Tools.CommonUtils;

public class UserRegisterActivity extends Activity {
	@ViewInject(R.id.IsShowPwd)
	ToggleButton				isShowPwd;
	@ViewInject(R.id.editTel)
	EditText					editTelText;
	@ViewInject(R.id.editCheckCode)
	EditText					editCheckCodeText;
	@ViewInject(R.id.editPwd)
	EditText					editPwdText;
	@ViewInject(R.id.editPwd2)
	EditText					editPwd2Text;
	@ViewInject(R.id.BtnCheckCode)
	Button						btnGetCheckCode;
	@ViewInject(R.id.register_title_back)
	ImageView					btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		x.view().inject(this);
		init();
	}
	
	public void init(){
		btnBack.setOnClickListener(new CommonUtils.MyFinishClickListener(this));
		editTelText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				Log.e("DEBUG2", "文字改变");
				int len = editTelText.getText().length();
				if(len == 11){
					btnGetCheckCode.setEnabled(true);
					btnGetCheckCode.setBackgroundResource(R.drawable.register_btn_getcode);
				}else{
					btnGetCheckCode.setEnabled(false);
					btnGetCheckCode.setBackgroundResource(R.drawable.register_btn_getcode2);
				}
			}
		});
	}
	
	@Event(value=R.id.IsShowPwd, type=CompoundButton.OnCheckedChangeListener.class)
	private void IsShowPwdChangedEvent(CompoundButton buttonView, boolean isChecked) {
		Log.e("DEBUG2", "显示/隐藏明文密码");
		if(isShowPwd.isChecked() == true){
			isShowPwd.setBackgroundResource(R.drawable.show_pwd);
			editPwdText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			Editable etable = editPwdText.getText();
            Selection.setSelection(etable, etable.length());
		}else{
			isShowPwd.setBackgroundResource(R.drawable.hide_pwd);
			editPwdText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Editable etable = editPwdText.getText();
            Selection.setSelection(etable, etable.length());
		}
	}
	@Event(value=R.id.BtnCheckCode, type=View.OnClickListener.class)
	private void GetCheckCodeEvent(View v){
		Log.e("DEBUG2", "请求服务器");
		Toast.makeText(this, "准备请求服务器发送验证码", 1).show();
	}
}
