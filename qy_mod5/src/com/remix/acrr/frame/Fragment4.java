package com.remix.acrr.frame;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.remix.acrr.R;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.remix.acrr.Activity.User.UserInfoActivity;
import com.remix.acrr.ENTITY.Entity_CKUpdate;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.Tools.SharedUtil_SharedPrefs;
import com.remix.acrr.dialog.AboutDialog;
import com.remix.acrr.dialog.AlertDialog_MY;
import com.remix.acrr.dialog.DownloadDialog;
import com.remix.acrr.dialog.NotLoginDialog;

public class Fragment4 extends Fragment {
	@ViewInject(R.id.CKCabout)
	LinearLayout CKabout;
	@ViewInject(R.id.CKCbind)
	LinearLayout CKCbind;
	@ViewInject(R.id.CKCcomment)
	LinearLayout CKCcomment;
	@ViewInject(R.id.CKCfeedback)
	LinearLayout CKCfeedback;
	@ViewInject(R.id.CKCgprsPic)
	LinearLayout CKCgprsPic;
	@ViewInject(R.id.CKCmyacount)
	LinearLayout CKCmyacount;
	@ViewInject(R.id.CKCupdate)
	LinearLayout CKCupdate;
	@ViewInject(R.id.toggleButton1)
	ToggleButton gprsPicButton;
	@ViewInject(R.id.CurentVersion)
	TextView textView;

	SharedUtil_SharedPrefs sharedPrefs;
	Boolean is_gprs_loadPIC = true;
	private Handler handler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try{
			sharedPrefs = new SharedUtil_SharedPrefs(getContext(), CONST.totalSetting);
			is_gprs_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_gprs_loadPIC);
		}catch(Exception e){
			
		}
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame4, null);
		x.view().inject(this, view);// ����this������ע��ʧ��--���fragement��ע��
		textView.setText("��ǰ�汾:V" + MyAndUtils.getVersion(getActivity()));
		gprsPicButton.setChecked(is_gprs_loadPIC);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.obj.equals("1")){
					// ��һ����ť
					new DownloadDialog(getActivity()).download(CONST.host+CONST.downloadUpdateUrl);
				}else{
					// �ڶ�����ť
				}
				super.handleMessage(msg);
			}
		};
		return view;
	}

	// Ӧ�Ե�������
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Event(value = { R.id.CKCabout, R.id.CKCbind, R.id.CKCcomment, R.id.toggleButton1,
			R.id.CKCfeedback, R.id.CKCgprsPic, R.id.CKCmyacount, R.id.CKCupdate },
			type = View.OnClickListener.class /* ��ѡ���� , Ĭ����View.OnClickListener.class */)
	private void onTestBaidu1Click(View view) {
		switch (view.getId()) {
		case R.id.CKCgprsPic: // �л�GPRS��ʾͼƬ��
			gprsPicButton.toggle();
		case R.id.toggleButton1: // �л�GPRS��ʾͼƬ��
			is_gprs_loadPIC = !is_gprs_loadPIC;
			sharedPrefs.putValue(CONST.setting_gprs, is_gprs_loadPIC);
			break;
		case R.id.CKCabout: // ����
			new AboutDialog(getActivity()).showDialog();
			break;
		case R.id.CKCbind: // �˺Ű�
			break;
		case R.id.CKCcomment:// ����
			break;
		case R.id.CKCfeedback:// ����
			FeedbackAPI.initAnnoy(getActivity().getApplication(), "23342831");
			FeedbackAPI.initOpenImAccount(getActivity().getApplication(), "23342831", "AC", "123");
			FeedbackAPI.openFeedbackActivity(getActivity().getApplication());
			break;
		case R.id.CKCmyacount:// �ҵ��˺�
			if(CONST.userInfo == null){
				new NotLoginDialog(getActivity()).getDialog().show();
			}else{
				Intent intent = new Intent();
				intent.setClass(getActivity(), UserInfoActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.CKCupdate:// ������
			// ������
			// Toast.makeText(getContext(), "��ʱû�и���",
			// Toast.LENGTH_SHORT).show();
			//new NoUpdateDialog(getActivity());
			new Entity_CKUpdate(getActivity()).checkUpdate(handler);
			break;
		default:
			Toast.makeText(getContext(), "����¼�Ŷ", Toast.LENGTH_SHORT).show();
			break;
		}
	}


}
