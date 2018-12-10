package com.danqiu.myapplication.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.service.MyBroadCastReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/10.
 * 广播介绍
 * 无序广播sendBroadcast（）：所有的接收者都会接收事件，不可以被拦截，不可以被修改。
 * 有序广播sendOrderedBroadcast（）：按照优先级，一级一级的向下传递，接收者可以修改广播数据，也可以终止广播事件。
 */

public class BroadcastActivity extends AppCompatActivity {
    MyBroadCastReceiver broadCastReceiver;
    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //1动态方式注册
        broadCastReceiver = new MyBroadCastReceiver();
        //添加过滤的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("ttt");
        //注册广播
        registerReceiver(broadCastReceiver, intentFilter);

        //2静态注册方式在xml写


    }
    /**
     *
     * @param view
     */
    @OnClick({R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                //发送广播测试，一般是在他页面发送广播
                Intent intent = new Intent();
                intent.setAction("ttt");
                intent.putExtra("context","我发送的广播内容");
                //发送无序广播
                sendBroadcast(intent); // 或sendBroadcast(intent,"Permissions权限");
                break;

        }
    }

    /**
     * 注销广播
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCastReceiver);
    }
}
