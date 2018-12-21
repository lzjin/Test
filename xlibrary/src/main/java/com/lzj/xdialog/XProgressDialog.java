package com.lzj.xdialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/12/20.
 * 加载进度对话框
 *
 * new XProgressDialog(this).onShow();
 * new XProgressDialog(this,"登录中",true).onShowN(true,false);
 */

public class XProgressDialog extends AlertDialog {
    private Context mContext;
    private TextView mTitleTxt;
    private String mTitle;
    private boolean isBack=true;
    private boolean isOutside=false;
    private ProgressDialog mProgressDialog;
    private boolean isNative=false;

    /**
     * 自定义效果
     * @param context
     */
    public XProgressDialog(Context context) {
        super(context, R.style.xProgressDialog);
        this.mContext=context;
    }

    /**
     * 自定义效果
     * @param context
     * @param title
     */
    public XProgressDialog(Context context, String title) {
        super(context, R.style.xProgressDialog);
        this.mContext=context;
        this.mTitle=title;
    }

    /**
     * 默认效果
     * @param context
     * @param title
     * @param iNative  这个参数只是个区别标识
     */
    public XProgressDialog(Context context, String title,boolean iNative) {
        super(context);
        this.mContext=context;
        this.mTitle=title;
        this.isNative=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isNative){
            addView();
        }

    }

    /**
     * 默认效果
     * @return
     */
    public XProgressDialog onShowN() {
        mProgressDialog=null;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(mTitle);
        mProgressDialog.show();
        return this;
    }
    public XProgressDialog onShowN(boolean iBack,boolean iOutside){
        mProgressDialog=null;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(iBack);//点击返回是否关闭
        mProgressDialog.setCanceledOnTouchOutside(iOutside);//点击空白是否关闭
        mProgressDialog.setMessage(mTitle);
        mProgressDialog.show();
        return this;
    }
    public XProgressDialog onDismissN(){
        if(mProgressDialog!=null||mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            mProgressDialog=null;
        }
        return this;
    }

    /**
     *  以下为自定效果
     * @return
     */
    public void addView() {
        setContentView(R.layout.view_xprogress_loading);
        mTitleTxt = (TextView) findViewById(R.id.tv_load_dialog);
        if (!TextUtils.isEmpty(mTitle)) { //设置标题
            mTitleTxt.setText(mTitle);
        }
    }
    public XProgressDialog onShow(boolean iBack,boolean iOutside){
        this.setCancelable(iBack);//点击返回是否关闭
        this.setCanceledOnTouchOutside(iOutside);//点击空白是否关闭
        this.show();
        return this;
    }
    public XProgressDialog onShow(){
        this.setCancelable(isBack);//点击返回是否关闭
        this.setCanceledOnTouchOutside(isOutside);//点击空白是否关闭
        this.show();
        return  this;
    }
    public XProgressDialog onDismiss(){
        if(isShowing()){
            this.dismiss();
        }
        return this;
    }

}