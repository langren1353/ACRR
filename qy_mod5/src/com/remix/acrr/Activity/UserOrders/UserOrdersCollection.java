package com.remix.acrr.Activity.UserOrders;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyAndUtils;

public class UserOrdersCollection extends Activity {

	@ViewInject(R.id.UserOrdersCollectionBack)
	ImageView							UserOrdersCollectionBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orders_collection);
		x.view().inject(this);
		UserOrdersCollectionBackBtn.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
	}
}
