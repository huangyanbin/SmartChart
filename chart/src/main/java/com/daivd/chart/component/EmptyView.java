package com.daivd.chart.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.component.base.IEmpty;
import com.daivd.chart.data.style.FontStyle;

/**
 * 空白提示
 * @author huangyanbin
 */

public class EmptyView implements IEmpty {
    /**
     * 空白提示字体样式
     */
    private FontStyle fontStyle = new FontStyle();
    /**
     * 空白文字
     */
    private String emptyTip = "No Data";
    /**
     * 空白范围
     */
    private Rect rect;

    /**
     * 绘制空白
     * @param canvas 画布
     * @param paint 画笔
     */
    @Override
    public void draw(Canvas canvas, Paint paint) {
        draw(canvas,emptyTip,paint);
    }

    /**
     * 获取空白字体样式
     * @return 空白字体样式
     */
    public FontStyle getFontStyle() {
        return fontStyle;
    }
    /**
     * 设置空白字体样式
     * @param fontStyle 空白字体样式
     */
    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }
    /**
     * 设置空白提示
     * @return   空白提示
     */
    @Override
    public String getEmptyTip() {
        return emptyTip;
    }
    /**
     * 获取空白提示
     * @param   emptyTip 空白提示
     */
    @Override
    public void setEmptyTip(String emptyTip) {
        this.emptyTip = emptyTip;
    }

    /**
     * 计算空白大小
     * <p>因为空白提示是占整个图表不需要计算</>
     * @param chartRect 图表范围
     */
    @Override
    public void computeRect(Rect chartRect) {
        rect = chartRect;
    }

    /**
     * 绘制空白
     * @param canvas 画布
     * @param emptyTip 空白提示
     * @param paint 画笔
     */
    @Override
    public void draw(Canvas canvas, String emptyTip, Paint paint) {
        fontStyle.fillPaint(paint);
        paint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        int textWidth = (int) paint.measureText(emptyTip);
        canvas.drawText(emptyTip,rect.left +(rect.right-rect.left-textWidth)/2,rect.top+(rect.bottom - rect.top-textHeight)/2,paint);
    }
}
