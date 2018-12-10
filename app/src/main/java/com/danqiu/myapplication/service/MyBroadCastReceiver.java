package com.danqiu.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.danqiu.myapplication.utils.MLog;

/**
 * Created by Administrator on 2018/12/10.
 * onReceive（）这里广播接收
 */

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //在这里实现一些功能,启动Service服务或打开通知栏
        String str=intent.getExtras().getString("context");
        MLog.i("test","-----------------接收到广播="+str);
    }
}
