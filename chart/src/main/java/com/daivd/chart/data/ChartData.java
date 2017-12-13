package com.daivd.chart.data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huang on 2017/9/26.
 * chart data
 */

public class ChartData<T extends ColumnData> {

    private String chartName;
    private List<String> charXDataList;
    private ScaleData scaleData = new ScaleData();
    private  List<T> columnDataList;

    public ChartData(String chartName,List<String> charXDataList, T... columnDataList){
        this.chartName = chartName;
        this.charXDataList = charXDataList;
        this.columnDataList = Arrays.asList(columnDataList);
    }

    public ChartData(String chartName,List<String> charXDataList, List<T> columnDataList){
        this.chartName = chartName;
        this.charXDataList = charXDataList;
        this.columnDataList = columnDataList;
    }
    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public boolean isEmpty() {
        return columnDataList== null || this.columnDataList.size() ==0 ;
    }

    public List<String> getCharXDataList() {
        return charXDataList;
    }

    public void setCharXDataList(List<String> charXDataList) {
        this.charXDataList = charXDataList;
    }

    public ScaleData getScaleData() {
        return scaleData;
    }

    public void setScaleData(ScaleData scaleData) {
        this.scaleData = scaleData;
    }

    public List<T> getColumnDataList() {
        return columnDataList;
    }

    public void setColumnDataList(List<T> columnDataList) {
        this.columnDataList = columnDataList;
    }
}
