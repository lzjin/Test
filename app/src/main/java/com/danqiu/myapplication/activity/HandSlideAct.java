package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.danqiu.myapplication.MainActivity;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.LogUtil;
import com.danqiu.myapplication.utils.ToastUtil;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/9/19.
 * 根据手滑动 移动控件
 * GestureDetectorCompat
 */

public class HandSlideAct extends BaseActivity implements View.OnTouchListener {


    @BindView(R.id.view_opt)
    ImageView viewOpt;
    @BindView(R.id.bt_end)
    Button btEnd;
    @BindView(R.id.bt_find)
    Button btFind;
    private GestureDetectorCompat mDetector;
    private int viewHeight, sbar;
    private int viewWidth;
    private int mTop, mLeft;
    private int startX, startY;


    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_slide);
        ButterKnife.bind(this);

        getStatusBarHeight();
        initData();

        viewOpt.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                viewHeight = viewOpt.getMeasuredHeight();
                viewWidth = viewOpt.getMeasuredWidth();
                return true;
            }
        });


    }

    public void initData() {
        viewOpt.setOnTouchListener(this);
    }

    //用来处理事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // LogUtil.e(TAG,"------------------------MainAct--onTouchEvent按下--");
                break;
            case MotionEvent.ACTION_UP:
                // LogUtil.e(TAG,"------------------------MainAct--onTouchEvent抬起--");
                break;
            case MotionEvent.ACTION_MOVE:
                // LogUtil.e(TAG,"------------------------MainAct--onTouchEvent移动--");

                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 分发事件
     * flase 不走onTouchEvent
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.e(TAG, "------------------------MainAct--dispatchTouchEvent--");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTop = viewOpt.getTop();
                mLeft = viewOpt.getLeft();
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:

                int MoveX = (int) event.getRawX();
                int MoveY = (int) event.getRawY();
                int dx = MoveX - startX;
                int dy = MoveY - startY;
                setViewPosition(dx, dy);
                LogUtil.e(TAG, "------------------------onTouch--移动X--" + startX);
                LogUtil.e(TAG, "------------------------onTouch--移动Y--" + startY);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e(TAG, "------------------------onTouch--抬起--");
                mTop = viewOpt.getTop();
                mLeft = viewOpt.getLeft();


                break;
        }
        return true;
    }

    /**
     * 设置控件坐标
     *
     * @param x
     * @param y
     */
    private void setViewPosition(int x, int y) {
        //坐标X-----------------------------
        int setX = mLeft + x;
        int setY = mTop + y;

        if (setX < 0) {
            setX = 0;
        } else if (setX > getScreenWidth() - viewWidth) {
            setX = getScreenWidth() - viewWidth;
        }

        //坐标Y------------------------------
        if (setY < 0) {
            setY = 0;
        } else if (setY > getScreenHeight() - viewWidth - sbar) {
            setY = getScreenHeight() - viewWidth - sbar;
        }
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(viewOpt.getLayoutParams());
        margin.setMargins(setX, setY, 0, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
        viewOpt.setLayoutParams(layoutParams);
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    private void getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 获取屏幕的宽度
     */
    private int getScreenWidth() {
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        return defaultDisplay.getWidth();
    }

    /**
     * 获取屏幕的高度
     */
    private int getScreenHeight() {
        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        return defaultDisplay.getHeight();
    }


    /**
     * px转dp
     */
    private int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }





    @OnClick({R.id.bt_end, R.id.bt_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_end:
                //ActivityUtils.finishOtherActivities(MainActivity.class);
                ActivityUtils.finishActivity(MainActivity.class);
                break;
            case R.id.bt_find:
                boolean is=ActivityUtils.isActivityExistsInStack(MainActivity.class);
                ToastUtil.showLong(this,"是否"+is);
                //AppUtils.installApp();
                break;
        }
    }
}
