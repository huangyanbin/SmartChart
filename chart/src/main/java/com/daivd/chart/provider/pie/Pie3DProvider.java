package com.daivd.chart.provider.pie;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.BaseProvider;
import com.daivd.chart.utils.ColorUtils;

import java.util.List;

/**
 * Created by huang on 2017/10/9.
 */

public class Pie3DProvider extends PieProvider {

    //提供摄像头
    Camera camera = new Camera();

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
