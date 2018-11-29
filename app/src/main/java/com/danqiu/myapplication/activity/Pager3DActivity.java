package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.utils.IntentUtil;
import com.danqiu.myapplication.utils.ToastUtil;
import com.danqiu.myapplication.views.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 * 3D轮播画廊
 */

public class Pager3DActivity extends AppCompatActivity {

    @BindView(R.id.viewpager2)
    BannerViewPager mViewPager2;
    @BindView(R.id.viewPager)
    com.lzj.gallery.library.views.BannerViewPager viewPager;


    private List<String> urlList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);

        urlList = new ArrayList<>();


        urlList.add("https://1timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543221773&di=c63f30c7809e518cafbff961bcd9ec2a&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0116605851154fa8012060c8587ca1.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042541&di=3ad9deeefff266e76d1f5d57a58f63d1&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=95bd41d43c335e74863d9bb540361906&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019a0558be22d6a801219c77d0578a.jpg%402o.jpg");
        urlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542627042539&di=cdd54bffd2aac448c70ae6b416a004d4&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01edb3555ea8100000009af0ba36f5.jpg%401280w_1l_2o_100sh.jpg");

        //依赖形式使用
        viewPager.initBanner(urlList, false)
                .addPageMargin(10, 60)
                .addPoint(6)
                // .addPointBottom(7)
                .addStartTimer(5)
                .addRoundCorners(12)
                .finishConfig();

        //本地类直接使用
        mViewPager2.initBanner(urlList, false)
                .addPageMargin(10, 60)
                .addPoint(6)
                // .addPointBottom(7)
               // .addStartTimer(3)
                .addRoundCorners(12)
                .finishConfig()
                .addBannerListener(new BannerViewPager.OnClickBannerListener() {
                    @Override
                    public void onBannerClick(int position) {
                        IntentUtil.IntenToActivity(Pager3DActivity.this,CardNoAct.class);
                        ToastUtil.showShort(Pager3DActivity.this, "点击了" + position);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewPager2.stopTimer();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        mViewPager2.addStartTimer(5);
    }
}
