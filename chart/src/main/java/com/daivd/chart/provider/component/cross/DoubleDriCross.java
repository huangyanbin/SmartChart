package com.daivd.chart.provider.component.cross;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.style.LineStyle;

/**
 * Created by huang on 2017/10/19.
 * 双方向
 */

public class DoubleDriCross implements ICross{

    LineStyle crossStyle = new LineStyle();
    @Override
    public void drawCross(Canvas canvas, PointF pointF, Rect rect, Paint paint) {
        crossStyle.fillPaint(paint);
        canvas.drawLine(pointF.x, rect.top, pointF.x, rect.bottom, paint);
        canvas.drawLine(rect.left, pointF.y, rect.right, pointF.y, paint);
    }

    public LineStyle getCrossStyle() {
        return crossStyle;
    }

    public void setCrossStyle(LineStyle crossStyle) {
        this.crossStyle = crossStyle;
    }
}
