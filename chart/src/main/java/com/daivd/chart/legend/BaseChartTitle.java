package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/9/29.
 */

public class BaseChartTitle implements IChartTitle {

    private static final float DEFAULT_PERCENT = 0.1f;
    private FontStyle fontStyle= new FontStyle();
    private float percent = DEFAULT_PERCENT;
    protected int titleDirection = TOP;
    private ChartData chartData;

    @Override
    public void computeTitle(ChartData chartData, Rect rect, Paint paint) {
        this.chartData = chartData;
        Rect titleRect = chartData.getScaleData().titleRect;
        switch (titleDirection){
            case TOP:
                titleRect.top = (int) ((rect.bottom - rect.top)*percent);
                break;
            case LEFT:
                titleRect.left = (int) ((rect.right - rect.left)*percent);
                break;
            case RIGHT:
                titleRect.right = (int) ((rect.right - rect.left)*percent);
                break;
            case BOTTOM:
                titleRect.bottom = (int) ((rect.bottom - rect.top)*percent);
                break;
        }
    }

    @Override
    public void drawTitle(Canvas canvas, Rect rect, Paint paint) {

        int startX = 0,startY = 0;
        int textHeight = (int)paint.measureText("1",0,1);
        int offsetY = textHeight;
        switch (titleDirection) {
            case TOP:
                startY = (int) (rect.top + (rect.bottom - rect.top) * percent/2);
                startX = rect.left+(rect.right - rect.left)/2;
                break;
            case LEFT:
                startX = (int) (rect.left +  (rect.right - rect.left) * percent/2);
                startY =rect.top + (rect.bottom -rect.top)/2;
                break;
            case RIGHT:
                startX = (int) (rect.right - (rect.right - rect.left) * percent/2);
                startY =rect.top + (rect.bottom -rect.top)/2;
                break;
            case BOTTOM:
                startY = (int) (rect.bottom - (rect.bottom - rect.top) * percent/2);
                startX =  rect.left+(rect.right - rect.left)/2;
                break;
        }
        startY+=offsetY;
        String chartName = chartData.getChartName();
        startX -=textHeight * chartName.length();
        drawText(canvas,startX,startY,chartName,paint);
    }


    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, int startX, int startY, String  content, Paint paint) {
        fontStyle.fillPaint(paint);
        canvas.drawText(content, startX, startY, paint);
    }

    @Override
    public void setTitlePercent(float percent) {

        this.percent = percent;
        if(this.percent >0.3){
            this.percent = 0.3f;
        }
    }

    @Override
    public FontStyle getTextStyle() {
        return fontStyle;
    }

    @Override
    public void setTitleDirection(int titleDirection) {
        this.titleDirection = titleDirection;
    }




}
