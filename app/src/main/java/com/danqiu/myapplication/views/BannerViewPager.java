package com.danqiu.myapplication.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyPager3DAdapter;
import com.danqiu.myapplication.utils.MLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/11/26.
 *
 */

public class BannerViewPager extends RelativeLayout implements View.OnTouchListener,ViewPager.OnPageChangeListener{
    private View mLayout;//布局
    private Activity mContext;//上下文
    private ViewPager mViewPager;
    private MyPager3DAdapter mPagerAdapter;
    private RelativeLayout mRelGroup;
    private LinearLayout mLineIndicator;
    private ImageView[] mImageView;//存放viewpager底部圆点imageview对象
    private List<String> urlList;
    private long secondTime=0,firstTime=0;
    private int currentIndex = 0;//当前page
    private int startCurrentIndex = 200;//当前page
    private Timer mTimer=null;//定时器
    private MyTimerTask mTimerTask=null;
    private int resId_piont_press=R.mipmap.ic_banner_point_press;
    private int resId_piont=R.mipmap.ic_banner_point;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            int index =  mViewPager.getCurrentItem()+1;//下一个页
            mViewPager.setCurrentItem(index);//设置此次要显示的pager
            currentIndex=index%urlList.size();
            setImageBackground(currentIndex);
        }
    };


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

        currentIndex=startCurrentIndex%urlList.size();

        mPagerAdapter = new MyPager3DAdapter(urlList,mContext);

        mLayout = LayoutInflater.from(mContext).inflate( R.layout.banner_view, null);
        mViewPager  = (ViewPager) mLayout.findViewById(R.id.viewPager);//关闭
        mRelGroup  = (RelativeLayout) mLayout.findViewById(R.id.rel_group);
        mLineIndicator  = (LinearLayout) mLayout.findViewById(R.id.relIndicator);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true,new RotationPageTransformer());

        setPageMargin(20,60);
        mViewPager.setCurrentItem(startCurrentIndex);
        mViewPager.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);

        initPoint();
    }

    /**
     * 初始化指示器
     */
    private void initPoint() {
            mImageView = new ImageView[urlList.size()];
        for (int i = 0; i < urlList.size(); i++) {
             ImageView imageView=new ImageView(mContext);
             imageView.setPadding(dip2px(4),0,dip2px(4),0);

             LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
             params.setMargins(dip2px(5), 0, dip2px(5), 0);
             imageView.setLayoutParams(params);
             if(i==currentIndex){
                 imageView.setImageResource(resId_piont_press);
             }
             else {
                 imageView.setImageResource(resId_piont);
             }
             mImageView[i]=imageView;
             mLineIndicator.addView(imageView);
        }

        //通过定时器，制作自动划动效果
        //startTimer(5);
    }



    /**
     * 开启定时器
     * @param time
     */
    public void startTimer(int time) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if(mTimerTask==null){
            mTimerTask=new MyTimerTask();
        }
        mTimer.schedule(mTimerTask, 3000, 1000*time);
    }

    public void stopTimer(){
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }
     class MyTimerTask extends TimerTask{
         @Override
         public void run() {
             mHandler.sendEmptyMessage(1001);//在此线程中，不能操作ui主线程
         }
     }

    /**
     *
     * @param columnMargin 两个Page之间的距离
     * @param rowMargin  page的外边距
     */
    private void setPageMargin(int columnMargin,int rowMargin) {

        mViewPager.setPageMargin(dip2px(columnMargin));


        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                                                  RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(dip2px(rowMargin), 0, dip2px(rowMargin), 0);
        mViewPager.setLayoutParams(layout);

    }

    /**
     * 点击Page事件
     *
     * 由于部分机型始终会执行ACTION_MOVE
     * 因此采用的间隔时间来判断是否为点击事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstTime = System.currentTimeMillis();
                MLog.i("test","--------ACTION_DOWN");
                break ;
            case MotionEvent.ACTION_MOVE:
                MLog.i("test","--------ACTION_MOVE");
                break ;
            case  MotionEvent.ACTION_UP :
                MLog.i("test","--------ACTION_UP");
                secondTime = System.currentTimeMillis();
                MLog.i("test","--------"+(secondTime - firstTime));
                if (secondTime - firstTime < 100) {
                    Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
                }
                break ;
        }
        return false;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public  int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex=position%urlList.size();
        setImageBackground(currentIndex);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 滑动时，改变指示器
     * @param selectItemsIndex
     */
    private void setImageBackground(int selectItemsIndex) {
        for (int i = 0; i < mImageView.length; i++) {
            if (i == selectItemsIndex) {
                mImageView[i].setImageResource(resId_piont_press);

            } else {
                mImageView[i].setImageResource(resId_piont);
            }
        }
    }
}
