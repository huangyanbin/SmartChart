package com.daivd.chart.data;

import java.util.List;

/**
 * Created by huang on 2017/10/19.
 */

public class BarLineData extends BarData {
    public static final int BROKEN= 0;
    public static final int CURVE = 1;
    public static final int BAR = 2;

    private int model = BAR;

    public BarLineData(String name, String unit, int color, List<Double> chartYDataList) {
        super(name, unit, color, chartYDataList);
    }

    public BarLineData(String name, int model,String unit, int color,List<Double> chartYDataList) {
        super(name, unit, color, chartYDataList);
        this.model = model;
    }
    public BarLineData(String name, String unit, int direction, int color, List<Double> datas) {
        super(name, unit, direction, color, datas);
    }


    public BarLineData(String name,int model, String unit, int direction, int color, List<Double> datas) {
        super(name, unit, direction, color, datas);
        this.model = model;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
