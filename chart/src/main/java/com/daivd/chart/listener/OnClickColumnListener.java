package com.daivd.chart.listener;

import com.daivd.chart.data.ColumnData;
import com.daivd.chart.legend.ILegend;

/**
 * Created by huang on 2017/9/30.
 */

public interface OnClickColumnListener<C extends ColumnData> {

   void onClickColumn(C c,int position);
}
