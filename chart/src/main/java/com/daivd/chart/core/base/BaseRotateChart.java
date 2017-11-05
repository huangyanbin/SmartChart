package com.daivd.chart.core.base;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daivd.chart.data.ColumnData;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.IProvider;

/**
 * Created by huang on 2017/10/9.
 * 饼图
 */

public abstract  class BaseRotateChart<P extends IProvider<C>,C extends ColumnData>  extends BaseChart<P,C> implements RotateHelper.OnRotateListener{
    private RotateHelper rotateHelper;
    private boolean isOnRotate;

    public BaseRotateChart(Context context) {
        super(context);
    }

    public BaseRotateChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRotateChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void drawContent(Canvas canvas) {
        provider.drawProvider(canvas, chartRect, matrixHelper, paint);
    }

    @Override
    protected P initProvider() {
        rotateHelper = new RotateHelper(this);
        return initProvider(rotateHelper);
    }

    protected abstract P  initProvider(RotateHelper rotateHelper);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN){
            isOnRotate = rotateHelper.containsPoint(event);
        }
        if(isOnRotate){
            super.onTouchEvent(event);
            return  rotateHelper.handlerTouchEvent(event);
        }
         return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(isOnRotate){
            rotateHelper.onDisallowInterceptEvent(this,event);
        }
        return super.dispatchTouchEvent(event);

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
