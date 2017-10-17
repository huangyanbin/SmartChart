package com.daivd.chart.axis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.IFormat;
import com.daivd.chart.data.LineData;
import com.daivd.chart.matrix.MatrixHelper;

/**
 * Created by huangYanBin on 2017/9/26.
 * 轴接口
 */

public interface IAxis<V> {

     void draw(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData<LineData> chartData);
     void computeScale(ChartData<LineData> chartData, Rect rect, Paint paint);
     void  setAxisDirection(AxisDirection axisDirection);
     void setFormat(IFormat<V> format);
     IFormat<V> getFormat();

}
