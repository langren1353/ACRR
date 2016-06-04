package com.remix.acrr.frame;

import java.io.IOException;

import org.xutils.DbManager;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.bu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.remix.acrr.R;
import com.remix.acrr.Activity.User.UserInfoActivity;
import com.remix.acrr.Activity.User.UserLoginActivity;
import com.remix.acrr.Activity.UserOrders.UserOrdersHistory;
import com.remix.acrr.Activity.UserOrders.UserOrdersMy;
import com.remix.acrr.Activity.UserOrders.UserOrdersNeedMe;
import com.remix.acrr.ENTITY.Entity_UploadHeadIcon;
import com.remix.acrr.ENTITY.RespData.ResponseObject;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_CheckCode;
import com.remix.acrr.MOD.Mod_UserInfo;
import com.remix.acrr.MOD.UserStore;
import com.remix.acrr.Tools.MyAndUtils;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.Tools.SelectPicUtil;
import com.remix.acrr.View.BottomPopView;
import com.remix.acrr.dialog.NotLoginDialog;

public class Fragment3 extends Fragment {
	@ViewInject(R.id.userNotloginPanel)
	View userNotloginPanelView;
	@ViewInject(R.id.loginButton)
	Button btn_loginButton;

	@ViewInject(R.id.userPanel)
	View userPanelView;
	@ViewInject(R.id.username)
	TextView userNameTextView;
	@ViewInject(R.id.userdescribe)
	TextView userDescribeTextView;
	@ViewInject(R.id.userheadicon)
	ImageView userheadiconImageView;
	private Mod_UserInfo userInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.main_frame3, null);
		x.view().inject(this, view);
		Login_IsSuccess(findHasLogin());
		userheadiconImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳转到上传头像
				BottomPopView bottomPopView = new BottomPopView(getActivity(),
						v) {
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
				bottomPopView.setTopText("选择图片");
				bottomPopView.setBottomText("拍张照片吧~");
				bottomPopView.show();
			}
		});
		userPanelView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳转到用户信息界面
				Intent intent = new Intent();
				intent.setClass(getActivity(), UserInfoActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("userinfo", userInfo);
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});
		btn_loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getContext(), UserLoginActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		return view;
	}

	// 应对叠层现象
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Bitmap bm = SelectPicUtil.onActivityResult(getActivity(), requestCode,
				resultCode, intent);
		if (bm != null) {
			userheadiconImageView.setImageBitmap(bm);
			new Entity_UploadHeadIcon().upload(bm, userInfo.getTel(),
					userInfo.getToken());
		}
		if (resultCode == 100) {
			try {
				Bundle bundle = intent.getExtras();
				Mod_UserInfo userInfo = (Mod_UserInfo) bundle
						.getSerializable("UserInfo");
				this.userInfo = userInfo;
				Login_IsSuccess(true);
				userNameTextView.setText(userInfo.getName());
				userDescribeTextView.setText(userInfo.getDescribe());
				String url = userInfo.getPic();
				x.image().bind(userheadiconImageView, CONST.host + url,
						MyAndUtils.getImageOption());

			} catch (Exception e) {
				Log.e("DEBUG2", "没有数据传递====未登录");
				e.printStackTrace();
			}
		} else if (resultCode == 101) {
			// 退出登录
			//Toast.makeText(getActivity(), "请重新登录", 0).show();
			Login_IsSuccess(findHasLogin());
		} else if (resultCode == 500 && requestCode == 1) { // 信息修改
			Log.v("OUT", "用户信息更改，重新获取用户数据");
			try{
				Bitmap bitmap = intent.getParcelableExtra("image");
				userheadiconImageView.setImageBitmap(bitmap);
			}catch(Exception e){
			}
			initUser();
		}
	}

	public void Login_IsSuccess(boolean isSuccess) {
		if (isSuccess == true) {
			userPanelView.setVisibility(View.VISIBLE);
			userNotloginPanelView.setVisibility(View.GONE);
			//userDescribeTextView.setText(CONST.user.getDescribe());
			x.image().bind(userheadiconImageView, CONST.host + CONST.userInfo.getPic());
			//userNameTextView.setText(CONST.user.getName() + "");
		} else {
			userPanelView.setVisibility(View.GONE);
			userNotloginPanelView.setVisibility(View.VISIBLE);
		}
	}

	@Event(value = { R.id.frame3OrdersMyBtn, R.id.frame3OrdersNeedMeBtn,
			R.id.frame3OrdersHistoryBtn })
	private void MainBtnClickListener(View v) {
		// Toast.makeText(getActivity(), v.getId() + "", 0).show();
		Intent intent = new Intent();
		AlertDialog.Builder dialog = new NotLoginDialog(getActivity())
				.getDialog();
		switch (v.getId()) {
		case R.id.frame3OrdersMyBtn:
			if (CONST.userInfo == null) {
				dialog.show();
			} else {
				intent.setClass(getActivity(), UserOrdersMy.class);
				startActivity(intent);
			}
			break;
		case R.id.frame3OrdersNeedMeBtn:
			if (CONST.userInfo == null) {
				dialog.show();
			} else {
				intent.setClass(getActivity(), UserOrdersNeedMe.class);
				startActivity(intent);
			}
			break;

		case R.id.frame3OrdersHistoryBtn:
			intent.setClass(getActivity(), UserOrdersHistory.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public boolean findHasLogin() {
		DbManager dbm = x.getDb(CONST.daoConfig);
		try {
			UserStore userStore = dbm.selector(UserStore.class).findFirst();
			CONST.userInfo = new Mod_UserInfo();
			CONST.userInfo.setTel(userStore.getUserId()+"");
			CONST.userInfo.setDescribe(userStore.getDescribe());
			CONST.userInfo.setPic(userStore.getPic());
			CONST.userInfo.setName(userStore.getName());
			CONST.userInfo.setToken(userStore.getToken());
			dbm.close();
			initUser();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			dbm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void initUser() {
		// 通过已有的本地数据+TOKEN-->获取用户真实的网络数据
		String url = CONST.host + CONST.getUserInfo + "?" + CONST.USERID_STRING
				+ "=" + CONST.userInfo.getTel() + "&" + CONST.TOKEN_STRING + "="
				+ CONST.userInfo.getToken();
		Log.v("OUT", url);
		RequestParams requestParams = new RequestParams(url);
		x.http().get(requestParams, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {
			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {
			}

			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Log.e("DEBUG2", arg0);
				Gson gson = new GsonBuilder().setDateFormat(
						"yyyy-MM-dd HH:mm:ss").create();
				ResponseObject result = gson.fromJson(arg0,
						new TypeToken<ResponseObject<Mod_UserInfo>>() {
						}.getType());
				userInfo = (Mod_UserInfo) result.getObject();
				CONST.userInfo = userInfo;
				
				x.image().bind(userheadiconImageView, CONST.host + userInfo.getPic(), MyAndUtils.getImageOption(), MyAndUtils.getCacheCallBack());
				userNameTextView.setText(userInfo.getName());
				userDescribeTextView.setText(userInfo.getDescribe());
				UserStore userStore = new UserStore();
				userStore.setId(1);
				userStore.setName(CONST.userInfo.getName());
				userStore.setUserId(Long.parseLong(CONST.userInfo.getTel()));
				userStore.setToken(CONST.userInfo.getToken());
				userStore.setDescribe(CONST.userInfo.getDescribe());
				userStore.setPic(CONST.userInfo.getPic());
				DbManager dbm = x.getDb(CONST.daoConfig);
				try {
					dbm.save(userStore);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
