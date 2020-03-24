package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;

/**
 * Created by lzj on 2020/3/19
 * Describe ：使用官方推荐viewbinding
 */
public class ViewBindingAct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbinding);
    }
}
