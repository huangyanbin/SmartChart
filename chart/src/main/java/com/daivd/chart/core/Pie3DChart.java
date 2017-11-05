package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseRotateChart;
import com.daivd.chart.data.PieData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.pie.Pie3DProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public class Pie3DChart extends BaseRotateChart<Pie3DProvider,PieData> {


    public Pie3DChart(Context context) {
        super(context);
    }

    public Pie3DChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Pie3DChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected Pie3DProvider initProvider(RotateHelper rotateHelper) {
        Pie3DProvider provider =  new Pie3DProvider(getContext());
        provider.setRotateHelper(rotateHelper);
        return provider;
    }



}
