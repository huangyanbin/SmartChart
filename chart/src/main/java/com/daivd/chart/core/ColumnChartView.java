package com.daivd.chart.core;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;

import com.daivd.chart.provider.ColumnProvider;

/**
 * Created by huang on 2017/9/26.
 */

public class ColumnChartView extends BaseChartView<ColumnProvider> {

    public ColumnChartView(Context context) {
        super(context);
    }

    public ColumnChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColumnChartView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
        provider = new ColumnProvider();


    }

    public int getGroupPadding() {
        return provider.getGroupPadding();
    }

    public void setGroupPadding(int groupPadding) {
        provider.setGroupPadding(groupPadding);
    }

}
