package com.danqiu.myapplication.views;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyPager3DAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */

public class BannerViewPager extends RelativeLayout implements View.OnTouchListener{
    private View mLayout;//布局
    private Activity mContext;//上下文
    private ViewPager mViewPager;
    private MyPager3DAdapter mPagerAdapter;

    private List<String> urlList;
    long secondTime=0,firstTime=0;

    public BannerViewPager(Context context) {
        super(context);
    }
    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
        init();
        this.addView(mLayout); //将子布局添加到父容器,才显示控件
    }

    private void init() {

        urlList=new ArrayList<>();


        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543221773&di=c63f30c7809e518cafbff961bcd9ec2a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0116605851154fa8012060c8587ca1.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042541&di=3ad9deeefff266e76d1f5d57a58f63d1&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=95bd41d43c335e74863d9bb540361906&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019a0558be22d6a801219c77d0578a.jpg%402o.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=cdd54bffd2aac448c70ae6b416a004d4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042538&di=9e5bddb59561300aa239277b81cc13c6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015c4c58c92dfda801219c77d6fdb2.jpg%402o.jpg");
        //在首尾分别加入最后一张图和第一张图


        mPagerAdapter = new MyPager3DAdapter(urlList,mContext);

        mLayout = LayoutInflater.from(mContext).inflate( R.layout.banner_view, null);
        mViewPager  = (ViewPager) mLayout.findViewById(R.id.viewPager);//关闭
        mViewPager.setAdapter(mPagerAdapter);
        // mViewPager.setPageTransformer(true,new RotationPageTransformer());
        mViewPager.setPageMargin(10);//设置两个Page之间的距离
        mViewPager.setCurrentItem(200);
        mViewPager.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
    }


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
                    Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
                }
                break ;
        }
        return false;
    }
}
