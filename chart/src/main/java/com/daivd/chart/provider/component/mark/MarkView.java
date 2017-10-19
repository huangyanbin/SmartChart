package com.daivd.chart.provider.component.mark;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.daivd.chart.data.LineData;

/**
 * Created by huang on 2017/9/28.
 */

public abstract class MarkView {

    private Rect rect;



    public void initMarkRect( Rect rect){
        this.rect = rect;
    }

    public abstract void drawMark(Canvas canvas,float x, float y, String content, LineData data, int position);




    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
