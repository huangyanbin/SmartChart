package com.daivd.chart.data;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class ChartData {

    private String chartName;
    private List<String> charXDataList;
    private List<ColumnData> columnDataList;
    private ScaleData scaleData;

    public ChartData(String chartName, List<String> charXDataList, List<ColumnData> columnDataList) {
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

    public List<ColumnData> getColumnDataList() {
        return columnDataList;
    }

    public void setColumnDataList(List<ColumnData> columnDataList) {
        this.columnDataList = columnDataList;
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
}
