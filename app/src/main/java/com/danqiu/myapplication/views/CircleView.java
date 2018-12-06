package com.danqiu.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/12/4.
 * 绘制小圆球
 */

public class CircleView extends View{
    private Context mContext;
    private int radius=6;
    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //实例化画笔对象
        Paint paint = new Paint();
        // 给画笔设置颜色
        paint.setColor(Color.BLACK);
       // 设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
         /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
           */

        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        canvas.drawCircle(dip2px(radius), dip2px(radius), dip2px(radius), paint);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public  int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
