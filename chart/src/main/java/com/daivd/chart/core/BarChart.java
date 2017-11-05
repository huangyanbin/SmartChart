package com.daivd.chart.core;

import android.content.Context;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseBarLineChart;
import com.daivd.chart.data.BarData;
import com.daivd.chart.provider.barLine.BarProvider;

/**
 * Created by huang on 2017/9/26.
 * 柱状图
 */

public class BarChart extends BaseBarLineChart<BarProvider<BarData>,BarData> {

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected BarProvider initProvider() {
        return new BarProvider();
    }


}
