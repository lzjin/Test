package com.danqiu.myapplication.socket;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.danqiu.myapplication.utils.MLog;

import org.greenrobot.eventbus.EventBus;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *  使用服务连接Socket
 */
public class JWebSocketClientService extends Service {
    private final static String TAG="zz";
    // ---------websocket心跳检测------------------
    private static final long HEART_BEAT_RATE = 5 * 1000;//每隔10秒进行一次对长连接的心跳检测

//    private Handler mHandler = new Handler();
//    private Runnable heartBeatRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Log.e(TAG, "心跳包检测websocket连接状态"+System.currentTimeMillis());
//            if (socketClient != null) {
//                if (socketClient.isClosed()) {
//                    reconnectWs();
//                }else {
//                    sendMsg("心跳检测消息");
//                }
//            } else {
//                //如果client已为空，重新初始化连接
//                socketClient = null;
//                initSocketClient();
//            }
//            //每隔一定的时间，对长连接进行一次心跳检测
//            mHandler.postDelayed(this, HEART_BEAT_RATE);
//        }
//    };

    public  JWebSocketClient socketClient;
    private JWebSocketClientBinder mBinder = new JWebSocketClientBinder();
    public JWebSocketClientService() {
    }
    //用于Activity和service通讯
    public class JWebSocketClientBinder extends Binder {
        public JWebSocketClientService getService() {
            return JWebSocketClientService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        MLog.i(TAG,"-------------onUnbind----");
        closeConnect();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.i(TAG,"-------------onCreate---------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLog.i(TAG,"-------------onStartCommand---------");
        //初始化websocket
        initSocketClient();
        //mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        MLog.i(TAG,"-------------onDestroy---------");
        closeConnect();
        super.onDestroy();
    }




    /**
     * 初始化websocket连接
     */
    private void initSocketClient() {
        URI uri = URI.create("ws://10.0.255.169:11211");
        socketClient = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                Log.e(TAG, "收到服务消息：" + message);
                //接收到服务消息 eventbus或广播更新ui
                //EventBus.getDefault().post("收到服务消息："+message);
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.e(TAG, "websocket连接成功");
                EventBus.getDefault().post("连接成功");
            }
            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "websocket连接关闭");
                EventBus.getDefault().post("连接关闭");
            }
        };
        connect();
    }

    /**
     * 连接websocket
     */
    private void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //设置ssl证书 可不设置
                    // SSLSocketFactory factory = getSSL().getSocketFactory();
                   // socketClient.setSocket(factory.createSocket());
                    socketClient.connectBlocking(); // 或socketClient.connect();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 发送消息
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != socketClient&&socketClient.isOpen()) {
            Log.e(TAG, "发送的消息：" + msg);
            socketClient.onMessage(msg);
        }else {
            EventBus.getDefault().post("断开连接");
            Log.e(TAG, "发送的消息：当前断连无法发送");
        }
    }
    /**
     * 断开连接
     */
    public void closeConnect() {
        try {
            if (null != socketClient) {
                socketClient.close();
            }
//            if(null != mHandler){
//                mHandler.removeCallbacks(heartBeatRunnable);
//            }
            if(null!=handler){
                handler.removeCallbacksAndMessages(null);
                stopTimer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socketClient = null;
        }
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        //mHandler.removeCallbacks(heartBeatRunnable);
        handler.removeCallbacksAndMessages(null);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "开启重连");
                    socketClient.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * ssl证书
     * @return
     */
    public SSLContext getSSL() {
        SSLContext sslContext=null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    private Timer mTimer=null;//定时器
    private MyTimerTask mTimerTask=null;
    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e(TAG, "心跳包检测websocket连接状态"+System.currentTimeMillis());
                    if (socketClient != null) {
                        if (socketClient.isClosed()) {
                            reconnectWs();
                        }else {
                            sendMsg("心跳检测消息");
                        }
                    } else {
                        //如果client已为空，重新初始化连接
                        socketClient = null;
                        initSocketClient();
                    }
                    break;
            }
        }
    };
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;  //消息(一个整型值)
            handler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
            //handler.sendEmptyMessage(1001);
        }
    }
   /**
     * 开启定时器
     */
    public void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if(mTimerTask==null){
            mTimerTask=new MyTimerTask();
        }
        mTimer.schedule(mTimerTask, 3000, 1000*5);
    }
    /**
     * 停止定时器
     */
    public void stopTimer(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer = null;
        }
        if(mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

}
