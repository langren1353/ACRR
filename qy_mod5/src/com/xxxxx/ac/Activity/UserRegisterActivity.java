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
import com.xxxxx.ac.ENTITY.Entity_Registe;
import com.xxxxx.ac.MOD.CONST;
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
	@ViewInject(R.id.btnRegister)
	Button						btnDoRegister;
	
	private String checkCode;
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
				Log.e("DEBUG2", "���ָı�");
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
		Log.e("DEBUG2", "��ʾ/������������");
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
		Entity_Registe checkCode = new Entity_Registe();
		switch(v.getId()){
		
			case R.id.BtnCheckCode:
				Log.e("DEBUG2", "���������������֤��");
				//Toast.makeText(this, "׼�����������������֤��", 1).show();
				checkCode.getCheckCode(CONST.SendTelType, editTelText.getText().toString());
				break;
				
			case R.id.btnRegister:
				Log.e("DEBUG2", "׼����ʽע�᣺�ϴ��ֻ��š���֤�롢����");
				//Toast.makeText(this, "��ʽע��", 1).show();
				checkCode.getCheckCode(CONST.SendTelType, editTelText.getText().toString());
				break;
		}
	}
	private boolean validateCanRegister(){
		// �����������6λ����ע��
		// �������1 != ����2 ����ע��
		// �����֤�벻��ȷ����ע��
		
		// ����ֻ����Ѿ����ڲ���ע�ᣬ���¼
		if(editPwdText.getText().length() < 6) return false;
		if(!editPwdText.getText().equals(editPwd2Text.getText())) return false;
		if(!editCheckCodeText.equals(checkCode)) return false;
		return true;
	}
}
