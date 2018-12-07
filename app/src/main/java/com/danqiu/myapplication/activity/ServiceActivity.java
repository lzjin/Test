package com.danqiu.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.service.ServiceOne;
import com.danqiu.myapplication.utils.MLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/7.
 * 服务使用
 */

public class ServiceActivity extends AppCompatActivity {
    @BindView(R.id.bt_startService)
    Button btStart;
    @BindView(R.id.bt_endService)
    Button btEnd;
    @BindView(R.id.bt_bindService)
    Button btBind;
    @BindView(R.id.bt_unbindService)
    Button btUnbind;
    public MyServiceConnection myConn;
    public ServiceOne mBindService;
    public ServiceOne.MyBinder mBinder;
    public Intent oneIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }

    /**
     *
     * @param view
     */
    @OnClick({R.id.bt_startService, R.id.bt_endService,R.id.bt_bindService,R.id.bt_unbindService})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_startService://startService方式启动服务
                oneIntent = new Intent(this, ServiceOne.class);
                startService(oneIntent);
                break;
            case R.id.bt_endService://结束服务，不是解绑
                if(oneIntent!=null){
                    stopService(oneIntent);
                }
                break;
            case R.id.bt_bindService://bindService绑定方式启动服务
                myConn=new MyServiceConnection();
                oneIntent = new Intent(this, ServiceOne.class);
                bindService(oneIntent,myConn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.bt_unbindService://解绑服务
                if(myConn!=null){
                    unbindService(myConn);
                    myConn=null;
                }

                break;
        }
    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (ServiceOne.MyBinder) service;
            mBinder.myPlaying();
            //mBindService = mBinder.getService();

            MLog.i("activity","-------------创建连接-----------");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            MLog.i("activity","-------------解除连接-----------");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myConn!=null){
            unbindService(myConn);
            myConn=null;
        }
    }
}
