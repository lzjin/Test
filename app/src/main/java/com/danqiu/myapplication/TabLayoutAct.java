package com.danqiu.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.danqiu.myapplication.adapter.MyViewPagerAdapter;
import com.danqiu.myapplication.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/18.
 * 练习tablayout+fragment
 */

public class TabLayoutAct extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.fragment_pager)
    ViewPager viewPager;
    List<String> listTitle;
    MyViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        ButterKnife.bind(this);

        initView();

    }


    private void initView() {

        listTitle=new ArrayList<>();
        for(int i=0;i<8;i++){
            listTitle.add("语文"+i);
        }
        for (int i = 0; i <8; i++) {
            TabLayout.Tab tab = tablayout.newTab();
            tab.setText("语文"+i);
            // tab.setIcon(R.mipmap.ic_launcher);//icon会显示在文字上面
            tablayout.addTab(tab);
        }

        //1.MODE_SCROLLABLE模式
         tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
         adapter = new MyViewPagerAdapter(getSupportFragmentManager(),listTitle);
         viewPager.setAdapter(adapter);
        // 关联TabLayout与ViewPager，且适配器必须重写getPageTitle()方法

         tablayout.setupWithViewPager(viewPager);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtil.i("TabLayoutAct","------点击--------"+tab);
                Toast.makeText(TabLayoutAct.this,"ok", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
