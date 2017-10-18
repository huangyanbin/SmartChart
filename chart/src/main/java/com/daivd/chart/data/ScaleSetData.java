package com.daivd.chart.data;

/**
 * Created by huang on 2017/10/18.
 */

public class ScaleSetData {

    private boolean isStartZoom;
    private boolean isHasMaxValue;
    private boolean isHasMinValue;
    private double maxValue;
    private double minValue;

    public boolean isStartZoom() {
        return isStartZoom;
    }

    public void setStartZoom(boolean startZoom) {
        isHasMinValue = false;
        isStartZoom = startZoom;
    }

    public boolean isHasMaxValue() {
        return isHasMaxValue;
    }

    public boolean isHasMinValue() {
        return isHasMinValue;
    }


    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        isHasMaxValue = true;
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        isHasMinValue = true;
        this.minValue = minValue;
    }
}
