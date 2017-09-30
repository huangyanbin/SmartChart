package com.daivd.chart.core;

import com.daivd.chart.data.ColumnData;
import com.daivd.chart.legend.ILegend;

/**
 * Created by huang on 2017/9/30.
 */

public interface OnClickChartListener  {

     void clickLegend(ILegend legend,ColumnData columnData);
}
