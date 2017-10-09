package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;

import com.daivd.chart.exception.ChartException;
import com.daivd.chart.provider.BarLineProvider;
import com.daivd.chart.provider.CurveProvider;
import com.daivd.chart.provider.LineProvider;

/**
 * Created by huang on 2017/9/26.
 */

public class LineChartView extends BaseChartView<BarLineProvider> {

    public static final int LINE_MODEL = 0;
    public static final  int CURVE_MODEL = 1;

    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
        provider = new LineProvider();
       horizontalAxis.setGravity(Gravity.LEFT);
    }

    public void setLineModel(int model){
        if(model == LINE_MODEL){
            provider = new LineProvider();
        }else if( model == CURVE_MODEL){
            provider = new CurveProvider();
        }else {
            throw new ChartException("请设置正确的Line model");
        }
    }


}
