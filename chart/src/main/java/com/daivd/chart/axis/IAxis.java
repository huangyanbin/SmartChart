package com.daivd.chart.axis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.LineData;
import com.daivd.chart.matrix.MatrixHelper;

/**
 * Created by huangYanBin on 2017/9/26.
 * 轴接口
 */

public interface IAxis<V> {

     void draw(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData<? extends LineData> chartData);
     void computeScale(ChartData<? extends LineData> chartData, Rect rect, Paint paint);
     void  setAxisDirection(int axisDirection);
     void setFormat(IFormat<V> format);
     IFormat<V> getFormat();

     /**
      * Created by huangYanBin on 2017/9/26.
      * 轴方向
      */

     interface AxisDirection {
         int TOP = 1;
         int BOTTOM=2;
         int LEFT = 3;
         int RIGHT = 4;
     }
}
