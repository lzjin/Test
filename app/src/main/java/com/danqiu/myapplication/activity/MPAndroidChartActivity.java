package com.danqiu.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.BarChartVtDateValueBean;
import com.danqiu.myapplication.utils.LocalJsonResolutionUtils;
import com.danqiu.myapplication.utils.MPChartUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MPAndroidChartActivity extends AppCompatActivity {
    @BindView(R.id.mpa_LineChart)
    LineChart mpaLineChart;
    @BindView(R.id.mpa_BarChart)
    BarChart mpaBarChart;
    @BindView(R.id.mpa_PieChart)
    PieChart mpaPieChart;
    public String[] xlineList = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十","十一","十二"};

    public String[] xbarList = {"","一月","二月","三月","四月","五月","六月"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapchart);
        ButterKnife.bind(this);

        initLineChart();

        initBarChart();

        initPieChart();
    }

    /**
     * 柱状图
     */
    private void initBarChart22() {
        //得到本地json文本内容
        String fileName = "test.json";
        String foodJson = LocalJsonResolutionUtils.getJson(this, fileName);
       //转换为对象
        BarChartVtDateValueBean barChartBean = LocalJsonResolutionUtils.JsonToObject(foodJson, BarChartVtDateValueBean.class);
        List<BarChartVtDateValueBean.ResultBean.ShanghaiBean> dateValueList = barChartBean.getResult().getShanghai();
        Collections.reverse(dateValueList);//将集合 逆序排列，转换成需要的顺序

    }

    /**
     * 饼状图
     */
    private void initPieChart() {

        //获取数据 5条
        List<PieEntry> pieEntrys = new ArrayList<>();

        pieEntrys.add(new PieEntry(20.0f, "单选"));
        pieEntrys.add(new PieEntry(30.0f, "阅读"));
        pieEntrys.add(new PieEntry(10.0f, "填空"));
        pieEntrys.add(new PieEntry(18.0f, "作文"));
        pieEntrys.add(new PieEntry(22.0f, "改错"));

        // 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#f5a658"));

        MPChartUtils.initPieChartData(this,"英语成绩",mpaPieChart,pieEntrys,colors,true);

    }

    /**
     *  折线图 或者曲线  setMode
     */
    private void initLineChart() {
        //获取数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        MPChartUtils.initLineChartData(this,"月份",mpaLineChart,entries,Arrays.asList(xlineList));


    }

    /**
     *  柱状图
     */
    public  void initBarChart() {

        //获取数据 5条
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1, 60));
        entries.add(new BarEntry(2, 70));
        entries.add(new BarEntry(3, 30));
        entries.add(new BarEntry(4, 55));
        entries.add(new BarEntry(5, 75));

        MPChartUtils.initBarChartData(this,"月考",mpaBarChart,entries,Arrays.asList(xbarList));



    }

}
