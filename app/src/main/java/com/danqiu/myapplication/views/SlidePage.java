package com.danqiu.myapplication.views;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lzj on 2019/10/18
 * Describe ：注释
 */
public class SlidePage extends FrameLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private GestureDetectorCompat mGestureDetector;


    public SlidePage(Context context) {
        super(context);
        init(context);
    }

    public SlidePage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidePage(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        mGestureDetector = new GestureDetectorCompat(context, this);
        setOnTouchListener(this);
        Log.i("test","-------------init");
    }

    //---------------------------------------------------------------
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean detectedUp = event.getAction() == MotionEvent.ACTION_UP;
        if (!mGestureDetector.onTouchEvent(event) && detectedUp) {
            Log.i("test","ACTION UP");
        }
        return true;
    }
    /**
     * 用户按下屏幕的时候的回调
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i("test","-------------按下");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {

        float x = e2.getX() - e1.getX();

        if(x>20){
            Log.i("test","-------------右滑动");
        }
        else if(x<-20){
            Log.i("test","-------------左滑动");
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }


}
