package com.danqiu.myapplication.retrofit;

import android.app.ProgressDialog;
import android.content.Context;

import com.danqiu.myapplication.utils.LogUtil;
import com.danqiu.myapplication.utils.ToastUtil;
import com.danqiu.myapplication.views.ProgressDialogFragment;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/9/27.
 *  二次封装
 */

public abstract  class ProgressSubscriber <T> extends Subscriber<T> {

    private Context mContext;
    private ProgressDialog dialog;
    private ProgressDialogFragment dialogFragment;

    public ProgressSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    public ProgressSubscriber(Context mContext, ProgressDialog dialog) {
        this.mContext = mContext;
        this.dialog = dialog;
    }

    public ProgressSubscriber(Context mContext, ProgressDialogFragment dialogFragment) {
        this.mContext = mContext;
        this.dialogFragment = dialogFragment;
    }



    @Override
    public void onCompleted() {
        LogUtil.i("test","--------onCompleted");
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        HttpException httpException = (HttpException) e;
        int responseCode = httpException.code();
        LogUtil.i("test","------error------错误信息:"+e.getMessage()+"  ----------错误码:"+responseCode);
        e.printStackTrace();
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
        if(e instanceof UnknownHostException) {
            ToastUtil.showShort(mContext, "网络连接失败，请检查网络设置");
            _onError("网络不可用");
        }
        else if (e instanceof SocketTimeoutException){
            ToastUtil.showShort(mContext, "网络连接超时");
            _onError("超时");
        }
        else if (e instanceof ConnectException) {
            ToastUtil.showShort(mContext, "网络连接失败");
            _onError("失败");
        }
        else {
            ToastUtil.showShort(mContext, "网络连接异常");
            _onError("异常");
        }

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
        if (dialog != null) {
            dialog.dismiss();
        }
        if(dialogFragment != null){
            dialogFragment.dismiss();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);



}
