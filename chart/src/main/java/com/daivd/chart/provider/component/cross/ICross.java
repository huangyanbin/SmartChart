package com.daivd.chart.provider.component.cross;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by huang on 2017/10/19.
 * 十字轴
 */

public interface ICross {

    void drawCross(Canvas canvas, PointF pointF, Rect rect, Paint paint);
}
