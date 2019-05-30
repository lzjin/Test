package com.danqiu.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.danqiu.myapplication.R;
import com.danqiu.myapplication.bean.BarChartVtDateValueBean;
import com.danqiu.myapplication.utils.LocalJsonResolutionUtils;
import com.danqiu.myapplication.utils.PieChartManaggerUtil;
import com.danqiu.myapplication.views.MPA_LineMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
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
    public String[] xlineList = {"一月","二月","三月","四月","五月","六","七","八","九","十","十一","十二"};
    public String[] xbarList = {"","语文","数学","化学",""};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        List<PieEntry> yvals = new ArrayList<>();

        yvals.add(new PieEntry(2.0f, "单选"));
        yvals.add(new PieEntry(3.0f, "阅读"));
        yvals.add(new PieEntry(4.0f, "填空"));
        yvals.add(new PieEntry(5.0f, "作文"));
        yvals.add(new PieEntry(1.0f, "改错"));

        // 设置每份的颜色
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#f5a658"));

        PieChartManaggerUtil pieChartManagger=new PieChartManaggerUtil(mpaPieChart);
        pieChartManagger.showSolidPieChart(yvals,colors);

    }
    /**
     * 初始化柱状图图表数据
     */
    public  void initBarChart() {
        //显示边界
        mpaBarChart.setDrawBorders(true);
        //获取数据 5条
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(1, 60));
        entries.add(new BarEntry(2, 70));
        entries.add(new BarEntry(3, 80));

        BarDataSet set1 = new BarDataSet(entries, "成绩");
        set1.setValueTextColor(Color.WHITE);
       // set1.setColor(barColor);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        // 设置bar的宽度，但是点很多少的时候好像没作用，会拉得很宽
        data.setBarWidth(0.2f);
        // 设置value值 颜色
        data.setValueTextColor(Color.RED);
        mpaBarChart.setData(data);
        mpaBarChart.invalidate();

        //得到X轴
        XAxis xAxis = mpaBarChart.getXAxis();

        //设置X轴的位置（默认在上方）：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        //设置X轴坐标之间 缩放值（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        xAxis.setGranularity(1f);
        //4.设置X轴的总刻度数量，均匀分配12f/12  为1:1
        xAxis.setLabelCount(5, true);
        //5.设置X轴 起始值、最终值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);//
        xAxis.setAxisMaximum(4f);

        //得到y轴
        // YAxis  yAxis = mpaLineChart.get();


        //6.设置X轴值为字符串（默认为上述5 中10  ）
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index=(int )value;
//                if(index==xlineList.length){
//                    index--;
//                }
                return xbarList[index]+"";
            }
        });
        // 1.得到Y轴
        YAxis leftYAxis = mpaBarChart.getAxisLeft();
        YAxis rightYAxis = mpaBarChart.getAxisRight();
        //2.设置起始与终止值、间隔值
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);

        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setLabelCount(11, true);
        rightYAxis.setLabelCount(11, true);
        //右侧Y轴不显示
        rightYAxis.setEnabled(false);
        //修改value值类型，也可以自定方式
//        leftYAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                  return (int) value + "%";
//            }
//        });

        //设置y轴坐标之间 缩放值（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        leftYAxis.setGranularity(1f);
        // 4.X轴和Y轴类似，都具有相同的属性方法
        leftYAxis.setTextColor(Color.BLUE); //文字颜色
        //leftYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(R.color.color_5db5e6); //Y轴颜色
        //取消网格线
        //rightYAxis.setDrawGridLines(false);
        //leftYAxis.setDrawGridLines(false);
        // xAxis.setDrawGridLines(false);

        // 1.隐藏X轴描述
        //Description description2 = new Description();
        //description2.setEnabled(false);
        // mpaLineChart.setDescription(description2);
        // 2.设置X轴描述内容
        Description description = new Description();
        description.setText("科目成绩");
        description.setTextColor(Color.RED);
        mpaBarChart.setDescription(description);

        //2.设置显示MarkerView
        mpaBarChart.setMarker(new MPA_LineMarkerView(this));

        //1.得到Lengend 图例
        Legend legend = mpaBarChart.getLegend();
        //2.设置Lengend位置
        legend.setTextColor(Color.CYAN); //设置Legend 文本颜色
        //垂直 对准
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //水平 对准
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //方向 水平
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);



    }
    /**
     *  折线图
     */
    private void initLineChart() {
        //显示边界
        mpaLineChart.setDrawBorders(true);
        //获取数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "月份");
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(9f);
        //线模式为圆滑曲线（默认折线）
       // lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData data = new LineData(lineDataSet);
        mpaLineChart.setData(data);

        //得到X轴
        XAxis xAxis = mpaLineChart.getXAxis();
        //设置X轴的位置（默认在上方）：
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
       //设置X轴坐标之间 缩放值（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        xAxis.setGranularity(1f);
        //4.设置X轴的总刻度数量，均匀分配12f/12  为1:1
        xAxis.setLabelCount(12, true);
        //5.设置X轴 起始值、最终值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);//
        xAxis.setAxisMaximum(11f);

        //得到y轴
       // YAxis  yAxis = mpaLineChart.get();


        //6.设置X轴值为字符串（默认为上述5 中10  ）
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index=(int )value;
                return xlineList[index]+"";
            }
        });
       // 1.得到Y轴
        YAxis leftYAxis = mpaLineChart.getAxisLeft();
        YAxis rightYAxis = mpaLineChart.getAxisRight();
        //2.设置起始与终止值、间隔值
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);

        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setLabelCount(11, true);
        rightYAxis.setLabelCount(11, true);
        //右侧Y轴不显示
        rightYAxis.setEnabled(false);
        //修改value值类型，也可以自定方式
//        leftYAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                  return (int) value + "%";
//            }
//        });

        //设置y轴坐标之间 缩放值（因为此图有缩放功能，X轴,Y轴可设置可缩放）
        leftYAxis.setGranularity(1f);
        // 4.X轴和Y轴类似，都具有相同的属性方法
        leftYAxis.setTextColor(Color.BLUE); //文字颜色
        //leftYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(R.color.color_5db5e6); //Y轴颜色
        //取消网格线
         //rightYAxis.setDrawGridLines(false);
         //leftYAxis.setDrawGridLines(false);
        // xAxis.setDrawGridLines(false);

       // 1.隐藏X轴描述
        //Description description2 = new Description();
        //description2.setEnabled(false);
       // mpaLineChart.setDescription(description2);
       // 2.设置X轴描述内容
        Description description = new Description();
        description.setText("运动时间");
        description.setTextColor(Color.RED);
        mpaLineChart.setDescription(description);

       //2.设置显示MarkerView
        mpaLineChart.setMarker(new MPA_LineMarkerView(this));

        //1.得到Lengend 图例
        Legend legend = mpaLineChart.getLegend();
        //2.设置Lengend位置
        legend.setTextColor(Color.CYAN); //设置Legend 文本颜色
        //垂直 对准
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //水平 对准
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //方向 水平
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);


    }

}
