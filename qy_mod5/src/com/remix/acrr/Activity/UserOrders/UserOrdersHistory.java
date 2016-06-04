package com.remix.acrr.Activity.UserOrders;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.remix.acrr.R;
import com.remix.acrr.Tools.MyAndUtils;

public class UserOrdersHistory extends Activity {

	@ViewInject(R.id.UserOrdersHistoryBack)
	ImageView							UserOrdersHistoryBackBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_orders_history);
		x.view().inject(this);
		UserOrdersHistoryBackBtn.setOnClickListener(new MyAndUtils.MyFinishClickListener(this));
	}

}
