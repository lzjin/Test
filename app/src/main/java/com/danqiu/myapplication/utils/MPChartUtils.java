package com.danqiu.myapplication.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import com.danqiu.myapplication.R;
import com.danqiu.myapplication.views.MPA_LineMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzj on 2019/7/24
 * Describe ：统计图 工具类
 */
public class MPChartUtils {

    /**
     * 不显示无数据的提示
     *
     * @param mChart
     */
    public static void NotShowNoDataText(Chart mChart) {
        mChart.clear();
        mChart.notifyDataSetChanged();
        mChart.setNoDataText("你还没有记录数据");
        mChart.setNoDataTextColor(Color.WHITE);
        mChart.invalidate();
    }



    /**
     *  直线图或者曲线图  图表数据
     *
     * @param mContext
     * @param mLineChart
     * @param entries
     * @param label
     * @param xLabels
     */
    public static void initLineChartData(Activity mContext, String label,LineChart mLineChart, List<Entry> entries,  final List<String> xLabels) {

        //创建一条线 可多条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        //线模式为 圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//CUBIC_BEZIER
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        //线条 十字线是否显示
        lineDataSet.setDrawHighlightIndicators(true);
        //线条 十字线颜色
        lineDataSet.setHighLightColor(mContext.getResources().getColor(R.color.red));
        //线条 圆点半径
        lineDataSet.setCircleRadius(3.5f);
        //线条 圆点颜色
        lineDataSet.setCircleColor(mContext.getResources().getColor(R.color.red));
        //线条 显示围成的阴影
        lineDataSet.setDrawFilled(true);
        //线条 阴影颜色
        lineDataSet.setFillColor(mContext.getResources().getColor(R.color.black));
        //线条 阴影透明值
        lineDataSet.setFillAlpha(50);
        //线条 颜色
        lineDataSet.setColor(mContext.getResources().getColor(R.color.blue3bafd9));
        //线条 显示坐标值
        lineDataSet.setDrawValues(true);
        //线条 显示坐标值 字体大小
        lineDataSet.setValueTextSize(12f);
        //线条 坐标字体颜色
        lineDataSet.setValueTextColor(mContext.getResources().getColor(R.color.blue));


        // 图例样式
        Legend legend = mLineChart.getLegend();
        legend.setEnabled(true);
        //(CIRCLE圆形；LINE线性；SQUARE是方块）
        legend.setForm(Legend.LegendForm.SQUARE);
        //垂直 对准
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //水平 对准
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //方向 水平
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextColor(mContext.getResources().getColor(R.color.red)); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小


        //得到X轴
        XAxis xAxis = mLineChart.getXAxis();
        //X轴是否显示
        xAxis.setDrawAxisLine(true);
        //x轴线的颜色
        xAxis.setAxisLineColor(mContext.getResources().getColor(R.color.black));
        //X轴线宽
        xAxis.setAxisLineWidth(2);
        //X轴绘制 标签
        xAxis.setDrawLabels(true);
        //X轴label文字 大小
        xAxis.setTextSize(12);
        //X轴label颜色
        xAxis.setTextColor(mContext.getResources().getColor(R.color.blue));
        //X轴label位置（默认在上方）：//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X轴坐标之间 缩放值
        xAxis.setGranularity(1f);
        //X轴的总刻度数量，均匀分配12f/12  为1:1
        xAxis.setLabelCount(12, true);//划分刻度值
        xAxis.setAxisMinimum(0f);//最小值
        xAxis.setAxisMaximum(11f);//最大值

        //x轴修改 label标签
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index=(int )value;
                return xLabels.get(index)+"";
            }
        });


        //Y轴左边
        YAxis leftYAxis = mLineChart.getAxisLeft();
        //Y轴左边 是否显示
        leftYAxis.setDrawAxisLine(true);
        //Y轴左边 颜色
        leftYAxis.setAxisLineColor(mContext.getResources().getColor(R.color.black));
        //Y轴左边 线宽
        leftYAxis.setAxisLineWidth(2);
        //Y轴左边 绘制标签
        xAxis.setDrawLabels(true);
        //Y轴左边 文字的大小
        leftYAxis.setTextSize(12);
        //Y轴左边 文字颜色
        leftYAxis.setTextColor(mContext.getResources().getColor(R.color.blue));
        ///Y轴左边 是否显示y轴坐标线
        leftYAxis.setDrawZeroLine(false);
        //Y轴左边 坐标之间 缩放值
        leftYAxis.setGranularity(1f);
        //Y轴左边起始与终止值、间隔值
        leftYAxis.setAxisMinimum(0f);//最小值
        leftYAxis.setAxisMaximum(100f);//最大值
        leftYAxis.setLabelCount(6, true);//划分刻度值 这里为20

        //Y轴修改 value值类型，也可以自定方式
//        leftYAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                  return (int) value + "%";
//            }
//        });

        //Y轴右边  同上
        YAxis rightYAxis = mLineChart.getAxisRight();
        rightYAxis.setDrawAxisLine(true);
        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        rightYAxis.setLabelCount(11, true);


        //--------------------------总配置-----------------------------------
        //Y轴右侧 不显示
        rightYAxis.setEnabled(false);
        rightYAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        rightYAxis.setDrawGridLines(false);//Y网格线显示

        //Y轴左侧 不显示
        leftYAxis.setEnabled(true);//y轴显示
        leftYAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        leftYAxis.setDrawGridLines(false);//Y网格线显示

        //X轴方向
        xAxis.setEnabled(true);//X轴显示
        xAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        xAxis.setDrawGridLines(false);//X网格线显示

        //X轴label描述
        Description description = new Description();
        description.setEnabled(true);
        description.setText("运动时间");
        description.setTextSize(14);
        description.setTextColor(mContext.getResources().getColor(R.color.red));
        mLineChart.setDescription(description);//设置label描述

        mLineChart.setMarker(new MPA_LineMarkerView(mContext));//设置游标MarkerView
        mLineChart.setDrawBorders(false); //显示边界
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
        mLineChart.animateX(1000);//绘制动画
        mLineChart.invalidate();
    }


    /**
     *  饼状图  图表数据
     * @param mContext
     * @param labelText 描述
     * @param pieChart
     * @param pieEntrys
     * @param colors
     * @param isHole 是否环形
     */
    public static void initPieChartData(Activity mContext,String labelText,PieChart pieChart,List<PieEntry> pieEntrys, List<Integer> colors,boolean isHole) {
        //饼状 数据
        PieDataSet dataset = new PieDataSet(pieEntrys, labelText);
        //填充每个区域的颜色
        dataset.setColors(colors);
        //是否在图上显示数值
        dataset.setDrawValues(true);
        //文字的大小
        dataset.setValueTextSize(14);
        //文字的颜色
        dataset.setValueTextColor(mContext.getResources().getColor(R.color.black));
        //文字的样式
        dataset.setValueTypeface(Typeface.DEFAULT_BOLD);
        //Y值位置   圆内、圆外
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//OUTSIDE_SLICE 外 , INSIDE_SLICE 内
        //Y值位置  外时，表示线的前半段长度。
        dataset.setValueLinePart1Length(0.4f);
        //Y值位置  外时，表示线的后半段长度。
        dataset.setValueLinePart2Length(0.6f);
        //Y值位置  外时，表示线的颜色。
        dataset.setValueLineColor(mContext.getResources().getColor(R.color.black));
        //Y值位置  外时，连接线 起始位置(0-100)
        dataset.setValueLinePart1OffsetPercentage(70f);

        //设置Y轴描述线和填充区域的颜色一致
        dataset.setUsingSliceColorAsValueLineColor(false);
        //设置每条之前的间隙
        dataset.setSliceSpace(0);


        //每部分 文字描述
        pieChart.setDrawEntryLabels(false);
        pieChart.setEntryLabelColor(mContext.getResources().getColor(R.color.red)); //描述文字的颜色
        pieChart.setEntryLabelTextSize(14);//描述文字的大小
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD); //描述文字的样式

        //环形
        pieChart.setDrawHoleEnabled(isHole);
        if(isHole){
            pieChart.setDrawCenterText(isHole);//中间文字 显示
            pieChart.setHoleRadius(40f);//半径
            pieChart.setCenterText(labelText); //设置中间文字
            pieChart.setCenterTextColor(mContext.getResources().getColor(R.color.black)); //中间问题的颜色
            pieChart.setCenterTextSizePixels(16);  //中间文字的大小
            pieChart.setCenterTextRadiusPercent(1f);
            pieChart.setCenterTextOffset(0, 0); //中间文字的偏移量
        }


        //描述
        Description description = new Description();
        description.setEnabled(true);
        description.setText("测试卷2");
        description.setTextSize(16);
        description.setTextColor(mContext.getResources().getColor(R.color.red));
        pieChart.setDescription(description);//设置label描述


        //图例
        Legend legend = pieChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //水平显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //顶部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //右对其
        legend.setTextColor(mContext.getResources().getColor(R.color.red)); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小

        legend.setXEntrySpace(10f);//x轴的间距
        legend.setYEntrySpace(10f); //y轴的间距
        legend.setYOffset(30f);  //图例的y偏移量
        legend.setXOffset(10f);  //图例x的偏移量
        // legend.setWordWrapEnabled(true);  //横向 换行
        //legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT); //从左到右



        //--------------------------总配置-----------------------------------
        // 半透明圈
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setTransparentCircleColor(Color.RED); //设置半透明圆圈的颜色
        pieChart.setTransparentCircleAlpha(125); //设置半透明圆圈的透明度


        pieChart.setExtraOffsets(20, 10, 75, 10); //图相对于上下左右的偏移
        pieChart.setBackgroundColor(mContext.getResources().getColor(R.color.grayf9f8f8)); //图标的背景色
        pieChart.setDragDecelerationFrictionCoef(0.75f);//设置pieChart图表转动阻力摩擦系数[0,1]
        pieChart.setRotationAngle(0);// 初始旋转角度
        pieChart.setRotationEnabled(true);// 可以手动旋转


        dataset.setSelectionShift(5f);//设置饼状Item被选中时变化的距离
        PieData pieData = new PieData(dataset); //填充数据
        pieData.setValueFormatter(new PercentFormatter());//格式化显示的数据为%百分比
        pieChart.setUsePercentValues(true);//显示成百分比
        pieChart.animateY(1000, Easing.EaseInBack); //设置动画
        pieChart.setData(pieData);//显示饼图
        pieChart.invalidate();
    }

    /**
     *  柱状图 图表数据
     * @param mContext
     * @param labelText
     * @param mpaBarChart
     * @param entries
     * @param xLabels
     */
    public static void initBarChartData(Activity mContext,String labelText,BarChart mpaBarChart, List<BarEntry> entries, final List<String> xLabels) {
        //创建柱状
        BarDataSet barDataSet = new BarDataSet(entries, labelText);
        //barData   字体颜色
        barDataSet.setValueTextColor(mContext.getResources().getColor(R.color.black));
        ///barData  字体大小
        barDataSet.setValueTextSize(14);
        //摄住柱状图颜色
        barDataSet.setColor(mContext.getResources().getColor(R.color.blue3bafd9));
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);


        // 图例样式
        Legend legend = mpaBarChart.getLegend();
        legend.setEnabled(true);
        //(CIRCLE圆形；LINE线性；SQUARE是方块）
        legend.setForm(Legend.LegendForm.SQUARE);
        //垂直 对准
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //水平 对准
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //方向 水平
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextColor(mContext.getResources().getColor(R.color.red)); //图例文字的颜色
        legend.setTextSize(13);  //图例文字的大小


        //得到X轴
        XAxis xAxis = mpaBarChart.getXAxis();
        //X轴是否显示
        xAxis.setDrawAxisLine(true);
        //x轴线的颜色
        xAxis.setAxisLineColor(mContext.getResources().getColor(R.color.black));
        //X轴线宽
        xAxis.setAxisLineWidth(2);
        //X轴绘制 标签
        xAxis.setDrawLabels(true);
        //X轴label文字 大小
        xAxis.setTextSize(12);
        //X轴label颜色
        xAxis.setTextColor(mContext.getResources().getColor(R.color.blue));
        //X轴label位置（默认在上方）：//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X轴坐标之间 缩放值
        xAxis.setGranularity(1f);
        //X轴的总刻度数量，均匀分配5f/5  为1:1
        xAxis.setLabelCount(7, true);//划分刻度值
        xAxis.setAxisMinimum(0f);//最小值
        xAxis.setAxisMaximum(6f);//最大值

        //x轴修改 label标签
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index=(int )value;
                return xLabels.get(index)+"";
            }
        });


        //Y轴左边
        YAxis leftYAxis = mpaBarChart.getAxisLeft();
        //Y轴左边 是否显示
        leftYAxis.setDrawAxisLine(true);
        //Y轴左边 颜色
        leftYAxis.setAxisLineColor(mContext.getResources().getColor(R.color.black));
        //Y轴左边 线宽
        leftYAxis.setAxisLineWidth(2);
        //Y轴左边 绘制标签
        xAxis.setDrawLabels(true);
        //Y轴左边 文字的大小
        leftYAxis.setTextSize(12);
        //Y轴左边 文字颜色
        leftYAxis.setTextColor(mContext.getResources().getColor(R.color.blue));
        ///Y轴左边 是否显示y轴坐标线
        leftYAxis.setDrawZeroLine(false);
        //Y轴左边 坐标之间 缩放值
        leftYAxis.setGranularity(1f);
        //Y轴左边起始与终止值、间隔值
        leftYAxis.setAxisMinimum(0f);//最小值
        leftYAxis.setAxisMaximum(100f);//最大值
        leftYAxis.setLabelCount(6, true);//划分刻度值 这里为20

        //Y轴修改 value值类型，也可以自定方式
//        leftYAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                  return (int) value + "%";
//            }
//        });

        //Y轴右边  同上
        YAxis rightYAxis = mpaBarChart.getAxisRight();
        rightYAxis.setDrawAxisLine(true);
        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        rightYAxis.setLabelCount(6, true);



        //--------------------------总配置-----------------------------------
        //Y轴右侧 不显示
        rightYAxis.setEnabled(false);
        rightYAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        rightYAxis.setDrawGridLines(false);//Y网格线显示

        //Y轴左侧 不显示
        leftYAxis.setEnabled(true);//y轴显示
        leftYAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        leftYAxis.setDrawGridLines(true);//Y网格线显示

        //X轴方向
        xAxis.setEnabled(true);//X轴显示
        xAxis.setGridColor(mContext.getResources().getColor(R.color.red));
        xAxis.setDrawGridLines(true);//X网格线显示

        //X轴label描述
        Description description = new Description();
        description.setEnabled(true);
        description.setText("收入");
        description.setTextSize(14);
        description.setTextColor(mContext.getResources().getColor(R.color.red));
        mpaBarChart.setDescription(description);//设置label描述

        mpaBarChart.setMarker(new MPA_LineMarkerView(mContext));//设置游标MarkerView
        mpaBarChart.setDrawBorders(false); //显示边界
        BarData barData = new BarData(dataSets);
        mpaBarChart.setData(barData);
        mpaBarChart.animateX(1000);//绘制动画
        mpaBarChart.invalidate();

    }
}
