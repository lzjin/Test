package com.danqiu.myapplication.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2020/3/24
 * Describe ：动画框架测试
 */
public class LottieAcitivity extends AppCompatActivity {
    @BindView(R.id.lottieView)
    LottieAnimationView lottieView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ButterKnife.bind(this);

        initLottie();
    }

    private void initLottie() {

        lottieView.setAnimation("LottieLogo1.json");//在assets目录下的动画json文件名。
        lottieView.setRepeatCount(0);//设置动画循环播放
        lottieView.playAnimation();//开始播放动画
        lottieView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                MLog.i("test","----------onAnimationStart----");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MLog.i("test","----------onAnimationEnd----");
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                MLog.i("test","----------onAnimationCancel----");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                MLog.i("test","----------onAnimationRepeat----");
            }
        });


    }
}
