package com.danqiu.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.danqiu.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<String> mListUrl;
    private Context mContext;


    public MyPagerAdapter(List<String> data, Context context) {
        this.mListUrl = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mListUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pager, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);

        LoadImage(mContext, mListUrl.get(position), imageView, R.mipmap.ic_launcher);

        // int index=position % mListUrl.size();
        // LoadImage(mContext,mListUrl.get(index),imageView,R.mipmap.ic_launcher);


        container.addView(view);
        return view;
    }

    public void LoadImage(Context mContext, String url, ImageView imageview, int defaultImg) {
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageview);
    }
}