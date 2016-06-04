package com.remix.acrr.Activity.UserSetting;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.remix.acrr.R;
import com.remix.acrr.ENTITY.Entity_PwdChange;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;

public class UserSetting_PwdChange extends Activity {
	@ViewInject(R.id.UserSettingPwdChangeBack)
	ImageView						UserSettingPwdChangeBack;
	@ViewInject(R.id.UserSettingPwdChangeOldPwd)
	EditText						UserSettingPwdChangeOldPwd;
	@ViewInject(R.id.UserSettingPwdChangeNewPwd1)
	EditText						UserSettingPwdChangeNewPwd1;
	@ViewInject(R.id.UserSettingPwdChangeNewPwd2)
	EditText						UserSettingPwdChangeNewPwd2;
	
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_setting__pwd_change);
		x.view().inject(this);
		UserSettingPwdChangeBack.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Toast.makeText(UserSetting_PwdChange.this, msg.obj.toString(), 0).show();
				super.handleMessage(msg);
			}
		};
	}
	
	@Event(value=R.id.UserSettingPwdChangePwdChangeBtn)
	private void BtnClickListener(View v){
		if(v.getId() == R.id.UserSettingPwdChangePwdChangeBtn){
			String new1 = UserSettingPwdChangeNewPwd1.getText().toString();
			String new2 = UserSettingPwdChangeNewPwd2.getText().toString();
			String old = UserSettingPwdChangeOldPwd.getText().toString();
			if(new1.length() > 6){
				if(new1.equals(new2)){
					new Entity_PwdChange().changePwd(MyUtils.GetMD5(new1), MyUtils.GetMD5(old), CONST.userInfo.getTel(), CONST.userInfo.getToken(), handler);
				}else{
					UserSettingPwdChangeNewPwd2.setError(Html.fromHtml("<font color=red>两次输入的密码不一致!</font>"));
				}
			}else{
				UserSettingPwdChangeNewPwd1.setError(Html.fromHtml("<font color=red>请输入6位数以上的密码!</font>"));
			}
		}
	}
}
