package com.daivd.chart.provider.component.cross;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.provider.component.point.Point;

/**
 * Created by huang on 2017/10/19.
 * 竖轴方向
 */

public class VerticalCross implements ICross{

    LineStyle crossStyle = new LineStyle();
    IPoint point = new Point();
    @Override
    public void drawCross(Canvas canvas, PointF pointF, Rect rect, Paint paint) {
        crossStyle.fillPaint(paint);
        canvas.drawLine(pointF.x, rect.top, pointF.x, rect.bottom, paint);
        if(point != null){
            point.drawPoint(canvas,pointF.x,pointF.y,0,false,paint);
        }
    }

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    public LineStyle getCrossStyle() {
        return crossStyle;
    }

    public void setCrossStyle(LineStyle crossStyle) {
        this.crossStyle = crossStyle;
    }
}
