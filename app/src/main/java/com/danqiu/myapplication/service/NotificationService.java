package com.danqiu.myapplication.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.activity.LoaingActivity;
import com.danqiu.myapplication.activity.NotificationActivity;
import com.danqiu.myapplication.utils.MLog;

/**
 * Created by Administrator on 2018/12/7.
 * 通知栏的服务
 */

public class NotificationService extends Service{
    private Notification.Builder builder;
    private NotificationManager manager;
    private NotificationActivity.MyServiceConnection conn;
    private Intent oneIntent;
    private int index_Progress=0;

    boolean isRunning = false;
    MyThread myThread;
    public String tag="NotificationService";

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    String result = msg.obj.toString();
                    int i=Integer.parseInt(result);
                    if (i != 6) {
                        builder.setProgress(100, i, false).setContentText("下载" + i + "%");
                        manager.notify(1, builder.build());
                        MLog.i("test", "接收到" + i);
                    } else {
                        builder.setProgress(100, i, false).setContentText("下载完成");
                        manager.notify(1, builder.build());
                        if(conn!=null){
                            //unbindService(conn);
                            conn=null;
                        }
                        //调用安装

                    }
                    break;
            }
        }
    };


    /** * 创建Binder对象，返回给客户端即Activity使用，提供数据交换的接口 */
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder{
        // 声明一个方法，getService。（提供给客户端调用）
        public NotificationService getService(){
            return NotificationService.this;
        }
        public void myStart(NotificationActivity.MyServiceConnection mConn,Intent intent){
            start(mConn,intent);
        }
        public void myUnBind(){
            stop();
        }
    }

    /**
     * 开始下载
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MLog.i(tag,"-------------onBind-----------");
        sendNotification();
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.i(tag,"-------------onCreate-----------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.i(tag,"-------------onStartCommand-----------");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载结束
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        stop();
        MLog.i(tag,"-------------onUnbind-----------");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLog.i(tag,"-------------onDestroy-----------");
    }

    /**
     * 服务耗时操作
     */
    private class MyThread implements Runnable {
        @Override
        public void run() {
            if (isRunning) {
                index_Progress++;
                handler.postDelayed(myThread, 1000);
                Message msg=Message.obtain();
                msg.what=2;
                msg.obj=index_Progress;
                handler.sendMessage(msg);
            }
        }
    }

    public void start(NotificationActivity.MyServiceConnection mConn,Intent intent) {
        if(myThread==null){
            this.myThread=new MyThread();
            this.conn=mConn;
            this.oneIntent=intent;
        }
        if (!isRunning) {
            isRunning = true;
            handler.removeCallbacks(myThread);
            handler.postDelayed(myThread, 500);
        }
    }

    public void stop() {
        if (isRunning) {
            handler.removeCallbacks(myThread);
            isRunning = false;
            handler=null;
            myThread=null;
        }
    }

    /**
     * 通知栏
     */
    public void sendNotification() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //ChannelId为"1001",ChannelName为"丹秋8.0渠道标志"
            NotificationChannel channel = new NotificationChannel("1001", "丹秋8.0渠道标志", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            //channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            manager.createNotificationChannel(channel);

            NotificationChannel getChannel_Id = manager.getNotificationChannel("1001");
            //判断渠道通知id是否关闭
            if (getChannel_Id.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }

            builder = new Notification.Builder(this, "1001");//与channelId对应
            builder.setContentTitle("标题")
                    .setContentText("内容是什么呢")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setProgress(100, index_Progress, false)
                    //自定义打开的界面
                    .setContentIntent(PendingIntent.getActivity(this, 0,
                            new Intent(this, LoaingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
            manager.notify(1, builder.build());
        }
        //8.0以下
        else {
            builder = new Notification.Builder(this);
            builder.setContentTitle("标题")
                    .setContentText("内容是什么呢")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setProgress(100, index_Progress, false)
                    //自定义打开的界面
                    .setContentIntent(PendingIntent.getActivity(this, 0,
                            new Intent(this, LoaingActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
            manager.notify(1, builder.build());
        }

       //模拟网络下载
    }
}
