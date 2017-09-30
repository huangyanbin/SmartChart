package com.daivd.chart.matrix;

/**
 * Created by huang on 2017/9/29.
 */

public interface ChartGestureObserver {

    void onClick(float x,float y);

    void onViewChanged(float scale,float translateX,float translateY);
}
