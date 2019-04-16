package com.danqiu.myapplication.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.RecycleBean;
import com.danqiu.myapplication.fresco.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by lzj on 2019/4/16
 * Describe ：注释
 */
public class RecycleViewHelperAdapter extends BaseQuickAdapter<RecycleBean, BaseViewHolder> {

    public RecycleViewHelperAdapter(int layoutResId, @Nullable List<RecycleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, RecycleBean item) {
        viewHolder.setText(R.id.name, item.getName());
        viewHolder.setText(R.id.content, item.getContext());


        ImageLoader.loadImage((SimpleDraweeView) viewHolder.getView(R.id.simple_head), item.getHead());
    }
}
