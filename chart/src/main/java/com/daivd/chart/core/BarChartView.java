package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.daivd.chart.provider.BarProvider;

/**
 * Created by huang on 2017/9/26.
 */

public class BarChartView extends BaseChartView<BarProvider> {

    public BarChartView(Context context) {
        super(context);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
        provider = new BarProvider();


    }

    public int getGroupPadding() {
        return provider.getGroupPadding();
    }

    public void setGroupPadding(int groupPadding) {
        provider.setGroupPadding(groupPadding);
    }

}
