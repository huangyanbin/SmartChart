package com.daivd.chart.provider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Interpolator;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.data.LevelLine;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.mark.MarkView;
import com.daivd.chart.core.BaseChartView;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.matrix.MatrixHelper;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public abstract class BaseProvider implements IProvider {

    protected ChartData chartData;
    float progress = 1;
     PointF pointF;
     MarkView markView;
    private boolean isOpenMark;
    protected LineStyle crossStyle = new LineStyle();
    private boolean isOpenCross;
    protected LevelLine levelLine;


    @Override
    public void drawProvider(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint) {
        if(markView != null){
            markView.init(canvas,rect);
        }
        canvas.save();
        canvas.clipRect(rect);
        drawProvider(canvas,helper.getZoomProviderRect(rect),rect,paint);
        canvas.restore();

    }

    protected   abstract void drawProvider(Canvas canvas, Rect zoomRect,Rect rect, Paint paint);


    @Override
    public boolean calculation(ChartData chartData) {
        this.chartData = chartData;
        ScaleData scaleData = new ScaleData();
        this.chartData.setScaleData(scaleData);
        List<ColumnData> columnDatas  =  chartData.getColumnDataList();
        if(columnDatas == null || columnDatas.size() == 0){
            return  false;
        }
        int columnSize = columnDatas.size();
        for(int i = 0 ; i <columnSize; i++){
            ColumnData columnData = columnDatas.get(i);
            if(!columnData.isDraw()){
                continue;
            }
            List<Double> datas = columnData.getChartYDataList();
            if(datas == null || datas.size() == 0){
                throw new ChartException("请设置Column数据");
            }
            scaleData.rowSize = datas.size();
            if(datas.size() != scaleData.rowSize){
                throw new ChartException("Column rows数据数量不一致");
            }
            double[] scale = getColumnScale(datas);
            scale = setMaxMinValue(scale[0],scale[1]);
            if(columnData.getDirection() == AxisDirection.LEFT){
                if(!scaleData.isLeftHasValue){
                    scaleData.maxLeftValue = scale[0];
                    scaleData.minLeftValue = scale[1];
                    scaleData.isLeftHasValue = true;
                }else{
                    scaleData.maxLeftValue = Math.max( scaleData.maxLeftValue,scale[0]);
                    scaleData.minLeftValue =  Math.min( scaleData.minLeftValue,scale[1]);
                }

            }else{
                if(!scaleData.isRightHasValue){
                    scaleData.maxRightValue = scale[0];
                    scaleData.minRightValue= scale[1];
                    scaleData.isRightHasValue = true;
                }else{
                    scaleData.maxRightValue = Math.max(scaleData.maxRightValue,scale[0]);
                    scaleData.minRightValue =  Math.min(scaleData.minRightValue,scale[1]);
                }
            }
        }
        if(chartData.getScaleData().rowSize == 0){
            return false;
        }
        return true;



    }

    private double[] getColumnScale(List<Double> values) {
        double maxValue = 0;
        double minValue =0;
        int size = values.size();
        for(int j= 0;j < size;j++) {
            double d = values.get(j) ;
            if(j == 0){
                maxValue = d;
                minValue = d;
            }
            if (maxValue < d){
                maxValue = d;
            }else if(minValue >d){
                minValue = d;
            }
        }
        return new double[] {maxValue,minValue};
    }

    /**
     * 绘制水平线
     */
    protected void drawLevelLine(Canvas canvas, Rect rect,float centerY,Paint paint){

        levelLine.getLineStyle().fillPaint(paint);
        canvas.drawLine(rect.left, centerY, rect.right, centerY, paint);
        levelLine.getTextStyle().fillPaint(paint);
        float textHeight = paint.measureText("1",0,1);
        float startX;
        float startY = centerY-textHeight+levelLine.getLineStyle().getWidth();
        String levelLineValue = String.valueOf(levelLine.getValue());
        if(levelLine.getTextDirection() == LevelLine.left){
            startX = rect.left;
        }else {
            startX = rect.right - textHeight*levelLineValue.length();
        }
        canvas.drawText(levelLineValue,startX,startY,paint);
    }

    @Override
    public void clickPoint(PointF point) {
        pointF = point;
    }

    @Override
    public void startAnim(final BaseChartView chartView,int duration,Interpolator interpolator) {
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


    public void setLevelLine(LevelLine levelLine) {
        this.levelLine = levelLine;
    }

    public boolean isOpenCross() {
        return isOpenCross;
    }

    public void setOpenCross(boolean openCross) {
        isOpenCross = openCross;
    }
    public LineStyle getCrossStyle() {
        return crossStyle;
    }
}
