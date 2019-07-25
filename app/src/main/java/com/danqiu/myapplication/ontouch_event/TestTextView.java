package com.danqiu.myapplication.ontouch_event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * Created by lzj on 2019/7/4
 * Describe ：注释
 */
@SuppressLint("AppCompatCustomView")
public class TestTextView extends TextView {


    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("test","-------33333------dispatchTouchEvent " );
        return super.dispatchTouchEvent(event);
    }
    float y = 0,y2;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y=event.getY();
                Log.i("test","-------33333------onTouchEvent ---按下" );
                break;
            case MotionEvent.ACTION_UP:
                Log.i("test","-------33333------onTouchEvent ---抬起" );
                break;
            case MotionEvent.ACTION_MOVE:
                y2=event.getY();
                if(y2-y>0){
                 //   Log.i("test","-------TestTextView------onTouchEvent ---下-----移动" );
                }
                else {
                  //  Log.i("test","-------TestTextView------onTouchEvent ---上-----移动" );
                }
                break;
        }
         return super.onTouchEvent(event);
        //return true;
    }
}
