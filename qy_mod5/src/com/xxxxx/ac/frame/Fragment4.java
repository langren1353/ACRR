package com.xxxxx.ac.frame;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.xxxxx.ac.R;
import com.xxxxx.ac.MOD.CONST;
import com.xxxxx.ac.Tools.SharedUtil_SharedPrefs;

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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try{
			sharedPrefs = new SharedUtil_SharedPrefs(getContext(), CONST.totalSetting);
			is_gprs_loadPIC = sharedPrefs.getValue(CONST.setting_gprs, is_gprs_loadPIC);
		}catch(Exception e){
			
		}
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame4, null);
		x.view().inject(this, view);// ����this������ע��ʧ��--���fragement��ע��
		textView.setText("��ǰ�汾:V" + getVersion());
		gprsPicButton.setChecked(is_gprs_loadPIC);
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
			break;
		case R.id.CKCupdate:// ������
			// ������
			// Toast.makeText(getContext(), "��ʱû�и���",
			// Toast.LENGTH_SHORT).show();
			showUpdateDialog();
			break;
		default:
			Toast.makeText(getContext(), "����¼�Ŷ", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	public String getVersion() {
		PackageInfo manger = null;
		try {
			manger = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			return manger.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "Unknown";
	}

	public void showUpdateDialog() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_noupdate, null);
		final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
		x.view().inject(dialog, view);
		dialog.show();
		dialog.getWindow().setContentView(view);
		TextView btnOK = (TextView) view.findViewById(R.id.updateOKBtn);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
	}
}
