package com.daivd.chart.provider.pie;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;

/**
 * Created by huang on 2017/10/9.
 * 3D饼图
 * 试验中，未完成
 */

public class Pie3DProvider extends PieProvider {
    private int borderWidth = 20;

    //提供摄像头
    private Camera camera = new Camera();

    public Pie3DProvider(Context context){
        //拉远摄像头Z轴
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0,0,newZ);
    }
    @Override
    public boolean calculationChild(ChartData<PieData> chartData) {
        return true;
    }

    /**
     * 变形开始
     *
     */
    @Override
    protected void matrixRectStart(Canvas canvas, Rect rect) {
        canvas.save();
        camera.save();
        canvas.translate(rect.centerX(),rect.centerY());
        camera.rotateX(60);
        camera.applyToCanvas(canvas);
        canvas.translate(-rect.centerX(),-rect.centerY());
        canvas.clipRect(rect);
        if(rotateHelper != null && rotateHelper.isRotate()){
            canvas.rotate((float) rotateHelper.getStartAngle(),rect.centerX(),rect.centerY());
        }

    }

    @Override
    protected void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {

        super.drawProvider(canvas, zoomRect, rect, paint);
        PointF centerPoint = getCenterPoint();
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#80FFFFFF"));
        int radius = getCenterRadius();
        canvas.drawCircle(centerPoint.x,centerPoint.y,radius*getCenterCirclePercent()+borderWidth/2,paint);
        canvas.drawCircle(centerPoint.x,centerPoint.y,radius-borderWidth/2,paint);
    }

    /**
     * 变形结束
     *
     */
    @Override
    protected void matrixRectEnd(Canvas canvas, Rect rect) {
        camera.restore();
        super.matrixRectEnd(canvas, rect);

    }


}
