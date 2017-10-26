package com.daivd.chart.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.component.base.IEmpty;
import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/9/30.
 * 空白提示
 */

public class EmptyView implements IEmpty {
    private FontStyle fontStyle = new FontStyle();
    private String emptyTip = "No Data";
    private Rect rect;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        draw(canvas,emptyTip,paint);
    }

    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    @Override
    public String getEmptyTip() {
        return emptyTip;
    }

    @Override
    public void setEmptyTip(String emptyTip) {
        this.emptyTip = emptyTip;
    }


    @Override
    public void computeRect(Rect chartRect) {
        rect = chartRect;
    }

    @Override
    public void draw(Canvas canvas, String emptyTip, Paint paint) {
        fontStyle.fillPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        int textWidth = (int) paint.measureText(emptyTip);
        canvas.drawText(emptyTip,rect.left +(rect.right-rect.left-textWidth)/2,rect.top+(rect.bottom - rect.top-textHeight)/2,paint);
    }
}
