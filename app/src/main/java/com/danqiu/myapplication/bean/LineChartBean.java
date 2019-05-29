package com.danqiu.myapplication.bean;

/**
 * Created by lzj on 2019/5/29
 * Describe ：注释
 */
public class LineChartBean {
    private float x;
    private float y;

    public LineChartBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "LineChartBean{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
