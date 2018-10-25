package com.danqiu.myapplication.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * Created by lzj on 2017/12/27.
 * 通用  自定义弹框
 */
public class ComDialog {
    private Context mContext;//上下文
    private int mThemeResId;//主题
    private View mContentView;//布局
    private AlertDialog mDialog;//弹框
    private Window window;//窗口

    /**
     * 布局接口
     */
    public static interface OnDialogListener {
        public void onViewClick(AlertDialog mDialog, View mianView);//监听
    }
    /**
     * 监听
     */
    private OnDialogListener mDialogListener;

    public ComDialog(Context context, int themeResId, int id_Layout) {//R.style.dialog_gray
        this.mContext = context;
        this.mThemeResId=themeResId;
        this.mContentView = LayoutInflater.from(mContext).inflate(id_Layout,null);
    }

    /**
     * 初始化Dialog
     */
    public ComDialog setAlertDialog(){
        mDialog=new AlertDialog.Builder(mContext,mThemeResId).create();
        mDialog.setCancelable(true);//按返回键退出
        mDialog.show();
        return this;
    }
    public ComDialog setAlertDialog(boolean isBack){
        mDialog=new AlertDialog.Builder(mContext,mThemeResId).create();
        mDialog.setCancelable(isBack);//按返回键退出
        mDialog.show();
        return this;
    }

    /**
     * 设置弹框大小 透明度
     */
    public ComDialog setWindowSize(int width, int height,float amount){
        mDialog.getWindow().setDimAmount(amount);//设置透明度
        window = mDialog.getWindow();
        window.setLayout(width,height);
        window.setContentView(mContentView);//设置弹框布局
        return this;
    }
    /**
     * 设置弹框大小 透明度
     * custom=2 自适应高度
     * 其他就是1指定高度
     */
    public ComDialog setWindowSize(int width, int height,int custom,float amount){
       if(custom==2){
            mDialog.getWindow().setDimAmount(amount);//设置透明度
            window = mDialog.getWindow();
            window.setLayout(width,LinearLayout.LayoutParams.WRAP_CONTENT);
            window.setContentView(mContentView);//设置弹框布局
            return this;
       }
       else {
           mDialog.getWindow().setDimAmount(amount);//设置透明度
           window = mDialog.getWindow();
           window.setLayout(width,height );
           window.setContentView(mContentView);//设置弹框布局
           return this;
       }

    }
    /**
     * 点击外部消失
     */
    public ComDialog setOutColse(boolean isOut){
        if (isOut){
            mDialog.setCanceledOnTouchOutside(true);
        }
        else {
            mDialog.setCanceledOnTouchOutside(false);
        }
        return this;
    }

    /**
     * 方式 与位置
     */
    public ComDialog setGravity(int animation, int gravity){
        window.setWindowAnimations(animation);  //添加动画
        window.setGravity(gravity);             //底部
        return this;
    }
    /**
     * 对外监听
     */
    public ComDialog setDialogListener(OnDialogListener listener) {
        mDialogListener = listener;
        return  this;
    }
    /**
     * 切记与先调用setDialogListener
     * 后调用此方法
     * @return
     */
    public ComDialog setListener() {
        //设置对外布局监听事件
        if(mDialogListener!=null){
            mDialogListener.onViewClick(mDialog,mContentView);
        }
        return this;
    }

    //关闭
    public void dismiss() {
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;//清空对象
            window=null;
        }
    }

}
