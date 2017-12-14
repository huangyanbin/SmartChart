package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.data.BarData;


/**柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public class Bar3DProvider extends BarProvider<BarData> {

    /**
     * 绘制柱
     * @param canvas 画布
     * @param rect 范围
     * @param value 数值
     * @param position 位置
     * @param line 线位置
     * @param paint 画笔
     */
    @Override
    protected void drawBar(Canvas canvas,Rect rect,double value,int position,int line,Paint paint){
        canvas.drawRect(rect, paint);
        float w = rect.right - rect.left;
        float  offsetY= w/2;
        float offsetX = w/3f;
        Path path = new Path();
        path.moveTo(rect.left,rect.top);
        path.lineTo(rect.left+offsetX,rect.top-offsetY);
        path.lineTo(rect.right+offsetX,rect.top-offsetY);
        path.lineTo(rect.right,rect.top);
        path.close();
        canvas.drawPath(path,paint);
        path.reset();
        path.moveTo(rect.right,rect.top);
        path.lineTo(rect.right,rect.bottom);
        path.lineTo(rect.right+offsetX,rect.bottom-offsetY);
        path.lineTo(rect.right+offsetX,rect.top-offsetY);
        path.close();
        canvas.drawPath(path,paint);
        drawPointText(canvas,value,(rect.right + rect.left) / 2, rect.top-offsetY,position,line, paint );

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
