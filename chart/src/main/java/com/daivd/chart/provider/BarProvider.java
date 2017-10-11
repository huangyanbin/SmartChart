package com.daivd.chart.provider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.utils.ColorUtils;

import java.util.List;


/**
 * Created by huang on 2017/9/26.
 */

public class BarProvider extends BarLineProvider {

    private int groupPadding =20;



    @Override
    public void drawProvider(Canvas canvas, Rect zoomRect,Rect rect, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        ScaleData scaleData= chartData.getScaleData();
        List<LineData> columnDatas = chartData.getColumnDataList();
        int columnSize = columnDatas.size();
        int rowSize = chartData.getCharXDataList().size();
        double width = (zoomRect.right - zoomRect.left)/(columnSize*rowSize)-groupPadding/2;
        float height =(zoomRect.bottom - zoomRect.top);
        PointF clickPoint = null;
        int clickPosition = 0;
        int clickColumnPosition = 0;
        boolean isClickRect1 = (pointF != null && rect.contains((int)pointF.x,(int)pointF.y));
        boolean isClickRect = false;

            for(int j = 0;j <rowSize;j++) {
                for(int i = 0;i <columnSize;i++) {
                    ColumnData columnData = columnDatas.get(i);
                    if (!columnData.isDraw()) {
                        continue;
                    }
                    double d = ((List<Double>) columnData.getChartYDataList()).get(j);
                    float left = (float) ((j * columnSize + i) * width) + j * groupPadding + zoomRect.left + 0.5f;
                    float right = (float) (left + width);
                    float top = getStartY(zoomRect, scaleData, height, d, columnData.getDirection());
                    float bottom = zoomRect.bottom + 0.5f;
                    paint.setColor(columnData.getColor());
                    if (isClickRect1 && isClickRect(left, right, top, bottom)) {
                        paint.setColor(ColorUtils.getDarkerColor(columnData.getColor()));
                        isClickRect = true;
                        clickPoint = new PointF((left + right) / 2, top);
                        clickPosition = j;
                        clickColumnPosition = i;
                    }
                    drawBar(canvas, paint, left, right, top + (bottom - top) - getAnimValue(bottom - top), bottom,d);
                }
            }

        if(levelLine != null && levelLine.isDraw()) {
            float startY = getStartY(zoomRect,scaleData,height,levelLine.getValue(),levelLine.getDirection());
            drawLevelLine(canvas, zoomRect,startY,paint);
        }
        if(isClickRect) {
            drawCross(canvas,clickPoint.x,clickPoint.y,zoomRect,paint);
            drawMark(clickPoint.x,clickPoint.y,clickPosition,clickColumnPosition,chartData);
        }
    }

    protected void drawBar(Canvas canvas, Paint paint, float left, float right, float top, float bottom,double value) {
        canvas.drawRect(left, top, right, bottom, paint);
        drawPointText((right + left) / 2, top, canvas, paint, value);
    }

    protected float getStartY(Rect rect, ScaleData scaleData, float height, double d, AxisDirection direction) {
        return (float) (rect.bottom -(d - scaleData.getMinScaleValue(direction)) * height / scaleData.getTotalScaleLength(direction));
    }


    private void drawCross(Canvas canvas,float x,float y, Rect rect,Paint paint){

        if(isOpenCross()) {
            crossStyle.fillPaint(paint);
            canvas.drawLine(x, rect.top, x, rect.bottom, paint);
            canvas.drawLine(rect.left, y, rect.right, y, paint);
        }
    }
    private void drawMark(float x, float y, int position,int columnPosition,  ChartData<LineData> chartData){

        if(markView != null && isOpenMark()){
            markView.drawMark(x,y,chartData.getCharXDataList().get(position),
                    chartData.getColumnDataList().get(columnPosition),position);
        }
    }

    private  boolean isClickRect(float left, float right, float top, float bottom){
        if(pointF != null) {
            return  pointF.x >= left && pointF.x <= right && pointF.y >= top && pointF.y <= bottom;
        }
        return false;
    }



    @Override
    public double[] setMaxMinValue(double maxValue, double minValue) {
        double dis = Math.abs(maxValue -minValue);
        maxValue = maxValue + dis*0.2;
        if(minValue >0){
            minValue = 0;
        }else{
            minValue = minValue - dis*0.2;
        }
        return new double[]{maxValue,minValue};
    }

    public int getGroupPadding() {
        return groupPadding;
    }

    public void setGroupPadding(int groupPadding) {
        this.groupPadding = groupPadding;
    }
}
