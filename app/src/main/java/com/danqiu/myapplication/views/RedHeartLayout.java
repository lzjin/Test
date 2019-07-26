package com.danqiu.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.danqiu.myapplication.R;

/**
 * Created by lzj on 2019/7/26
 * Describe ：仿抖音红心自定义布局
 */
public class RedHeartLayout extends RelativeLayout {

    private Context mContext;
    float[] num = {-30, -20, 0, 20, 30};//随机心形图片角度
    private int mWidth=300;
    private int mHeight=300;

    public RedHeartLayout(Context context) {
        super(context);
        initView(context);
    }

    public RedHeartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RedHeartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        removeAllViews();
        //第一步,创建ImageView
        ImageView imageView=new ImageView(mContext);
        imageView.setImageResource(R.mipmap.ic_red);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(mWidth, mHeight);
        int xLeft=(int)event.getX()- mWidth/2;
        int yTop=(int)event.getY() - mHeight/2;
        layoutParams.setMargins(xLeft,yTop,0,0);
        imageView.setLayoutParams(layoutParams);
        addView(imageView);

        //第二步,添加动画效果





        return super.onTouchEvent(event);
    }
}
