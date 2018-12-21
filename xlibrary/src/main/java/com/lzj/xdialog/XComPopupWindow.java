package com.lzj.xdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2018/12/20.
 * 功能:<<==通用窗口封装==>>
 * 提醒:设置动画在showAsDropDown前调用
 *
 *  new XComPopupWindow(this, R.layout.view_xcom_dialog)
 * .init()
 * .addAnimation(R.style.commonAnimation)
 * .showAsDropDown(bt5)
 * .setListener(new XComPopupWindow.OnPopupListener() {
 * @Override
 * public void onViewClick(View popupLayout) {
 *   Button button= popupLayout.findViewById(R.id.bt_colse);
 *   button.setOnClickListener(new View.OnClickListener() {
 *    @Override
 *    public void onClick(View v) {
 *     popupWindow.dismiss();
 *    }
 *   });
 * }
 * });
 *
 */

public class XComPopupWindow {
    private Activity mContext;
    private View mLayoutView;//布局
    public PopupWindow mPopupWindow;//弹框
    private boolean isalpha = false;//是否设置遮到层
    private int popupWidth;
    private int popupHeight;

    /**
     * 布局接口
     */
    public static interface OnPopupListener {
        public void onViewClick(View popupLayout);//事件

    }

    private OnPopupListener mPopupListener;


    /**
     * 设置布局
     *
     * @param context
     * @param id_Layout
     */
    public XComPopupWindow(Activity context, int id_Layout) {
        this.mContext = context;
        this.mLayoutView = LayoutInflater.from(mContext).inflate(id_Layout, null);
    }

    /**
     * 初始化
     *
     * @return
     */
    public XComPopupWindow init() {
        mPopupWindow = new PopupWindow(mLayoutView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //测量view
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = mLayoutView.getMeasuredWidth();    //  获取测量后的宽度
        popupHeight = mLayoutView.getMeasuredHeight();  //获取测量后的高度
        addFocusable();
        return this;
    }

    /**
     * 初始化
     *
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public XComPopupWindow init(int screenWidth, int screenHeight) {
        mPopupWindow = new PopupWindow(mLayoutView, screenWidth, screenHeight, true);
        //测量view
        mLayoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = mLayoutView.getMeasuredWidth();    //  获取测量后的宽度
        popupHeight = mLayoutView.getMeasuredHeight();  //获取测量后的高度
        addFocusable();
        return this;
    }

    /**
     * 点击外部消失
     *
     * @return
     */
    private void addFocusable() {
        mPopupWindow.setFocusable(true);//设置可获取焦点
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));//必须设置否则点击消失无效
        mPopupWindow.setOutsideTouchable(true);//设置外部可触摸
    }

    /**
     * 某控件 正下方
     *
     * @param viewLocation
     * @return
     */
    public XComPopupWindow showAsDropDown(View viewLocation) {
        mPopupWindow.showAsDropDown(viewLocation);// 正下方
        addDismissListener();
        return this;
    }

    /**
     * 某控件 正下方 有偏移
     *
     * @param viewLocation
     * @param x
     * @param y
     * @return
     */
    public XComPopupWindow showAsDropDown(View viewLocation, int x, int y) {
        mPopupWindow.showAsDropDown(viewLocation, x, y);//有偏移
        addDismissListener();
        return this;
    }

    /**
     * 某控件  正上方
     *
     * @param v
     * @return 以底部作为原点坐标 向上y正，向右x正
     * 屏幕总高度 - 某控件y坐标高度(说明:location[1]的坐标参考是最左角为原点，包含了控件自身高度) +  虚拟键高度
     * <p>
     * 控件自身高度 v.getHeight()
     */
    public XComPopupWindow showAtLocation(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, getScreenHeight(mContext) - location[1] + getBotBarHeight((Activity) mContext));
        addDismissListener();
        return this;
    }

    /**
     * 添加关闭监听
     */
    private void addDismissListener() {
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (isalpha) {
                    cancelAlpha();
                }
                mPopupWindow = null;
            }
        });
    }

    /**
     * 设置动画
     *
     * @param animationStyle
     * @return
     */
    public XComPopupWindow addAnimation(int animationStyle) {
        mPopupWindow.setAnimationStyle(animationStyle);
        return this;
    }

    /**
     * 添加透明度
     *
     * @param y
     * @return
     */
    public XComPopupWindow addAlpha(float y) {
        isalpha = true;
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = y; //0.0-1.0
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 取消透明度
     *
     * @return
     */
    private XComPopupWindow cancelAlpha() {
        isalpha = false;
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 1.0f; //0.0-1.0
        mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 设置监听
     *
     * @return
     */
    public XComPopupWindow setListener(OnPopupListener listener) {
        listener.onViewClick(mLayoutView);
        return this;
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 获取底部虚拟键高度
     *
     * @param activity
     * @return
     */
    private int getBotBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = activity.getWindow().getDecorView().getHeight();
        return winHeight - rect.bottom;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
