package com.daivd.chart.component.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/9/30.
 */

public interface IEmpty extends IComponent<String> {

     void draw(Canvas canvas,  Paint paint);

     FontStyle getFontStyle();

     void setFontStyle(FontStyle fontStyle) ;

     String getEmptyTip();

     void setEmptyTip(String emptyTip);
}
