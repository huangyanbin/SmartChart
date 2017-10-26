package com.daivd.chart.data;

import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.provider.component.line.ILineModel;
import com.daivd.chart.provider.component.point.IPoint;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class LineData extends BarData {

    private IPoint point;
    private ILineModel lineModel;
    private LineStyle lineStyle;



    public LineData(String name, String unit, int color, List<Double> chartYDataList) {

        super(name,unit,color,chartYDataList);
    }
    public LineData(String name, String unit, int direction, int color, List<Double> datas) {
        super(name,unit,direction,color,datas);
    }

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    public ILineModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(ILineModel lineModel) {
        this.lineModel = lineModel;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }
}
