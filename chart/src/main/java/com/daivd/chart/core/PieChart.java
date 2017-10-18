package com.daivd.chart.core;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daivd.chart.data.PieData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.pie.PieProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public class PieChart  extends BaseRotateChart<PieProvider,PieData>{


    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PieChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected PieProvider initProvider(RotateHelper rotateHelper) {
        PieProvider provider =  new PieProvider();
        provider.setRotateHelper(rotateHelper);
        return provider;
    }



}
