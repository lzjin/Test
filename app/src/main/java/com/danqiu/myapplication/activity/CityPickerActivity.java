package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.fragment.city.base.CityPicker;
import com.danqiu.myapplication.fragment.city.hotcity.ChoiceCityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description ： 仿美团外卖 城市选择
 * author : asus
 * date : 2020/9/27
 */
public class CityPickerActivity extends AppCompatActivity {
    @BindView(R.id.bt_city)
    Button btCity;
    @BindView(R.id.bt_city2)
    Button btCity2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypicker);
        ButterKnife.bind(this);

    }



    @OnClick({R.id.bt_city, R.id.bt_city2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_city:
                initView();
                break;
            case R.id.bt_city2:
                initView2();
                break;
        }
    }

    private void initView() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        CityPicker.from(this) //activity或者fragment
                .enableAnimation(true)    //启用动画效果，默认无
                .setAnimationStyle(R.style.dialogCommonTheme)    //自定义动画
                //.setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，传null会自动定位（默认）
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                // CityPicker.getInstance().locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                CityPicker.from(CityPickerActivity.this).locateComplete(new LocatedCity("武汉", "湖北", "101280651"), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
    }

    private void initView2() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));


        ChoiceCityPicker.from(this) //activity或者fragment
                .enableAnimation(true)    //启用动画效果，默认无
                .isHttpSearch(true)
                .setAnimationStyle(R.style.dialogCommonTheme)    //自定义动画
                //.setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，传null会自动定位（默认）
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）
                .setHotCities(hotCities)    //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLocate() {
                        Log.e("testz","-----------------00---开始定位");
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("testz","--------------------开始定位");
                                //定位完成之后更新数据
                               // ChoiceCityPicker.getInstance().locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                ChoiceCityPicker.from(CityPickerActivity.this).locateComplete(new LocatedCity("武汉", "湖北", "101280651"), LocateState.SUCCESS);
                            }
                        }, 3000);
                    }
                })
                .show();
    }
}
