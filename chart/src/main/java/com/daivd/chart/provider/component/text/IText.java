package com.daivd.chart.provider.component.text;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/11/30.
 */

public interface IText {

    void drawText(Canvas canvas, String value, float x, float y, int position, int line, Paint paint);
}
