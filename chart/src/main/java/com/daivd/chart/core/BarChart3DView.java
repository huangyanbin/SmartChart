package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.daivd.chart.provider.Bar3DProvider;
import com.daivd.chart.provider.BarProvider;

/**
 * Created by huang on 2017/9/26.
 */

public class BarChart3DView extends BaseChartView<Bar3DProvider> {

    public BarChart3DView(Context context) {
        super(context);
    }

    public BarChart3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart3DView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BarChart3DView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
        provider = new Bar3DProvider();

    }


    public int getGroupPadding() {
        return provider.getGroupPadding();
    }

    public void setGroupPadding(int groupPadding) {
        provider.setGroupPadding(groupPadding);
    }

}
