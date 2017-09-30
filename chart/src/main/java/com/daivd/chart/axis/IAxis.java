package com.daivd.chart.axis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.matrix.ChartGestureObserver;
import com.daivd.chart.matrix.MatrixHelper;

/**
 * Created by huang on 2017/9/26.
 */

public interface IAxis {

     void draw(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData chartData);
     void computeScale(ChartData chartData,Rect rect,Paint paint);
     void  setAxisDirection(AxisDirection axisDirection);
     String formatVerticalAxisData(double value);
     String formatHorizontalAxisData(String value);

}
