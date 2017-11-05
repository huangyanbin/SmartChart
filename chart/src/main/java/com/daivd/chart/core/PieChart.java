package com.daivd.chart.core;

import android.content.Context;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseRotateChart;
import com.daivd.chart.data.PieData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.pie.PieProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public class PieChart  extends BaseRotateChart<PieProvider,PieData> {


    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected PieProvider initProvider(RotateHelper rotateHelper) {
        PieProvider provider =  new PieProvider();
        provider.setRotateHelper(rotateHelper);
        return provider;
    }



}
