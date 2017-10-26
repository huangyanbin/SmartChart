package com.daivd.chart.listener;

import com.daivd.chart.component.Legend;
import com.daivd.chart.data.ColumnData;

/**
 * Created by huang on 2017/9/30.
 */

public interface OnClickLegendListener<C extends ColumnData> {

   void onClickLegend(C c, Legend<C> legend);
}
