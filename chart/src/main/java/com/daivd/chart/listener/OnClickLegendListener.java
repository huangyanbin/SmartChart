package com.daivd.chart.listener;

import com.daivd.chart.data.ColumnData;
import com.daivd.chart.legend.ILegend;

/**
 * Created by huang on 2017/9/30.
 */

public interface OnClickLegendListener<C extends ColumnData> {

   void onClickLegend(C c, ILegend<C> legend);
}
