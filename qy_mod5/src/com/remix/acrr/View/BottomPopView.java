package com.remix.acrr.View;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.remix.acrr.R;

public abstract class BottomPopView {
    public Activity context;
    private View anchor;
    private LayoutInflater mInflater;
    private TextView mTvTop;
    private TextView mTvBottom;
    private TextView mTvCancel;
    private PopupWindow mPopupWindow;
    private int gravity;
    LayoutParams params;
    WindowManager windowManager;
    Window window;
 
    /**
     * @param context
     * @param anchor  依附在哪个View下面
     */
    public BottomPopView(Activity context, View anchor) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.anchor = anchor;
        windowManager = context.getWindowManager();
        window = context.getWindow();
        params = context.getWindow().getAttributes();
        setGravity(Gravity.BOTTOM);
        init();
    }
 
    public void init() {
        View view = mInflater.inflate(R.layout.dialog_bottompopview, null);
        params.dimAmount = 0.5f;
        window.addFlags(LayoutParams.FLAG_DIM_BEHIND);
       
        mTvTop = (TextView) view.findViewById(R.id.dialogNormal1);
        mTvBottom = (TextView) view.findViewById(R.id.dialogNormal2);
        mTvCancel = (TextView) view.findViewById(R.id.dialogNormal3);
        mTvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onTopButtonClick();
            }
        });
        mTvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	onBottomButtonClick();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
 
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //监听PopupWindow的dismiss，当dismiss时屏幕恢复亮度
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                window.setAttributes(params);
            }
        });
        mPopupWindow.setWidth(LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
    }
 
    /**
     * 显示底部对话框
     */
    public void show() {
        mPopupWindow.showAtLocation(anchor, this.gravity, 0, 0);
        params.alpha = 0.5f;
        window.setAttributes(params);
    }
 
    /**
     * 第一个按钮被点击的回调
     */
    public abstract void onTopButtonClick();
 
    /**
     * 第二个按钮被点击的回调
     */
    public abstract void onBottomButtonClick();
 
    public void setTopText(String text) {
        mTvTop.setText(text);
    }
 
    public void setBottomText(String text) {
        mTvBottom.setText(text);
    }
    
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    
    public void setCancelVisible(int visible){
    	mTvCancel.setVisibility(visible);
    }
    public void setColorAll(int color){
    	mTvTop.setTextColor(color);
    	mTvBottom.setTextColor(color);
    	mTvCancel.setTextColor(color);
    }
    public void dismiss(){
        if(mPopupWindow!=null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }
}