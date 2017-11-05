package com.daivd.chart.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import com.daivd.chart.core.base.BaseBarLineChart;
import com.daivd.chart.data.LineData;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.provider.barLine.LineProvider;
import com.daivd.chart.provider.component.line.CurveLineModel;
import com.daivd.chart.provider.component.line.BrokenLineModel;

/**
 * Created by huang on 2017/9/26.
 * 线性图
 */

public class LineChart extends BaseBarLineChart<LineProvider,LineData> {

    public static final int LINE_MODEL = 0;
    public static final  int CURVE_MODEL = 1;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected LineProvider initProvider() {
        horizontalAxis.setGravity(Gravity.LEFT);
        horizontalAxis.isLine(true);
        return new LineProvider();
    }

    public void setLineModel(int model){
        if(model == LINE_MODEL){
            provider.setLineModel(new BrokenLineModel());
        }else if( model == CURVE_MODEL){
            provider.setLineModel(new CurveLineModel());
        }else {
            throw new ChartException("Please set the correct Line model");
        }
    }

}
