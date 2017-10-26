package com.bin.david.smartchart.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.daivd.chart.provider.component.point.IPoint;

/**
 * Created by huang on 2017/10/26.
 */

public class StarPoint implements IPoint {
    private Path mPath;
    private int radius;
    private int color;

    public StarPoint(int radius,int color){
        this.radius = radius;
        this.color = color;
        this.mPath =new Path();
    }

    @Override
    public void drawPoint(Canvas canvas, float x, float y, boolean isShowDefaultColor, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        mPath.reset();
        float[] floats =  fivePoints(x,y-radius/2,radius);
        for (int i = 0; i < floats.length - 1; i++) {
            if(i == 0){
                mPath.moveTo(floats[i], floats[i += 1]);
            }else {
                mPath.lineTo(floats[i], floats[i += 1]);
            }
        }
        mPath.close();
        canvas.drawPath(mPath,paint);
    }

    @Override
    public float getWidth() {
        return radius;
    }

    @Override
    public float getHeight() {
        return radius;
    }

    /**
     * 五角星Path
     * @return
     */
    public static float[] fivePoints(float xA, float yA, int rFive) {
        float xB = 0;
        float xC = 0;
        float xD = 0;
        float xE = 0;
        float yB = 0;
        float yC = 0;
        float yD = 0;
        float yE = 0;
        xD = (float) (xA - rFive * Math.sin(Math.toRadians(18)));
        xC = (float) (xA + rFive * Math.sin(Math.toRadians(18)));
        yD = yC = (float) (yA + Math.cos(Math.toRadians(18)) * rFive);
        yB = yE = (float) (yA + Math.sqrt(Math.pow((xC - xD), 2) - Math.pow((rFive / 2), 2)));
        xB = xA + (rFive / 2);
        xE = xA - (rFive / 2);
        float[] floats = new float[]{xA, yA,  xD, yD,xB, yB, xE, yE, xC, yC};
        return floats;
    }
}
