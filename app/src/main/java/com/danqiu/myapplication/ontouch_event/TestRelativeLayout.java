package com.danqiu.myapplication.ontouch_event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by lzj on 2019/7/4
 * Describe ：注释
 */
public class TestRelativeLayout extends RelativeLayout {
    public TestRelativeLayout(Context context) {
        super(context);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test","-------11111-----dispatchTouchEvent"  );
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("test","-------11111-----onInterceptTouchEvent"  );
        return super.onInterceptTouchEvent(ev);
    }

    float y = 0,y2;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getY();
                Log.i("test","-------11111------onTouchEvent ---按下" );
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test","-------11111------onTouchEvent ---抬起" );
                break;
            case MotionEvent.ACTION_MOVE:
                y2=event.getY();
                //Log.i("MainActivity","-------Act------dispatchTouchEvent----移动");
                if(y2-y>0){
                    Log.i("test","-------11111------onTouchEvent ---下-----移动" );
                }
                else {
                    Log.i("test","-------11111------onTouchEvent ---上-----移动" );
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
