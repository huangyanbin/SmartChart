package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/9/30.
 */

public interface IEmpty {

     void drawEmpty(Canvas canvas, Rect rect, Paint paint);

     FontStyle getFontStyle();

     void setFontStyle(FontStyle fontStyle) ;

     String getEmtpyTip();

     void setEmtpyTip(String emtpyTip);
}
