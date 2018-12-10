package com.danqiu.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.service.NotificationService;
import com.danqiu.myapplication.utils.MLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/6.
 * 通知栏
 * 1、使用startService启动方式,必须手动结束stopService
 * 2、使用bindService启动方式,先绑定再使用，这样就也绑定组件同生同死。
 */

public class NotificationActivity extends AppCompatActivity {
    public NotificationService mBindService;
    public NotificationService.MyBinder mBinder;
    public MyServiceConnection myConn;
    public Intent oneIntent;

    private Notification.Builder builder;
    private NotificationManager manager;


    private int index_Progress = 0;
    private MyThread myThread;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_end)
    Button btEnd;
    @BindView(R.id.bt_nat)
    Button btNat;


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if (index_Progress != 100) {
                        builder.setProgress(100, index_Progress, false).setContentText("下载" + index_Progress + "%");
                        manager.notify(1, builder.build());
                        MLog.i("test", "接收到" + index_Progress);
                    } else {
                        builder.setProgress(100, index_Progress, false).setContentText("下载完成");
                        manager.notify(1, builder.build());
                        myThread.stop();
                    }
                    break;
            }


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

    }

    /**
     * 事件
     * @param view
     */
    @OnClick({R.id.bt_nat,R.id.bt_start, R.id.bt_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_nat:
                //方式一  在Activity中更新通知栏
                sendNotification();
                break;
            case R.id.bt_start:
                //方式二:考虑到本Activity被销毁后，线程还在执行，建议通过service方式更新通知栏
                //建议使用绑定方式启动，不常用 startService(oneIntent)方式
                myConn=new MyServiceConnection();
                oneIntent = new Intent(this, NotificationService.class);
                bindService(oneIntent,myConn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.bt_end:
                //根据需求解绑
                if(myConn!=null){
                    unbindService(myConn);
                    myConn=null;
                }
                break;
        }
    }

    public class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (NotificationService.MyBinder) service;
           // mBindService = mBinder.getService();
            mBinder.myStart(myConn,oneIntent);
            MLog.i("activity","-------------创建连接-----------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MLog.i("activity","-------------解除连接-----------");
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
        //方式一  在Activity中更新通知栏
         myThread = new MyThread();
         myThread.start();

    }
    /**
     * 模拟 子线程下载
     */
    private class MyThread implements Runnable {
        boolean isRunning = false;

        @Override
        public void run() {
            if (isRunning) {
                index_Progress++;
                handler.postDelayed(myThread, 1000);
                Message msg=Message.obtain();
                msg.what=1;
                handler.sendMessage(msg);
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
                handler=null;
                myThread=null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MLog.i("test","----------onDestroy----");
        if(myConn!=null){
            unbindService(myConn);
            myConn=null;
        }
    }
}
