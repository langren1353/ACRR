package com.xxxxx.ac.Activity;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxxxx.ac.R;
import com.xxxxx.ac.MOD.baidu.Mod_Content;
import com.xxxxx.ac.MOD.baidu.Mod_News;
import com.xxxxx.ac.Tools.CommonUtils;

public class NewsDetailActivity extends FragmentActivity {
	@ViewInject(R.id.News_body)
	LinearLayout 	News_bodyLayout;
	@ViewInject(R.id.news_from)
	TextView		fromTextView;
	@ViewInject(R.id.news_title)
	TextView		titleTextView;
	@ViewInject(R.id.news_title_left)
	ImageView		buttonLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.frame2_news_info);
		x.view().inject(this);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		Mod_News news = (Mod_News) bundle.getSerializable("key");
		if (news != null) {
			fromTextView.setText(news.getSite());
			titleTextView.setText(news.getTitle());
			ArrayList<Mod_Content> contents = news.getContent();
			for (int i = 0; i < contents.size(); i++) {
				if (contents.get(i).getType().equals("text")) {
					try {
						TextView textView = new TextView(this);
						DisplayMetrics dm = new DisplayMetrics();
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						layoutParams.topMargin = 20;
						textView.setText(Html.fromHtml("&nbsp;&nbsp;&nbsp;&nbsp;" + contents.get(i).getData()));
						textView.setTextSize(16);

						textView.setLayoutParams(layoutParams);
						News_bodyLayout.addView(textView);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						ImageView imageView = new ImageView(this);
						DisplayMetrics dm = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(dm);
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT, contents.get(i).getImage().getHeight());
						layoutParams.topMargin = 20;
						imageView.setLayoutParams(layoutParams);
						x.image().bind(imageView, contents.get(i).getImage().getUrl());
						News_bodyLayout.addView(imageView);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
		buttonLeft.setOnClickListener(new CommonUtils.MyFinishClickListener(this));
	}

}
