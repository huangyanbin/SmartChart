package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;



/**柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public class Bar3DProvider extends BarProvider {

    @Override
    protected void drawBar(Canvas canvas, Paint paint, float left, float right, float top, float bottom,double value) {
        canvas.drawRect(left, top, right, bottom, paint);

        float w = right - left;
        float  offsetY= w/2;
        float offsetX = w/3f;
        Path path = new Path();
        path.moveTo(left,top);
        path.lineTo(left+offsetX,top-offsetY);
        path.lineTo(right+offsetX,top-offsetY);
        path.lineTo(right,top);
        path.close();
        canvas.drawPath(path,paint);
        path.reset();
        path.moveTo(right,top);
        path.lineTo(right,bottom);
        path.lineTo(right+offsetX,bottom-offsetY);
        path.lineTo(right+offsetX,top-offsetY);
        path.close();
        canvas.drawPath(path,paint);
        drawPointText(canvas,value,(right + left) / 2, top-offsetY, paint );

    }


    @Override
    public double[] setMaxMinValue(double maxValue, double minValue) {
        double dis = Math.abs(maxValue -minValue);
        maxValue = maxValue + dis*0.4;
        if(minValue >0){
            minValue = 0;
        }else{
            minValue = minValue - dis*0.4;
        }
        return new double[]{maxValue,minValue};
    }
}
