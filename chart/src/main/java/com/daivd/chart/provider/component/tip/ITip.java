package com.daivd.chart.provider.component.tip;

import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * Created by huang on 2017/10/20.
 */

public interface ITip<C,S> {

    void drawTip(Canvas canvas, float x, float y, Rect rect, C content,int position);

    boolean isShowTip(C c,int position);

    S format(C c,int position);
}
