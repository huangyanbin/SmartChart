package com.daivd.chart.data;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;

/**
 * Created by huang on 2017/9/30.
 */

public class LevelLine {
    public static int left = 0;
    public static int right = 1;
    private LineStyle lineStyle = new LineStyle();
    private double value;
    private boolean isDraw;
    private int textDirection = right;
    private FontStyle textStyle = new FontStyle();
    private AxisDirection direction = AxisDirection.LEFT;

    public LevelLine(boolean isDraw,double value) {
        this.value = value;
        this.isDraw = isDraw;
    }

    public LevelLine( boolean isDraw,double value, LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        this.value = value;
        this.isDraw = isDraw;
    }

    public LevelLine( boolean isDraw,double value, LineStyle lineStyle, AxisDirection direction) {
        this.lineStyle = lineStyle;
        this.value = value;
        this.isDraw = isDraw;
        this.direction = direction;
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

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
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

    public AxisDirection getDirection() {
        return direction;
    }

    public void setDirection(AxisDirection direction) {
        this.direction = direction;
    }

    public void setTextStyle(FontStyle textStyle) {
        this.textStyle = textStyle;
    }
}
