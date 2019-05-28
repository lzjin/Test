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
 * Created by lzj on 2019/4/16
 * Describe ：注释
 */
public class RecycleViewAdapterHelperAct extends AppCompatActivity {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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
        activity = this;
        initData();
    }

    private void initData() {
        lisBean = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
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
                ToastUtil.showShort(activity, "点击" + (position + 1));
            }
        });
//        listRecycle.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
//                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    listAdapter.setScrolling(false);
//                    listAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                } else{
//                    listAdapter.setScrolling(true);
//                }
//
//            }
//        });

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
                refreshlayout.finishLoadmore(1500);
                addDataUp();//上拉加载更多
            }
        });


//        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                lisBean = new ArrayList<>();
//                for (int i = 0; i < 8; i++) {
//                    lisBean.add(new RecycleBean(url, "李四", "撒大声地大夫水电费水电费是打发斯蒂芬是否"));
//                }
//
//                listAdapter.setNewData(lisBean);
//                listAdapter.loadMoreComplete();
//                listAdapter.notifyDataSetChanged();
//
//            }
//        });

    }

    private void addDataDown() {
        lisBean.clear();
        lisBean = null;
        lisBean = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            lisBean.add(new RecycleBean(url, "张三2", "是打发水电费水电费第三方士大夫水电费水电费是打发斯蒂芬是否"));
        }
        listAdapter.loadMoreComplete();
        listAdapter.setNewData(lisBean);

        listAdapter.notifyDataSetChanged();

    }

    private void addDataUp() {

        TimerTask task = new TimerTask() {
            public void run() {
                for (int i = 0; i < 4; i++) {
                    lisBean.add(new RecycleBean(url, "李四", "发个非官方2222222222电费水电费是打发斯蒂芬是否"));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //  myListAdapter.notifyDataSetChanged();
                        listAdapter.setNewData(lisBean);
                        listAdapter.loadMoreComplete();
                        listAdapter.notifyDataSetChanged();
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }
}
