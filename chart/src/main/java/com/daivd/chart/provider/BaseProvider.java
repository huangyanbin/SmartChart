package com.daivd.chart.provider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Interpolator;

import com.daivd.chart.core.BaseChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.mark.MarkView;
import com.daivd.chart.matrix.MatrixHelper;

/**
 * Created by huang on 2017/9/26.
 */

public abstract class BaseProvider<C extends ColumnData> implements IProvider<C> {

    private float progress = 1;
    protected  PointF pointF;
    protected  MarkView markView;
    private boolean isOpenMark;
    private boolean isShowText =true;
    private int pointTextHeight;
    protected ChartData<C> chartData;
    private Rect providerRect;
    private FontStyle textStyle = new FontStyle();
    protected OnClickColumnListener<C> onClickColumnListener;

    @Override
    public boolean calculation(ChartData<C> chartData) {
        this.chartData = chartData;
        return calculationChild(chartData);
    }

    public abstract boolean calculationChild(ChartData<C> chartData);

    @Override
    public void drawProvider(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint) {
        providerRect = rect;
        if(markView != null){
            markView.init(canvas,rect);
        }
        canvas.save();
        matrixRect(canvas,rect);
        Rect zoomRect = helper.getZoomProviderRect(rect);
        drawProvider(canvas,zoomRect,rect,paint);
        canvas.restore();
        drawPeripheral(canvas,zoomRect,rect,paint);

    }

    /**
     * 外围绘制
     */
    protected void drawPeripheral(Canvas canvas, Rect zoomRect,Rect rect, Paint paint){

    }

    protected void matrixRect(Canvas canvas, Rect rect){
        canvas.clipRect(rect);
    }

    protected   abstract void drawProvider(Canvas canvas, Rect zoomRect,Rect rect, Paint paint);


    @Override
    public void clickPoint(PointF point) {
        pointF = point;
    }

    @Override
    public void startAnim(final BaseChart chartView, int duration, Interpolator interpolator) {
        ValueAnimator animator =  ValueAnimator.ofFloat(0,1);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                chartView.invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                progress = 1;
                chartView.invalidate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progress = 1;
                chartView.invalidate();
            }
        });
        animator.start();
    }

    public void drawPointText(float x,float y,Canvas canvas,Paint paint,double value){
       if(isShowText) {
           int oldColor = paint.getColor();
           textStyle.fillPaint(paint);
           paint.setColor(oldColor);
           String val = String.valueOf(value);
           if(pointTextHeight == 0){
               pointTextHeight = (int) paint.measureText(val,0,1);
           }
           canvas.drawText(val, x-pointTextHeight*val.length()/2, y-pointTextHeight, paint);
       }
    }

    public boolean isOpenMark() {
        return isOpenMark;
    }

    public void setOpenMark(boolean openMark) {
        isOpenMark = openMark;
    }

    public  abstract double[] setMaxMinValue(double maxMinValue, double minValue);

    @Override
    public void setMarkView(MarkView markView) {
        this.markView = markView;
    }


    protected float getAnimValue(float value){

        return value*progress;
    }

    public Rect getProviderRect() {
        return providerRect;
    }


    public boolean isShowText() {
        return isShowText;
    }

    public void setShowText(boolean showText) {
        isShowText = showText;
    }

    public void setOnClickColumnListener(OnClickColumnListener<C> onClickColumnListener) {
        this.onClickColumnListener = onClickColumnListener;
    }


}
