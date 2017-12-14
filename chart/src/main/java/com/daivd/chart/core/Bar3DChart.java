package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseBarLineChart;
import com.daivd.chart.data.BarData;
import com.daivd.chart.provider.barLine.Bar3DProvider;

/**
 * Created by huang on 2017/9/26.
 * 3DBar图表
 */

public class Bar3DChart extends BaseBarLineChart<Bar3DProvider,BarData> {

    public Bar3DChart(Context context) {
        super(context);
    }

    public Bar3DChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Bar3DChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }





    @Override
    protected Bar3DProvider initProvider() {
        return  new Bar3DProvider();
    }



}
