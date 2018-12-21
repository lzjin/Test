package com.danqiu.myapplication.xframe.xdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.danqiu.myapplication.R;

/**
 * Created by Administrator on 2018/12/20.
 * <<==通用弹框封装==>>
 * 如果设置动画无效,可能 mDialog.show()先调用有影响
 *
 *  new XComDialog(this,R.layout.view_xcom_dialog)
 * .init()
 * .setWindowSize()
 * .setDialogListener(new XComDialog.OnDialogListener() {
 *  @Override
 *  public void onViewClick(View mLayoutView, final AlertDialog mDialog) {
 *  Button button= mLayoutView.findViewById(R.id.bt_colse);
 *  button.setOnClickListener(new View.OnClickListener() {
 *    @Override
 *    public void onClick(View v) {
 *       mDialog.dismiss();
 *    }
 *   });
 * }
 * });
 *
 */

public class XComDialog {
    private Activity mContext;//上下文
    private int mThemeResId;//主题
    private View mLayoutView;//布局
    private AlertDialog mDialog;//弹框
    private Window window;//窗口


    /**
     * 布局接口
     */
    public static interface OnDialogListener {
        public void onViewClick(View layoutView, AlertDialog mDialog);//监听
    }


    /**
     * 默认主题
     *
     * @param context
     * @param id_Layout
     */
    public XComDialog(Activity context, int id_Layout) {
        this.mContext = context;
        this.mThemeResId = R.style.dialogCommonTheme;
        this.mLayoutView = LayoutInflater.from(mContext).inflate(id_Layout, null);
    }

    /**
     * 自定主题
     *
     * @param context
     * @param themeResId
     * @param id_Layout
     */
    public XComDialog(Activity context, int themeResId, int id_Layout) {
        this.mContext = context;
        this.mThemeResId = themeResId;
        this.mLayoutView = LayoutInflater.from(mContext).inflate(id_Layout, null);
    }

    /**
     * 初始化Dialog
     */
    public XComDialog init() {
        mDialog = new AlertDialog.Builder(mContext, mThemeResId).create();
        mDialog.setCanceledOnTouchOutside(true);//点击外部不消失
        mDialog.setCancelable(true);//按返回键退出
        mDialog.show();

        return this;
    }

    public XComDialog init(boolean isBack, boolean isOut) {
        mDialog = new AlertDialog.Builder(mContext, mThemeResId).create();
        mDialog.setCancelable(isBack);//按返回键退出
        mDialog.setCanceledOnTouchOutside(isOut);
        mDialog.show();
        return this;
    }

    /**
     * 默认 弹框大小 透明度
     */
    public XComDialog setWindowSize() {
        mDialog.getWindow().setDimAmount(0.5f);//设置透明度
        window = mDialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setContentView(mLayoutView);//设置弹框布局

        moveTop();
        window.setGravity(Gravity.CENTER);

        return this;
    }

    /**
     * 设置 大小与透明度
     *
     * @param width
     * @param height
     * @param amount
     * @return
     */
    public XComDialog setWindowSize(int width, int height, float amount) {

        mDialog.getWindow().setDimAmount(amount);//设置透明度
        window = mDialog.getWindow();
        window.setLayout(width, height);
        window.setContentView(mLayoutView);//设置弹框布局

        return this;
    }

    /**
     * 设置弹框大小 透明度   当含listview数据时使用
     *
     * @param width
     * @param height
     * @param self   true自适应高度 ，false指定高度
     * @param amount
     * @return
     */
    public XComDialog setWindowSize(int width, int height, boolean self, float amount) {
        if (self) {
            mDialog.getWindow().setDimAmount(amount);//设置透明度
            window = mDialog.getWindow();
            window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setContentView(mLayoutView);//设置弹框布局

            return this;
        } else {
            mDialog.getWindow().setDimAmount(amount);//设置透明度
            window = mDialog.getWindow();
            window.setLayout(width, height);
            window.setContentView(mLayoutView);//设置弹框布局

            return this;
        }
    }

    /**
     * 方式 与位置
     */
    public XComDialog setGravity(int animation, int gravity) {
        window.setWindowAnimations(animation);  //添加动画
        window.setGravity(gravity);
        if (gravity == Gravity.CENTER) {
            moveTop();
        }
        return this;
    }

    /**
     * 对外监听
     */
    public XComDialog setDialogListener(OnDialogListener listener) {
        listener.onViewClick(mLayoutView, mDialog);
        return this;
    }

    //关闭
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;//清空对象
            window = null;
        }
    }

    /**
     * 向上移动 虚拟键一般高度
     * 使至弹框居中
     */
    private void moveTop() {
        WindowManager.LayoutParams lp = window.getAttributes();
        int y = getBotBarHeight();//虚拟键高度
        lp.y = -y / 2; // 偏移原来坐标 向上
        window.setAttributes(lp);
    }

    /**
     * 获取底部 虚拟键 高度
     *
     * @return
     */
    public int getBotBarHeight() {
        Rect rect = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = mContext.getWindow().getDecorView().getHeight();
        return winHeight - rect.bottom;
    }
}
