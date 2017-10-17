package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
                titleRect.top = (int) (rect.height()*percent);
                break;
            case LEFT:
                titleRect.left = (int) (rect.width()*percent);
                break;
            case RIGHT:
                titleRect.right = (int) (rect.width()*percent);
                break;
            case BOTTOM:
                titleRect.bottom = (int) (rect.height()*percent);
                break;
        }
    }

    @Override
    public void drawTitle(Canvas canvas, Rect rect, Paint paint) {
        fontStyle.fillPaint(paint);
        int startX,startY;
        int textHeight = (int)paint.measureText("1",0,1);
        String chartName = chartData.getChartName();
        Path path = new Path();
        switch (titleDirection) {
            case TOP:
                startY = (int) (rect.top + rect.height() * percent/2);
                startX = rect.centerX();
                startY+= textHeight;
                startX -=textHeight * chartName.length();
                canvas.drawText(chartName, startX, startY, paint);
                break;
            case LEFT:

                startX = (int) (rect.left +  rect.width() * percent/2);
                startX -= textHeight;
                path.moveTo(startX,rect.top);
                path.lineTo(startX,rect.bottom);
                canvas.drawTextOnPath(chartName,path,rect.width()/2-textHeight * chartName.length()/2,0,paint);
                break;
            case RIGHT:
                startX = (int) (rect.right - rect.width()* percent/2);
                startX -= textHeight;
                path.moveTo(startX,rect.top);
                path.lineTo(startX,rect.bottom);
                canvas.drawTextOnPath(chartName,path,rect.width()/2-textHeight * chartName.length()/2,0,paint);
                break;
            case BOTTOM:
                startY = (int) (rect.bottom - rect.height() * percent/2);
                startX =  rect.centerX();
                startY+= textHeight;
                startX -=textHeight * chartName.length();
                canvas.drawText(chartName, startX, startY, paint);
                break;
        }

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
