package com.danqiu.myapplication.views;

import android.view.View;

/**
 * description ： 防快速点击
 * author : asus
 * date : 2020/10/26
 */
public abstract  class OnFastClickListener implements View.OnClickListener {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    public abstract void onFastClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
        if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onFastClick(v);
        }
    }
}
