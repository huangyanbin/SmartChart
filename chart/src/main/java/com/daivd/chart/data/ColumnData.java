package com.daivd.chart.data;

import com.daivd.chart.axis.AxisDirection;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class ColumnData {

    public String name;

    private String unit;
    private int color;
    private boolean isDraw = true;
    private AxisDirection  direction = AxisDirection.LEFT;
    private List<Double> chartYDataList;

    public ColumnData(String name, String unit, int color, List<Double> chartYDataList) {
        this.name = name;
        this.unit = unit;
        this.color = color;
        this.chartYDataList = chartYDataList;
    }
    public ColumnData(String name, String unit, AxisDirection direction, int color, List<Double> datas) {

        this.name = name;
        this.unit = unit;
        this.color = color;
        this.chartYDataList = datas;
        if(direction == AxisDirection.LEFT || direction == AxisDirection.RIGHT){
           this.direction = direction;
        }
    }

    public AxisDirection getDirection() {
        return direction;
    }

    public void setDirection(AxisDirection direction) {
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

    public List<Double> getChartYDataList() {
        return chartYDataList;
    }

    public void setChartYDataList(List<Double> chartYDataList) {
        this.chartYDataList = chartYDataList;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
