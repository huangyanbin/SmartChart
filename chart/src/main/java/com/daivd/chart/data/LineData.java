package com.daivd.chart.data;

import com.daivd.chart.axis.AxisDirection;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class LineData extends ColumnData<List<Double>> {


    private AxisDirection  direction = AxisDirection.LEFT;


    public LineData(String name, String unit, int color, List<Double> chartYDataList) {

        super(name,unit,color,chartYDataList);
    }
    public LineData(String name, String unit, AxisDirection direction, int color, List<Double> datas) {
        super(name,unit,color,datas);
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

}
