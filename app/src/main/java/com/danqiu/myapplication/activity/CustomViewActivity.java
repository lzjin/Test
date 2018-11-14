package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;

/**
 * Created by Administrator on 2018/11/14.
 * 使用自定义view控件
 */

public class CustomViewActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
    }
}
