package com.danqiu.myapplication.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.DataUserBean;
import com.danqiu.myapplication.databinding.ActivityDatabindBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 * 数据绑定库   测试界面
 */

public class DataBindingAct extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_databind);
        //ActivityDatabindBinding是layout布局文件名称的大写首字母+Binding
        ActivityDatabindBinding binding =DataBindingUtil.setContentView(this, R.layout.activity_databind);
        initData(binding);
    }

    private void initData(ActivityDatabindBinding binding) {

        List<DataUserBean> list=new ArrayList<>();
                           list.add(new DataUserBean("李四","女"));
        List<String> stringList=new ArrayList<String>();
        stringList.add("嗯嗯嗯呃");


        DataUserBean user =new DataUserBean("张三","男");
        binding.setUser(user);
        binding.setStr("字符串");
        binding.setUserListKey(0);
        //binding.setUserList(list);
        binding.setList(stringList);

    }
}
