package com.daivd.chart.data;

import com.daivd.chart.component.base.IAxis;

/**
 * Created by huang on 2017/9/26.
 */

public class PieData extends ColumnData<Double> {


    public PieData(String name, String unit, int color, Double chartYDataList) {

        super(name, unit, color, chartYDataList);
    }

    public PieData(String name, String unit, IAxis.AxisDirection direction, int color, Double datas) {
        super(name, unit, color, datas);

    }


}
