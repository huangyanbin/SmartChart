package com.daivd.chart.data;

import com.daivd.chart.component.base.IAxis;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class LineData extends ColumnData<List<Double>> {


    private int direction = IAxis.AxisDirection.LEFT;


    public LineData(String name, String unit, int color, List<Double> chartYDataList) {

        super(name,unit,color,chartYDataList);
    }
    public LineData(String name, String unit, int direction, int color, List<Double> datas) {
        super(name,unit,color,datas);
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

}
