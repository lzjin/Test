package com.danqiu.myapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.utils.ToastUtil;
import com.danqiu.myapplication.views.ProgressDialogFragment;

/**
 * Created by Administrator on 2018/9/19.
 */

abstract class BaseActivity extends AppCompatActivity {
    public String TAG = getClass().getSimpleName();
    public ProgressDialog dialog;
    public ProgressDialogFragment dialogFragment;
    public BaseActivity activity;//不v

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        initCreate(savedInstanceState);

    }
    public abstract void initCreate(Bundle savedInstanceState);//子类--初始化



    public void showDialog() {
        dialog = new ProgressDialog(activity);
        dialog.setMessage("加载中...");
        dialog.show();
    }


    /**
     *  Toast短显示
     */
    public void showToast(String str){
        ToastUtil.showShort(this,str);
    }
}
