package com.daivd.chart.matrix;

import android.view.MotionEvent;

import com.daivd.chart.core.base.BaseChart;

/**
 * Created by huang on 2017/10/18.
 */

public interface ITouch {
    /**
     * 用于判断是否请求不拦截事件
     * 解决手势冲突问题
     * @param chart
     * @param event
     */
    void onDisallowInterceptEvent(BaseChart chart, MotionEvent event);

    /**
     * 处理touchEvent
     * @param event
     */
    boolean handlerTouchEvent(MotionEvent event);
}
