package com.danqiu.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/11/19.
 */

public class MyPager3DAdapter extends PagerAdapter {
    private List<String> mListUrl;
    private Context mContext;


    public MyPager3DAdapter(List<String> data,Context context){
        this.mListUrl = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        //return mListUrl.size();
          return 500000;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pager,container,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);

        //GlideUtils.LoadImage(mContext,mListUrl.get(position),imageView,R.mipmap.ic_launcher);

         int index=position % mListUrl.size();
         GlideUtils.LoadImage(mContext,mListUrl.get(index),imageView,R.mipmap.ic_launcher);


        container.addView(view);
        return view;
    }




}