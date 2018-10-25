package com.danqiu.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.danqiu.myapplication.TabLAyoutFrg;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private  List<String> arrayList;
    private FragmentManager fm;  //创建FragmentManager

    public MyViewPagerAdapter(FragmentManager fm,  List<String> arrayList) {
        super(fm);
        this.arrayList = arrayList;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {

        return  TabLAyoutFrg.getFragmentA(arrayList.get(position));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    /**
     * 添加标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position);
    }
}
