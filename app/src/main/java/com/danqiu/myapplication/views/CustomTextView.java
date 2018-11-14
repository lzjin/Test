package com.danqiu.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.danqiu.myapplication.R;

/**
 * Created by Administrator on 2018/11/14.
 *  自定义view 图片文字
 */

public class CustomTextView extends View{
    public Bitmap mBgbitmap;
    public int mTextSize=16;
    public int mTextColor=Color.WHITE;
    public String mText="";
    /**
     * 使用方式
     *  CustomTextView=new CustomTextView(this)
     * @param context
     */
    public CustomTextView(Context context) {
        super(context);
    }

    /**
     * 使用方式
     * 布局使用 <com.danqiu.myapplication.views.CustomTextView.....
     * attrs中使用自定属性
     * @param context
     * @param attrs
     */
    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ImgTextView);
        mTextSize=typedArray.getInteger(R.styleable.ImgTextView_jtextSize,16);
        mTextColor=typedArray.getColor(R.styleable.ImgTextView_jtextColor, Color.BLACK);
        mText=typedArray.getString(R.styleable.ImgTextView_jtext);
       // mBgbitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_text_bg);

        typedArray.recycle();
    }

    /**
     * 使用方式
     * 当有style自定义样式时候使用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initView(canvas);
    }

    private void initView(Canvas canvas) {
        Paint paint=new Paint();


        Rect bounds  = new Rect();
        paint.getTextBounds(mText, 0, mText.length(), bounds );

        paint.setTextAlign(Paint.Align.LEFT);

        paint.setTextSize(sp2px(mTextSize));//自定义属性
        paint.setColor(mTextColor);//自定义属性
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText(mText,  getMeasuredWidth()/2 - bounds.width()/2, getMeasuredHeight()/2 + bounds.height()/2, paint);


//
//
//        Rect mSrcRec = new Rect(0, 0, mBitWidth, mBitHeight);
//        RectF mSrcRect = new RectF(0, 0, mBitWidth, mBitHeight);
//        //bitmap对象，Bitmap对象的矩形区域，bitmap绘制在屏幕的什么地方，Paint对象。
//        canvas.drawBitmap(mBgbitmap,mSrcRec,mSrcRect, paint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽高模式
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
       // int heigthMode=MeasureSpec.getMode(heightMeasureSpec);
        //自适应
        if(widthMode==MeasureSpec.AT_MOST){

        }
        //布局中写死100dp
        if(widthMode==MeasureSpec.EXACTLY){

        }
        //尽可能大，极少用
        if(widthMode==MeasureSpec.UNSPECIFIED){

        }


    }

    /**
     * 文字sp转px
     * @param spVal
     * @return
     */
    public int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

}
