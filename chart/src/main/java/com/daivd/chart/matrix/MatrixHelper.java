package com.daivd.chart.matrix;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.daivd.chart.core.Observable;

import java.util.List;

/**
 * Created by huang on 2017/9/29.
 * 图表放大缩小协助类
 */

public class MatrixHelper extends Observable<ChartGestureObserver> implements ScaleGestureDetector.OnScaleGestureListener {

    private static final int MAX_ZOOM = 5;
    public static final int MIN_ZOOM = 1;
    private float zoom = MIN_ZOOM; //缩放比例  不得小于1
    private int translateX; //以左上角为准，X轴位移的距离
    private int translateY;//以左上角为准，y轴位移的距离
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private boolean isCanZoom;
    private boolean isScale; //是否正在缩放


    public MatrixHelper(Context context){
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context,new OnChartGestureListener());
    }

    /**
     * 处理手势
     */
    public boolean HandlerGestureDetector(MotionEvent event){
        if(isCanZoom) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void notifyObservers(List<ChartGestureObserver> observers) {
        for(ChartGestureObserver observer: observers){
            observer.onViewChanged(zoom,translateX,translateY);
        }
    }

    private float tempScale = MIN_ZOOM; //缩放比例  不得小于1

    class OnChartGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            translateX +=   distanceX;
            translateY += distanceY;
            notifyObservers(observables);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }



        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(isCanZoom){
                if(isScale){ //缩小
                    zoom = zoom / 1.5f;
                    if(zoom <1){
                        zoom = MIN_ZOOM;
                        isScale = false;
                    }
                }else{ //放大
                    zoom = zoom*1.5f;
                    if(zoom > MAX_ZOOM){
                        zoom = MAX_ZOOM;
                        isScale = true;
                    }
                }
                notifyObservers(observables);
            }

            return true;
        }
        //单击
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for(ChartGestureObserver observer: observables){
                observer.onClick(e.getX(),e.getY());
            }
            return true;
        }
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        tempScale = this.zoom;
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float scale = detector.getScaleFactor();
        this.zoom = (float) (tempScale * Math.pow(scale,3));
        notifyObservers(observables);
        if(this.zoom > MAX_ZOOM){
            this.zoom = MAX_ZOOM;
            return true;
        }else if(this.zoom <1){
            this.zoom = 1;
            return true;
        }
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    /**
     * 获取图片内容的缩放大小
     * @param providerRect 内容的大小
     * @return 缩放后内容的大小
     */
    public Rect getZoomProviderRect(Rect providerRect){
        Rect scaleRect = new Rect();
        int oldw = providerRect.width();
        int oldh = providerRect.height();
        int newWidth = (int) (oldw * zoom);
        int newHeight = (int)(oldh * zoom);
        int maxTranslateX = Math.abs(newWidth -oldw)/2;
        int maxTranslateY =  Math.abs(newHeight -oldh)/2;
        if(Math.abs(translateX) > maxTranslateX){
            translateX = translateX > 0 ?maxTranslateX :-maxTranslateX;
        }
        if(Math.abs(translateY) > maxTranslateY){
            translateY = translateY > 0 ? maxTranslateY :-maxTranslateY;
        }
        int offsetX = (newWidth - oldw)/2;
        int offsetY = (newHeight -oldh)/2;
        scaleRect.left = providerRect.left - offsetX -translateX;
        scaleRect.right = providerRect.right +offsetX - translateX;
        scaleRect.top = providerRect.top - offsetY - translateY;
        scaleRect.bottom = providerRect.bottom +offsetY -translateY;
        return scaleRect;
    }

    public boolean isCanZoom() {
        return isCanZoom;
    }

    public void setCanZoom(boolean canZoom) {
        isCanZoom = canZoom;
    }

    public float getZoom() {
        return zoom;
    }
}
