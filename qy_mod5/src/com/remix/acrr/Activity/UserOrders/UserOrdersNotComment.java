package com.remix.acrr.Activity.UserOrders;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyAndUtils;

public class UserOrdersNotComment extends Activity {

	@ViewInject(R.id.UserOrdersNotCommentBack)
	ImageView							UserOrdersNotCommentBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orders_not_comment);
		x.view().inject(this);
		UserOrdersNotCommentBackBtn.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
	}
}
