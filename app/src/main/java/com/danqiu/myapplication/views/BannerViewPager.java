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

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyPager3DAdapter;
import com.danqiu.myapplication.utils.MLog;

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
    private List<String> mList;
    private long secondTime=0,firstTime=0;
    private int currentIndex = 0;//当前page
    private int startCurrentIndex = 2000;//当前page
    private Timer mTimer=null;//定时器
    private MyTimerTask mTimerTask=null;
    private int resId_piont_press=R.mipmap.ic_banner_point_press;
    private int resId_piont=R.mipmap.ic_banner_point;
    private boolean isPoint=false;

    public static interface OnClickBannerListener {
        void onBannerClick(int position);
    }
    private OnClickBannerListener mBannerListener;

    public BannerViewPager addBannerListener(OnClickBannerListener listener) {
        mBannerListener = listener;
        return this;
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            int index =  mViewPager.getCurrentItem()+1;//下一个页
            mViewPager.setCurrentItem(index);//设置此次要显示的pager
            currentIndex=index%mList.size();
            setImageBackground(currentIndex);

        }
    };

    public BannerViewPager(Context context) {
        super(context);
    }
    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = (Activity) context;
    }
    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
    }

    /**
     * 初始化viewpager
     * @param list  url集合
     * @param isGallery 是否使用3D画廊效果
     */
    public BannerViewPager initBanner(List<String> list,boolean isGallery){
        mList=list;
        //引入布局
        mLayout = LayoutInflater.from(mContext).inflate( R.layout.banner_view, null);
        mViewPager  = (ViewPager) mLayout.findViewById(R.id.viewPager);//关闭
        mLineIndicator  = (LinearLayout) mLayout.findViewById(R.id.lineIndicator);
        //初始化位置
        currentIndex=startCurrentIndex%mList.size();

        mPagerAdapter = new MyPager3DAdapter(mList,mContext);


        mViewPager.setAdapter(mPagerAdapter);
        if(isGallery){
            mViewPager.setPageTransformer(true,new RotationPageTransformer());
        }

        mViewPager.setCurrentItem(startCurrentIndex);
        mViewPager.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        return this;
    }


    /**
     * 初始化viewpager
     * @param list  url集合
     * @param isGallery 是否使用3D画廊效果
     * @param alpha  滑动透明度变化
     */
    public BannerViewPager initBanner(List<String> list,boolean isGallery,float alpha){
        mList=list;
        //引入布局
        mLayout = LayoutInflater.from(mContext).inflate( R.layout.banner_view, null);
        mViewPager  = (ViewPager) mLayout.findViewById(R.id.viewPager);//关闭
        mLineIndicator  = (LinearLayout) mLayout.findViewById(R.id.lineIndicator);
        //初始化位置
        currentIndex=startCurrentIndex%mList.size();

        mPagerAdapter = new MyPager3DAdapter(mList,mContext);


        mViewPager.setAdapter(mPagerAdapter);
        if(isGallery){
            mViewPager.setPageTransformer(true,new RotationPageTransformer(alpha));
        }

        mViewPager.setCurrentItem(startCurrentIndex,false);
        mViewPager.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        return this;
    }
     public BannerViewPager addRoundCorners(int corners){
         mPagerAdapter.setmRoundCorners(corners);
         return this;
     }
    /**
     *
     * @param rowMargin 两个Page之间的距离
     * @param edgeMargin  中间的page距离屏幕便于间距，包括了page之间的间距
     * 注意当添加了3D画廊效果时,columnMargin尽量设小。应该本是已经进行了x、y的缩放
     */
    public BannerViewPager addPageMargin(int rowMargin,int edgeMargin) {

        mViewPager.setPageMargin(dip2px(rowMargin));
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(dip2px(edgeMargin), 0, dip2px(edgeMargin), 0);
        mViewPager.setLayoutParams(layout);
        return this;
    }

    /**
     * 初始化指示器
     */
    public BannerViewPager addPoint(int distance) {
        this.isPoint=true;
        mImageView = new ImageView[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView=new ImageView(mContext);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(dip2px(distance)/2, 0, dip2px(distance)/2, 0);
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

        return this;
    }

    /**
     * 配置完成,将布局添加到父容器
     */
    public BannerViewPager  finishConfig(){
        this.addView(mLayout);
        return this;
    }



    /**
     * 开启定时器
     * @param time
     */
    public BannerViewPager addStartTimer(int time) {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if(mTimerTask==null){
            mTimerTask=new MyTimerTask();
        }
        mTimer.schedule(mTimerTask, 3000, 1000*time);
        return  this;
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
     * 点击Page事件
     *
     * 由于部分机型始终会执行ACTION_MOVE
     * 因此采用的间隔时间来判断是否为点击事件
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
       if(mBannerListener!=null){
         switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstTime = System.currentTimeMillis();
                break ;
            case MotionEvent.ACTION_MOVE:
                break ;
            case  MotionEvent.ACTION_UP :
                secondTime = System.currentTimeMillis();
                if (secondTime - firstTime < 75) {
                    MLog.i("test","------------+"+(secondTime - firstTime));
                    mBannerListener.onBannerClick(currentIndex);
                }
                break ;
         }
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
        currentIndex=position%mList.size();
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
        if(isPoint){
           for (int i = 0; i < mImageView.length; i++) {
              if (i == selectItemsIndex) {
                  mImageView[i].setImageResource(resId_piont_press);

              } else {
                  mImageView[i].setImageResource(resId_piont);
              }
           }
        }
    }
}
