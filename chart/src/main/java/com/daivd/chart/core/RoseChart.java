package com.daivd.chart.core;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daivd.chart.core.BaseChart;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.RoseData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.pie.PieProvider;
import com.daivd.chart.provider.rose.RoseProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public class RoseChart extends BaseChart<RoseProvider,RoseData> implements RotateHelper.OnRotateListener{
    private RotateHelper rotateHelper;
    private boolean isOnRotate;

    public RoseChart(Context context) {
        super(context);
    }

    public RoseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoseChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void drawContent(Canvas canvas) {
        provider.drawProvider(canvas, chartRect, matrixHelper, paint);
    }

    @Override
    protected RoseProvider initProvider() {
        rotateHelper = new RotateHelper(this);
        RoseProvider provider =  new RoseProvider();
        provider.setRotateHelper(rotateHelper);
        return provider;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            isOnRotate = provider.getProviderRect().contains((int)event.getX(),(int)event.getY());
        }
        if(isOnRotate){
            super.onTouchEvent(event);
            return  rotateHelper.dispatchTouchEvent(event);
        }
         return super.onTouchEvent(event);
    }

    public void setRotate(boolean rotate) {
        if(rotate){
            setZoom(false);
        }
        rotateHelper.setRotate(rotate);
    }

    @Override
    public void onRotate(double angle) {
        invalidate();
    }
}
