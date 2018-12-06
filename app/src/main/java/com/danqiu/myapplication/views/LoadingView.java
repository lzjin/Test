package com.danqiu.myapplication.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.danqiu.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by Administrator on 2018/12/4.
 *  自定义组合控件
 *  通过缩放三小球，实现加载动画
 */

public class LoadingView extends LinearLayout{
    private Timer mTimer=null;//定时器
    private MyTimerTask mTimerTask=null;
    private Context mContext;
    private View mView;
    private LinearLayout mLayout;
    private CircleView mCircleViewI,mCircleViewJ,mCircleViewK;
    ScaleAnimation scaleAnimationi,scaleAnimationj,scaleAnimationk;
   // AnimationSet animationSet = new AnimationSet(true);
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1001:
                    //AlphaAnimation alphaAnimation=new AlphaAnimation(1.0F,0.7f);
                    // alphaAnimation.setDuration(1000);
                    // animationSet.addAnimation("添加动画");//动画集合,同时使用多种动画
                    // mCircleViewI.startAnimation(animationSet);
                    //第一个球
                    scaleAnimationi=new ScaleAnimation(1.0F,0.5F,1.0F,0.5F,Animation.RELATIVE_TO_SELF ,0.0F,Animation.RELATIVE_TO_SELF ,0.0F );
                    scaleAnimationi.setDuration(1000);
                    mCircleViewI.startAnimation(scaleAnimationi);
                    //第二个球
                    scaleAnimationj=new ScaleAnimation(0.85F,0.6F,0.85F,0.6F,RELATIVE_TO_SELF,0.0F,RELATIVE_TO_SELF ,0.0F );
                    scaleAnimationj.setDuration(900);
                    mCircleViewJ.startAnimation(scaleAnimationj);
                    //第三个球
                    scaleAnimationk=new ScaleAnimation(0.5F,1.0F,0.5F,1.0F,RELATIVE_TO_SELF,0.0F,RELATIVE_TO_SELF ,0.0F );
                    scaleAnimationk.setDuration(1000);
                    mCircleViewK.startAnimation(scaleAnimationk);
                    break;
            }


        }
    };
    public LoadingView(Context context) {
        super(context);
    }
    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mView=LayoutInflater.from(mContext).inflate(R.layout.view_loading,null);
        mLayout=mView.findViewById(R.id.id_view);
        mCircleViewI=mView.findViewById(R.id.circle_i);
        mCircleViewJ=mView.findViewById(R.id.circle_j);
        mCircleViewK=mView.findViewById(R.id.circle_k);


        //LinearLayout.LayoutParams iParams=new LinearLayout.LayoutParams(dip2px(30), dip2px(30));
        //mCircleViewI.setLayoutParams(iParams);
        //mCircleViewI.setRadius(6);

        //布局容器设置属性LayoutParams
        LinearLayout.LayoutParams linearParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        mLayout.setLayoutParams(linearParams);

        this.addView(mView);
        addStartTimer(1);
    }




   /**
     * 开启定时器
     * @param time
     */
    public void addStartTimer(int time) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if(mTimerTask==null){
            mTimerTask=new MyTimerTask();
        }
        mTimer.schedule(mTimerTask, 0, 1000*time);
    }

    /**
     * 暂停
     */
    public void stopTimer(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer = null;
        }
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask = null;
        }

    }
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1001);
        }
    }

}
