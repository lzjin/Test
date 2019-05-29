package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.MyListAdapter;
import com.danqiu.myapplication.bean.ListBean;
import com.danqiu.myapplication.utils.MLog;
import com.danqiu.myapplication.views.MyRefreshFooter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/9.
 * 刷新框架测试
 */

public class RefreshActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    MyListAdapter myListAdapter;
    List<ListBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        ButterKnife.bind(this);
        ininData();
    }

    private void ininData() {
        addDataDown();
        //设置 Header
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setColorSchemeColors(getResources().getColor(R.color.redd2)));
        //设置 Footer
        refreshLayout.setRefreshFooter(new MyRefreshFooter(this));


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                addDataDown();//下拉刷新
            }

        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                addDataUp();//上拉加载更多
                refreshlayout.finishLoadMore();
            }
        });

    }

    /**
     * 初始化10条
     */
    private void addDataDown() {
        list = new ArrayList<>();
        for (int i = 18; i < 28; i++) {
            list.add(new ListBean("张三", i, "男"));
        }
        myListAdapter = new MyListAdapter(list, this);
        listview.setAdapter(myListAdapter);


        MLog.i("test", "----------------: " + myListAdapter.getItem(0));

        ListBean bean= (ListBean) myListAdapter.getItem(0);
        bean.setName("改变历史");
        long id=myListAdapter.getItemId(0);

        myListAdapter.notifyDataSetInvalidated();
        MLog.i("test", "---------------- : " + myListAdapter.getItem(0));

    }

    private void addDataUp() {

        TimerTask task = new TimerTask() {
            public void run() {
                for (int i = 38; i < 43; i++) {
                    list.add(new ListBean("李四", i, "男"));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myListAdapter.notifyDataSetChanged();

                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }
}
