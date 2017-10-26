package com.daivd.chart.provider.component.level;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;

/**
 * Created by huang on 2017/9/30.
 * LevelLine
 */

public class LevelLine implements ILevel{
    public static int left = 0;
    public static int right = 1;
    private LineStyle lineStyle = new LineStyle();
    private double value;
    private int textDirection = right;
    private FontStyle textStyle = new FontStyle();
    private int direction = IAxis.AxisDirection.LEFT;

    public LevelLine(double value) {
        this.value = value;
    }


    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public int getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(int textDirection) {
        this.textDirection = textDirection;
    }

    public FontStyle getTextStyle() {
        return textStyle;
    }

    public int getAxisDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setTextStyle(FontStyle textStyle) {
        this.textStyle = textStyle;
    }



    @Override
    public void drawLevel(Canvas canvas, Rect rect, float y, Paint paint) {
        getLineStyle().fillPaint(paint);
        Path path = new Path();
        path.moveTo(rect.left, y);
        path.lineTo(rect.right, y);
        canvas.drawPath(path, paint);
        getTextStyle().fillPaint(paint);
        float textHeight = paint.measureText("1", 0, 1);
        float startX;
        float startY = y - textHeight + getLineStyle().getWidth();
        String levelLineValue = String.valueOf(getValue());
        if (getTextDirection() == LevelLine.left) {
            startX = rect.left;
        } else {
            startX = rect.right - textHeight * levelLineValue.length();
        }
        canvas.drawText(levelLineValue, startX, startY, paint);
    }
}
