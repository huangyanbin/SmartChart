package com.daivd.chart.provider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Interpolator;

import com.daivd.chart.core.base.BaseChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.IMark;
import com.daivd.chart.matrix.MatrixHelper;
import com.daivd.chart.provider.component.text.IText;

/**
 * Created by huang on 2017/9/26.
 */

public abstract class BaseProvider<C extends ColumnData> implements IProvider<C> {

    private float progress = 1;
    protected  PointF pointF;
    protected IMark<C> markView;
    private boolean isOpenMark;
    private boolean isShowText =true;
    protected ChartData<C> chartData;
    private Rect providerRect;
    private IText text;
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
        matrixRectStart(canvas,rect);
        Rect zoomRect = helper.getZoomProviderRect(rect);
        drawProvider(canvas,zoomRect,rect,paint);
        matrixRectEnd(canvas,rect);
    }


    protected void matrixRectStart(Canvas canvas, Rect rect){
        canvas.save();
        canvas.clipRect(rect);
    }

    protected void matrixRectEnd(Canvas canvas, Rect rect){
        canvas.restore();
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

    public void drawPointText(Canvas canvas,double value,float x,float y,int position,int line,Paint paint){
        if(text != null) {
            String val = String.valueOf(value);
            if (containsRect(x,y)) {
                text.drawText(canvas,val,x,y,position,line,paint);
            }
        }
    }

    public boolean containsRect( float x,float y){
        Rect rect = providerRect;
        return y>= rect.top -1 && y<= rect.bottom+1
                && x>= rect.left-1 && x<= rect.right+1;
    }

    public boolean isOpenMark() {
        return isOpenMark;
    }

    public void setOpenMark(boolean openMark) {
        isOpenMark = openMark;
    }

    public  abstract double[] setMaxMinValue(double maxMinValue, double minValue);

    @Override
    public void setMarkView(IMark markView) {
        this.markView = markView;
    }


    protected float getAnimValue(float value){

        return value*progress;
    }

    public Rect getProviderRect() {
        return providerRect;
    }



    public float getProgress() {
        return progress;
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
    public IText getText() {
        return text;
    }

    public void setText(IText text) {
        this.text = text;
    }


}
