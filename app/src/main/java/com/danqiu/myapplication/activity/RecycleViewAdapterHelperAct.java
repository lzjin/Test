package com.danqiu.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.RecycleViewHelperAdapter;
import com.danqiu.myapplication.bean.RecycleBean;
import com.danqiu.myapplication.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2019/4/16
 * Describe ：注释
 */
public class RecycleViewAdapterHelperAct extends AppCompatActivity {
    @BindView(R.id.list_recycle)
    RecyclerView listRecycle;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Activity activity;
    RecycleViewHelperAdapter listAdapter;
    List<RecycleBean> lisBean;
    String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapterhelper);
        ButterKnife.bind(this);
        activity=this;
        initData();
    }

    private void initData() {
        lisBean = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            lisBean.add(new RecycleBean(url, "张三", "是打发水电费水电费第三方士大夫水电费水电费是打发斯蒂芬是否"));
        }

        //ListView 效果
        listAdapter = new RecycleViewHelperAdapter(R.layout.item_recycle, lisBean);
        listRecycle.setLayoutManager(new LinearLayoutManager(this)); //设置LayoutManager为LinearLayoutManager
        listRecycle.setAdapter(listAdapter);
        listRecycle.setNestedScrollingEnabled(false);//解决滑动不流畅
        listAdapter.openLoadAnimation();
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShort(activity,"点击" + (position + 1));
            }
        });




    }
}
