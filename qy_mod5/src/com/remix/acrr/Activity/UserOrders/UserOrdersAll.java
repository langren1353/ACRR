package com.remix.acrr.Activity.UserOrders;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyAndUtils;

public class UserOrdersAll extends Activity {

	@ViewInject(R.id.UserOrdersAllBack)
	ImageView							UserOrdersAllBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orders_all);
		x.view().inject(this);
		UserOrdersAllBackBtn.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
	}

}
