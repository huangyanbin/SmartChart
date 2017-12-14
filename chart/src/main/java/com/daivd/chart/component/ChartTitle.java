package com.daivd.chart.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.component.base.IChartTitle;
import com.daivd.chart.component.base.PercentComponent;
import com.daivd.chart.data.style.FontStyle;

/**
 * 绘制标题
 * @author huangyanbin
 */

public class ChartTitle extends PercentComponent<String> implements IChartTitle {

    /**
     * 图表标题最大占比
     */
    private static final float MAX_PERCENT =0.4f;
    /**
     * 标题字体样式
     */
    private FontStyle fontStyle= new FontStyle();

    private Path path = new Path();

    /**
     * 设置标题占比
     * @param percent 百分比
     */
    @Override
    public void setPercent(float percent) {
        if(percent > MAX_PERCENT){
            percent = MAX_PERCENT;
        }
        super.setPercent(percent);
    }

    /**
     * 绘制标题
     * <p>通过设置标题方位绘制标题</p>
     * @param canvas 画布
     * @param chartName 图表标题
     * @param paint 画笔
     */
    @Override
    public void draw(Canvas canvas, String chartName, Paint paint) {
        fontStyle.fillPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        paint.setTextAlign(Paint.Align.LEFT);
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        int textWidth = (int)paint.measureText(chartName);
        Rect rect = getRect();
        int startY = rect.centerY();
        int startX = rect.centerX();
        path.rewind();
        switch (direction) {
            case TOP:
            case BOTTOM:
                startY-= textHeight/2;
                startX -=textWidth/2;
                canvas.drawText(chartName, startX, startY, paint);
                break;
            case LEFT:
            case RIGHT:
                path.moveTo(startX,rect.top);
                path.lineTo(startX,rect.bottom);
                canvas.drawTextOnPath(chartName,path,(rect.height()-textWidth)/2,0,paint);
                break;
        }
    }

    /**
     * 获取标题字体样式
     * @return 标题字体样式
     */
    public FontStyle getFontStyle() {
        return fontStyle;
    }
    /**
     * 设置标题字体样式
     * @param  fontStyle 标题字体样式
     */
    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }
}
