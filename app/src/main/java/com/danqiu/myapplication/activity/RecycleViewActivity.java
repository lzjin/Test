package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.RecycleViewAdapter;
import com.danqiu.myapplication.adapter.RecycleViewGridAdapter;
import com.danqiu.myapplication.bean.RecycleBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2019/3/20
 * Describe ：RecycleView代替 ListView 或GridView
 */
public class RecycleViewActivity extends BaseActivity {

    @BindView(R.id.list_recycle)
    RecyclerView listRecycle;

    @BindView(R.id.grid_recycle)
    RecyclerView gridRecycle;

    RecycleViewGridAdapter girdAdapter;

    RecycleViewAdapter listAdapter;
    List<RecycleBean>  lisBean;
    private String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";
    @Override
    public void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        lisBean=new ArrayList<>();
        for (int i=0;i<8;i++){
            lisBean.add(new RecycleBean(url,"张三","是打发水电费水电费第三方士大夫水电费水电费是打发斯蒂芬是否"));
        }

        //ListView 效果
        listAdapter=new RecycleViewAdapter(this,lisBean);
        listRecycle.setLayoutManager(new LinearLayoutManager(this)); //设置LayoutManager为LinearLayoutManager
        listRecycle.setAdapter(listAdapter);
        listRecycle.setNestedScrollingEnabled(false);//解决滑动不流畅

        listAdapter.setOnItemClickListener(new RecycleViewGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast("点击"+(position+1) );
            }
        });

        //GridView 效果
        girdAdapter=new RecycleViewGridAdapter(this,lisBean);
        gridRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//grid
        gridRecycle.setAdapter(girdAdapter);
        gridRecycle.setNestedScrollingEnabled(false);//解决滑动不流畅

        girdAdapter.setOnItemClickListener(new RecycleViewGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showToast("点击"+(position+1) );
            }
        });
    }


}
