package com.danqiu.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.danqiu.myapplication.utils.MLog;

/**
 * Created by Administrator on 2018/12/7.
 *  startService不用MyBinder
 *  bindService常自定义内部类用来Activity调用方法 如下:myPlaying
 */

public class ServiceOne extends Service{
    public String tag="ServiceOne";
    public boolean isClose=true;
    /** * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口 */
    private ServiceOne.MyBinder binder = new ServiceOne.MyBinder();

    public class MyBinder extends Binder {
        // 声明一个方法，getService。（提供给客户端调用）
        public ServiceOne getService(){
            return ServiceOne.this;
        }
        public void myPlaying(){
            playing();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MLog.i(tag,"-------------onBind-----------");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.i(tag,"-------------onCreate--一次---------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.i(tag,"-------------onStartCommand----可以多次调用-------");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if(isClose){
//                 for (int i=0;i<120;i++){
//                    MLog.i(tag,"-------------onStartCommand-----------"+i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                 }
//                }
//            }
//        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
    //普通启动方式无法解绑
    @Override
    public boolean onUnbind(Intent intent) {

        MLog.i(tag,"-------------onUnbind-----------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        isClose=false;
        MLog.i(tag,"-------------onDestroy-----------");
        super.onDestroy();
    }

    /**
     *
     */
    public void playing(){
        MLog.i(tag,"------------玩-----------");
    }
}
