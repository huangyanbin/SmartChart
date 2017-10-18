package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/9/30.
 */

public class EmptyView implements IEmpty{
    private FontStyle fontStyle = new FontStyle();
    private String emtpyTip = "No Data";

    public void drawEmpty(Canvas canvas, Rect rect, Paint paint){
        fontStyle.fillPaint(paint);
        int textHeight = (int) paint.measureText("1",0,1)*2;
        int textWidth = textHeight * emtpyTip.length();
        canvas.drawText(emtpyTip,rect.left +(rect.right-rect.left-textWidth)/2,rect.top+(rect.bottom - rect.top-textHeight)/2,paint);
    }
    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getEmtpyTip() {
        return emtpyTip;
    }

    public void setEmtpyTip(String emtpyTip) {
        this.emtpyTip = emtpyTip;
    }
}
