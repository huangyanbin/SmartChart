package com.daivd.chart.data;

import com.daivd.chart.component.base.IAxis;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class BarData extends ColumnData<List<Double>> {





    public BarData(String name, String unit, int color, List<Double> chartYDataList) {

        super(name,unit,color,chartYDataList);
    }
    public BarData(String name, String unit, int direction, int color, List<Double> datas) {
        super(name,unit,direction,color,datas);

    }


}
