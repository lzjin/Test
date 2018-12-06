package com.danqiu.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.MLog;

/**
 * Created by Administrator on 2018/12/6.
 *  通知栏
 */

public class NotificationActivity extends AppCompatActivity{
    Notification.Builder builder;
    NotificationManager manager;
    int index_Progress=0;
    MyThread myThread;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(index_Progress!=100){
                builder.setProgress(100, index_Progress,false).setContentText("下载"+index_Progress+"%");
                manager.notify(1, builder.build());
                MLog.i("test","接收到"+index_Progress);
            }
            else {
                builder.setProgress(100, index_Progress,false).setContentText("下载完成");
                manager.notify(1, builder.build());
                myThread.stop();
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sendChatMsg();
    }

    public void sendChatMsg() {
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

                     builder  = new Notification.Builder(this, "1001");//与channelId对应
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
            manager.notify(1, builder.build() );
        }
        //8.0以下
        else {
                    builder  = new Notification.Builder(this);
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
            manager.notify(1, builder.build() );
        }
        //方式一  线程更新通知栏
        myThread=new MyThread();
        myThread.start();
        //方式二:考虑到本Activity被销毁后，线程还在执行，所以通过service

    }

    private class MyThread implements Runnable {
        boolean isRunning=false;
        @Override
        public void run() {
            if (isRunning) {
                index_Progress++;
                handler.postDelayed(myThread, 1000);
                handler.sendMessage(Message.obtain());
            }
        }

        public void start() {
            if (!isRunning) {
                isRunning = true;
                handler.removeCallbacks(this);
                handler.postDelayed(this, 1000);
            }
        }
        public void stop() {
            if (isRunning) {
                handler.removeCallbacks(this);
                isRunning = false;
            }
        }
    }

}
