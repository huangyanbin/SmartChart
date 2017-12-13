package com.daivd.chart.provider.component.point;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/10/20.
 */

public interface IPoint {

     void drawPoint(Canvas canvas, float x, float y, int position, boolean isShowDefaultColor, Paint paint);


}
