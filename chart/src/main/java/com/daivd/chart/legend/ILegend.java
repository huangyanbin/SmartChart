package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.core.OnClickChartListener;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;

/**
 * Created by huang on 2017/9/29.
 * 图例
 */

public interface ILegend {


    int LEFT = 0;
    int TOP = 1;
    int RIGHT =2;
    int BOTTOM = 3;
    /**
     * 计算图例大小
     */
    void computeLegend(ChartData chartData, Rect rect, Paint paint);

    /**
     * 绘制图例
     */
    void drawLegend(Canvas canvas, Rect rect, Paint paint);

    void  setLegendPercent(float percent);

    FontStyle getTextStyle();

    PointStyle getLegendStyle();

    void setLegendDirection(int legendDirection);

    void onClickLegend(PointF pointF);
    void setOnClickChartListener(OnClickChartListener onClickChartListener);
}
