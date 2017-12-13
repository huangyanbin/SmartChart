package com.daivd.chart.provider.component.point;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.daivd.chart.data.style.PointStyle;

/**
 * Created by huang on 2017/10/20.
 */

public class Point implements IPoint {

     private PointStyle pointStyle = new PointStyle();

    public void drawPoint(Canvas canvas,float x, float y,int position,boolean isShowDefaultColor, Paint paint){
         float w = pointStyle.getWidth();
        if(isShowDefaultColor){
            pointStyle.fillPaint(paint);
        }else {
            int oldColor =paint.getColor();
            pointStyle.fillPaint(paint);
            paint.setColor(oldColor);
        }
         if (pointStyle.getShape() == PointStyle.CIRCLE) {
              canvas.drawCircle(x, y, w/2, paint);
         } else if (pointStyle.getShape() == PointStyle.SQUARE) {
              canvas.drawRect(x - w/2 , y - w/2 , x + w/2, y + w/2 , paint);
         } else if (pointStyle.getShape() == PointStyle.RECT) {
              canvas.drawRect(x - w * 2 / 3, y - w/2 , x + w * 2 / 3, y + w/2, paint);
         }
     }





    public PointStyle getPointStyle() {
          return pointStyle;
     }
}
