package com.danqiu.myapplication.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danqiu.myapplication.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by Administrator on 2018/11/9.
 */

public class MyRefreshFooter extends LinearLayout implements RefreshFooter {

    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";
    public static String REFRESH_FOOTER_ALLLOADED = "没有更多数据了";

    private View mViewFooter;
    private TextView mTitleText;
    protected boolean mNoMoreData = false;


    public MyRefreshFooter(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public MyRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    /**
     * 初始化界面布局
     * @param context
     */
    private void initView(Context context) {
        mViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
        mTitleText=mViewFooter.findViewById(R.id.pull_to_refresh_loadmore_text);
        mTitleText.setText(REFRESH_FOOTER_LOADING);
        addView(mViewFooter);
    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }
    /**
     * 获取变换方式 {@link SpinnerStyle} 必须返回 非空
     * @return 变换方式
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }
    /**
     * 设置主题颜色
     * @param colors 对应Xml中配置的 srlPrimaryColor srlAccentColor
     */
    @Override
    public void setPrimaryColors(int... colors) {

    }
    /**
     * 尺寸定义完成 （如果高度不改变（代码修改：setHeader），只调用一次, 在RefreshLayout#onMeasure中调用）
     * @param kernel RefreshKernel
     * @param height HeaderHeight or FooterHeight
     * @param extendHeight extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }
    /**
     * 手指拖动下拉（会连续多次调用）
     * @param percent 下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+extendHeight) / footerHeight )
     * @param offset 下拉的像素偏移量  0 - offset - (footerHeight+extendHeight)
     * @param height 高度 HeaderHeight or FooterHeight
     * @param extendHeight 扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        mTitleText.setText(REFRESH_FOOTER_LOADING);
    }
    /**
     * 手指释放之后的持续动画（会连续多次调用）
     * @param percent 下拉的百分比 值 = offset/footerHeight (0 - percent - (footerHeight+extendHeight) / footerHeight )
     * @param offset 下拉的像素偏移量  0 - offset - (footerHeight+extendHeight)
     * @param height 高度 HeaderHeight or FooterHeight
     * @param extendHeight 扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    /**
     * 释放时刻（调用一次，将会触发加载）
     * @param refreshLayout RefreshLayout
     * @param height 高度 HeaderHeight or FooterHeight
     * @param extendHeight 扩展高度  extendHeaderHeight or extendFooterHeight
     */
    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
       // mProgressDrawable.start();//开始动画
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        if (success){
//            mTitleText.setText(REFRESH_FOOTER_FINISH);
//        } else {
//            mTitleText.setText(REFRESH_FOOTER_FAILED);
//        }
//        return 500;//延迟500毫秒之后再弹回底部
        return 0;
    }
    /**
     * 水平方向的拖动
     * @param percentX 下拉时，手指水平坐标对屏幕的占比（0 - percentX - 1）
     * @param offsetX 下拉时，手指水平坐标对屏幕的偏移（0 - offsetX - LayoutWidth）
     * @param offsetMax 最大的偏移量
     */
    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }
    /**
     * 是否支持水平方向的拖动（将会影响到onHorizontalDrag的调用）
     * @return 水平拖动需要消耗更多的时间和资源，所以如果不支持请返回false
     */
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        mNoMoreData=noMoreData;
        return true;
    }
}
