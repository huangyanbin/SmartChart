package com.daivd.chart.provider.component.grid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by huang on 2017/12/1.
 */

public interface IGrid {
   /**
    * 绘制网格
    * @param canvas
    * @param x
    * @param rect
    * @param perWidth
    * @param paint
    */
   void drawGrid(Canvas canvas, float x, Rect rect, int perWidth, Paint paint);

   /**
    * 绘制点击背景
    * @param canvas
    * @param pointF
    * @param rect
    * @param perWidth
    * @param paint
    */
   void drawClickBg(Canvas canvas, PointF pointF, Rect rect, int perWidth, Paint paint);

   /**
    * 绘制列内容
    * @param canvas
    * @param position
    * @param rect
    * @param perWidth
    * @param paint
    */
   void drawColumnContent(Canvas canvas, int position, Rect zoomRect, Rect rect, int perWidth, Paint paint);
}
