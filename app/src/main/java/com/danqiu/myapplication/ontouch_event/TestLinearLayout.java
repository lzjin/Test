package com.danqiu.myapplication.ontouch_event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by lzj on 2019/7/4
 * Describe ：注释
 */
public class TestLinearLayout extends LinearLayout {
    public TestLinearLayout(Context context) {
        super(context);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test","-------22222-----dispatchTouchEvent"  );
        return super.dispatchTouchEvent(ev);
    }

    boolean onIntercept;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //Log.i("test","-------22222-----onInterceptTouchEvent"  );
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getY();
                y2=0;
                onIntercept=super.onInterceptTouchEvent(event);
                Log.i("test","-------22222------onInterceptTouchEvent ---按下" );
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test","-------22222------onInterceptTouchEvent ---抬起" );
                break;
            case MotionEvent.ACTION_MOVE:
                y2=event.getY();
                if(y2-y>0){
                    isTouch=true;
                    Log.i("test","-------22222------onInterceptTouchEvent ---下-----移动" );
                }
                else {
                    isTouch=super.onInterceptTouchEvent(event);
                    Log.i("test","-------22222------onInterceptTouchEvent ---上-----移动" );
                }
                break;
        }
        return onIntercept;
    }


    float y = 0,y2;boolean isTouch=true;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getY();
                y2=0;
                isTouch=super.onTouchEvent(event);
                Log.i("test","-------22222------onTouchEvent ---按下" );
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test","-------22222------onTouchEvent ---抬起" );
                break;
            case MotionEvent.ACTION_MOVE:
                y2=event.getY();
                if(y2-y>0){
                    isTouch=true;
                    Log.i("test","-------22222------onTouchEvent ---下-----移动" );
                }
                else {
                    isTouch=super.onTouchEvent(event);
                    Log.i("test","-------22222------onTouchEvent ---上-----移动" );
                }
                break;
        }
        return isTouch;
    }
}
