package com.daivd.chart.component.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/** 图表组件
 * Created by huang on 2017/10/26.
 */

public interface IComponent<T> {

    int LEFT = 0;
    int TOP = 1;
    int RIGHT =2;
    int BOTTOM = 3;
    /**
     * 计算组件Rect
     * @param chartRect
     */
    void computeRect(Rect chartRect);

    /**
     * 绘制组件
     * @param canvas 画布
     * @param  t 数据
     * @param paint 画笔
     */
    void draw(Canvas canvas, T t, Paint paint);
}
