package com.daivd.chart.core;

import android.content.Context;
import android.util.AttributeSet;

import com.daivd.chart.core.base.BaseRotateChart;
import com.daivd.chart.data.RadarData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.radar.RadarProvider;

/**
 * Created by huang on 2017/10/9.
 * 雷达图
 */

public class RadarChart extends BaseRotateChart<RadarProvider,RadarData> implements RotateHelper.OnRotateListener{


    public RadarChart(Context context) {
        super(context);
    }

    public RadarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected RadarProvider initProvider(RotateHelper rotateHelper) {
        RadarProvider provider =  new RadarProvider();
        provider.setRotateHelper(rotateHelper);
        return provider;
    }
}
