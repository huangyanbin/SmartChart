package com.daivd.chart.group.point;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by huang on 2017/10/20.
 */

public interface IPoint {

     void drawPoint(Canvas canvas, float x,float y,boolean isShowDefaultColor,Paint paint);
     float getWidth();
     float getHeight();

}
