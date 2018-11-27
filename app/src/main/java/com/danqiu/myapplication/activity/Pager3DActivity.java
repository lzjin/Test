package com.danqiu.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyPager3DAdapter;
import com.danqiu.myapplication.utils.IntentUtil;
import com.danqiu.myapplication.views.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 * 3D轮播画廊
 */

public class Pager3DActivity extends AppCompatActivity {
    boolean mIsChanged;
    int currentIndex=1;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.viewpager2)
    BannerViewPager mViewPager2;
    private RelativeLayout mRelativeLayout;
    private MyPager3DAdapter mPagerAdapter;
    private List<String> urlList;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);

        urlList=new ArrayList<>();


        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543221773&di=c63f30c7809e518cafbff961bcd9ec2a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0116605851154fa8012060c8587ca1.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042541&di=3ad9deeefff266e76d1f5d57a58f63d1&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=95bd41d43c335e74863d9bb540361906&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019a0558be22d6a801219c77d0578a.jpg%402o.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=cdd54bffd2aac448c70ae6b416a004d4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");
        urlList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1044771406,1640828817&fm=11&gp=0.jpg");
        //在首尾分别加入最后一张图和第一张图


        mPagerAdapter = new MyPager3DAdapter(urlList,this);
        mViewPager.setAdapter(mPagerAdapter);
       // mViewPager.setPageTransformer(true,new RotationPageTransformer());
        mViewPager.setPageMargin(10);//设置两个Page之间的距离
        mViewPager.setCurrentItem(200);
        mViewPager.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            long secondTime=0,firstTime=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        firstTime = System.currentTimeMillis();
                        break ;
                    case MotionEvent.ACTION_MOVE:
                        break ;
                    case  MotionEvent.ACTION_UP :
                        secondTime = System.currentTimeMillis();
                        if (secondTime - firstTime < 100) {
                            Toast.makeText(Pager3DActivity.this, "点击", Toast.LENGTH_SHORT).show();
                            IntentUtil.IntenToActivity(Pager3DActivity.this,CardNoAct.class);
                        }
                        break ;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewPager2.stopTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewPager2.startTimer(6);
    }
}
