package com.danqiu.myapplication.ontouch_event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2019/7/4
 * Describe ：滑动冲突
 */
public class TouchActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();
    @BindView(R.id.test_1)
    TestRelativeLayout test_1;

    @BindView(R.id.test_2)
    TestLinearLayout test_2;

    @BindView(R.id.test_3)
    TestTextView test_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        ButterKnife.bind(this);
    }

//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.i("test","-------Act------dispatchTouchEvent----按下");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i("test","-------Act------dispatchTouchEvent----抬起");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //Log.i("MainActivity","-------Act------dispatchTouchEvent----移动");
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.i("test","-------Act------onTouchEvent----按下");
//                //break;
//            case MotionEvent.ACTION_UP:
//                Log.i("test","-------Act------onTouchEvent----抬起");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //Log.i("MainActivity","-------Act------dispatchTouchEvent----移动");
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}
