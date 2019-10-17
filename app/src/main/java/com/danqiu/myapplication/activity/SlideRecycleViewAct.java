package com.danqiu.myapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.adapter.RecycleViewHelperAdapter;
import com.danqiu.myapplication.bean.RecycleBean;
import com.danqiu.myapplication.utils.ToastUtil;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lzj on 2019/10/17
 * Describe ：侧滑删除+显示动画
 */
public class SlideRecycleViewAct extends AppCompatActivity {
    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;
    Activity activity;
    RecycleViewHelperAdapter mAdapter;
    List<RecycleBean> arrayList;
    String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_recycle);
        ButterKnife.bind(this);
        activity = this;

        initData();
    }

    private void initData() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            arrayList.add(new RecycleBean(url, "张三"+i, "是打发水电费水电费第三方士大夫水电费水电费是打发斯蒂芬是否"));
        }

        mAdapter = new RecycleViewHelperAdapter(R.layout.item_recycle, arrayList);
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //设置LayoutManager为ListView
        swipeRecyclerView.setNestedScrollingEnabled(false);//解决滑动不流畅
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if(view.getId()==R.id.content){
                    ToastUtil.showShort(activity, "子view内容点击=" + position);
                }
                if(view.getId()==R.id.simple_head){
                    ToastUtil.showShort(activity, "子view头部点击=" + position);
                }

            }
        });
        //View view = LayoutInflater.from(activity).inflate(R.layout.item_recycle, null, false);
        swipeRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                int width = getResources().getDimensionPixelSize(R.dimen.dp_50);

                SwipeMenuItem modifyItem = new SwipeMenuItem(activity) // 1 编辑
                        .setBackgroundColor(getResources().getColor(R.color.blue3bafd9))
                        .setText("编辑")
                        .setTextColor(Color.BLACK)
                        .setTextSize(15) // 文字大小。
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(modifyItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(activity);   // 2 删除
                deleteItem.setText("删除")
                        .setBackgroundColor(getResources().getColor(R.color.gray8c))
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(15) // 文字大小。
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(deleteItem);
            }
        });
        swipeRecyclerView.setLongPressDragEnabled(true); // 拖拽排序，默认关闭。
        swipeRecyclerView.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                // 左侧还是右侧菜单：
                if (menuBridge.getDirection() == SwipeRecyclerView.RIGHT_DIRECTION) {
                    arrayList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        swipeRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                ToastUtil.showShort(activity, "点击=" + adapterPosition);
            }
        });
        swipeRecyclerView.setAdapter(mAdapter);
    }
}
