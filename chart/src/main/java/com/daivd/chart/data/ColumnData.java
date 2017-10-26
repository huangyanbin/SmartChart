package com.daivd.chart.data;

import com.daivd.chart.component.base.IAxis;

/**
 * Created by huang on 2017/9/26.
 */

public class ColumnData<T> {

    public String name;

    private String unit;
    private int color;
    private boolean isDraw = true;
    private int direction = IAxis.AxisDirection.LEFT;
    private T chartYDataList;


    public ColumnData(String name, String unit, int color, T chartYDataList) {
        this.name = name;
        this.unit = unit;
        this.color = color;
        this.chartYDataList = chartYDataList;
    }
    public ColumnData(String name, String unit, int direction, int color, T datas) {

        this.name = name;
        this.unit = unit;
        this.color = color;
        this.chartYDataList = datas;
        if(direction == IAxis.AxisDirection.LEFT || direction == IAxis.AxisDirection.RIGHT){
           this.direction = direction;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public T getChartYDataList() {
        return chartYDataList;
    }

    public void setChartYDataList(T chartYDataList) {
        this.chartYDataList = chartYDataList;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
