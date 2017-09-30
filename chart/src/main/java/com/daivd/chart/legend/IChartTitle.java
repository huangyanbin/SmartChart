package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;

/**
 * Created by huang on 2017/9/29.
 * 图例
 */

public interface IChartTitle {


    int LEFT = 0;
    int TOP = 1;
    int RIGHT =2;
    int BOTTOM = 3;
    /**
     * 计算图例大小
     */
    void computeTitle(ChartData chartData, Rect rect, Paint paint);

    /**
     * 绘制图例
     */
    void drawTitle(Canvas canvas, Rect rect, Paint paint);

    void  setTitlePercent(float percent);

    FontStyle getTextStyle();

    void setTitleDirection(int titleDirection);
}
