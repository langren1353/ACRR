package com.remix.acrr.frame;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.remix.acrr.R;
import com.remix.acrr.Activity.User.UserInfoActivity;
import com.remix.acrr.Activity.User.UserLoginActivity;
import com.remix.acrr.Activity.UserOrders.UserOrdersAll;
import com.remix.acrr.Activity.UserOrders.UserOrdersCollection;
import com.remix.acrr.Activity.UserOrders.UserOrdersHistory;
import com.remix.acrr.Activity.UserOrders.UserOrdersNotComment;
import com.remix.acrr.MOD.CONST;
import com.remix.acrr.MOD.Mod_UserInfo;
import com.remix.acrr.Tools.MyUtils;
import com.remix.acrr.Tools.SelectPicUtil;
import com.remix.acrr.View.BottomPopView;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frame3, null);
		x.view().inject(this, view);
		Login_IsSuccess(false);
		userheadiconImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳转到上传头像
				BottomPopView bottomPopView = new BottomPopView(getActivity(), v) {
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
				startActivity(intent);
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
			this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Bitmap bm = SelectPicUtil.onActivityResult(getActivity(), requestCode, resultCode, intent);
		if (bm != null)
			userheadiconImageView.setImageBitmap(bm);
		if (resultCode == 100) {
			try {
				Bundle bundle = intent.getExtras();
				Mod_UserInfo userInfo = (Mod_UserInfo) bundle.getSerializable("UserInfo");
				Login_IsSuccess(true);
				userNameTextView.setText(userInfo.getName());
				userDescribeTextView.setText(userInfo.getDescribe());
				String url = userInfo.getPic();
				if (!(url == null || url.equals(""))) {
					x.image().bind(userheadiconImageView, CONST.host + url, MyUtils.getImageOption());
				}
			} catch (Exception e) {
				Log.e("DEBUG2", "没有数据传递====未登录");
			}
		}
	}

	public void Login_IsSuccess(boolean isSuccess) {
		if (isSuccess == true) {
			userPanelView.setVisibility(View.VISIBLE);
			userNotloginPanelView.setVisibility(View.GONE);
		} else {
			userPanelView.setVisibility(View.GONE);
			userNotloginPanelView.setVisibility(View.VISIBLE);
		}
	}

	@Event(value = { R.id.frame3OrderCollectionBtn, R.id.frame3OrdersALlBtn, R.id.frame3OrdersHistoryBtn, R.id.frame3OrdersNotCommentBtn })
	private void MainBtnClickListener(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.frame3OrderCollectionBtn:
			intent.setClass(getActivity(), UserOrdersCollection.class);
			startActivity(intent);
			break;
		case R.id.frame3OrdersALlBtn:
			intent.setClass(getActivity(), UserOrdersAll.class);
			startActivity(intent);
			break;
		case R.id.frame3OrdersHistoryBtn:
			intent.setClass(getActivity(), UserOrdersHistory.class);
			startActivity(intent);
			break;
		case R.id.frame3OrdersNotCommentBtn:
			intent.setClass(getActivity(), UserOrdersNotComment.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
