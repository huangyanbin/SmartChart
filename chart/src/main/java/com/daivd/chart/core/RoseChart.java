package com.daivd.chart.core;

import android.content.Context;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseRotateChart;
import com.daivd.chart.data.RoseData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.rose.RoseProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public class RoseChart extends BaseRotateChart<RoseProvider,RoseData> {


    public RoseChart(Context context) {
        super(context);
    }

    public RoseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected RoseProvider initProvider(RotateHelper rotateHelper) {
        RoseProvider provider =  new RoseProvider();
        provider.setRotateHelper(rotateHelper);
        return provider;
    }
}
