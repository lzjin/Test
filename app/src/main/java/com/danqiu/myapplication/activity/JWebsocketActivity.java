package com.danqiu.myapplication.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.netty.HeartbeatClient;
import com.danqiu.myapplication.socket.JWebSocketClient;
import com.danqiu.myapplication.socket.JWebSocketClientService;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzj on 2020/3/11
 * Describe ：注释
 */
public class JWebsocketActivity extends AppCompatActivity {
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_msg)
    Button btnMsg;
    @BindView(R.id.btn_colse)
    Button btnColse;
    private final static String TAG="zz";
    //定义一个全局变量用来标记
    private boolean isConnected = false;

    private JWebSocketClient socketClient;
    private JWebSocketClientService.JWebSocketClientBinder binder;
    private JWebSocketClientService jWebSClientService;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "服务与活动成功绑定");
            binder = (JWebSocketClientService.JWebSocketClientBinder) iBinder;
            jWebSClientService = binder.getService();
            socketClient = jWebSClientService.socketClient;
            isConnected=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "服务与活动成功断开");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

//        //启动服务
//        startJWebSClientService();
//        //绑定服务
//        bindService();

    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String msg) {
        MLog.i(TAG, "-------act----接收-------" + msg);
    }

    @OnClick({R.id.btn_open, R.id.btn_send, R.id.btn_msg, R.id.btn_colse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HeartbeatClient.getInstance().start();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                break;
            case R.id.btn_add:
                ToastUtil.showShort(this,"5秒内自动重连");
                break;
            case R.id.btn_send:
                HeartbeatClient.getInstance().sendMsg("我是客服端9527："+System.currentTimeMillis());
                break;
            case R.id.btn_msg:
                break;
            case R.id.btn_colse:
                if(jWebSClientService!=null){
                    jWebSClientService.closeConnect();
                }

                break;
        }
    }

    /**
     * 启动服务（websocket客户端服务）
     */
    private void startJWebSClientService() {
        Intent intent = new Intent(this, JWebSocketClientService.class);
        startService(intent);
    }
    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(this, JWebSocketClientService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isConnected&&serviceConnection!=null){
            unbindService(serviceConnection);
            //serviceConnection=null;
            isConnected = false;
        }
    }

}
