package com.daivd.chart.mark;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Rect;

import com.daivd.chart.data.ColumnData;
import com.david.chart.R;

/**
 * Created by huang on 2017/9/28.
 */

public abstract class MarkView {

    private Canvas canvas;
    private Rect rect;



    public void init(Canvas canvas, Rect rect){
        this.canvas = canvas;
        this.rect = rect;
    }

    public abstract void drawMark(float x, float y, String content, ColumnData data, int position);



    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
