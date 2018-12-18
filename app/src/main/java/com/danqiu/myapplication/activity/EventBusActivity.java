package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/14.
 */

public class EventBusActivity extends AppCompatActivity {
    @BindView(R.id.tvName)
    Button btSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tvName)
    public void onViewClicked() {
        EventBus.getDefault().post(new MessageEvent("张飒"));
    }
}
