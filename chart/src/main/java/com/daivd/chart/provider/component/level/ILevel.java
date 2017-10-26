package com.daivd.chart.provider.component.level;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by huang on 2017/10/19.
 */

public interface ILevel {

    int getAxisDirection();

    void drawLevel(Canvas canvas, Rect rect, float y, Paint paint);

    double getValue();
}
