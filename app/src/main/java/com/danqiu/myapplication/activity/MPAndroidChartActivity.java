package com.danqiu.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MPAndroidChartActivity extends AppCompatActivity {
    @BindView(R.id.mpa_LineChart)
    LineChart mpaLineChart;
    @BindView(R.id.mpa_BarChart)
    BarChart mpaBarChart;
    @BindView(R.id.mpa_PieChart)
    PieChart mpaPieChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapchart);
        ButterKnife.bind(this);

        initData();
    }

    /**
     * 初始化
     */
    private void initData() {

    }
}
