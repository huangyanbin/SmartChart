package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.utils.ColorUtils;

import java.util.List;


/**柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public class BarProvider extends BarLineProvider {

    private int groupPadding =20;



    @Override
    public void drawProvider(Canvas canvas, Rect zoomRect,Rect rect, Paint paint) {
        paint.setStyle(Paint.Style.FILL);

        List<LineData> columnDataList = chartData.getColumnDataList();
        int columnSize = columnDataList.size();
        int rowSize = chartData.getCharXDataList().size();
        double width = zoomRect.width()/(columnSize*rowSize)-groupPadding/2;
        PointF clickPoint = null;
        int clickPosition = 0;
        int clickColumnPosition = 0;
        boolean isClickRect1 = (pointF != null && rect.contains((int)pointF.x,(int)pointF.y));
        boolean isClickRect = false;

            for(int j = 0;j <rowSize;j++) {
                for(int i = 0;i <columnSize;i++) {
                    LineData columnData = columnDataList.get(i);
                    if (!columnData.isDraw()) {
                        continue;
                    }
                    double value = columnData.getChartYDataList().get(j);
                    float left = (float) ((j * columnSize + i) * width)
                            + j * groupPadding + zoomRect.left + 0.5f;
                    float right = (float) (left + width);
                    float top = getStartY(zoomRect, value, columnData.getDirection());
                    float bottom = zoomRect.bottom + 0.5f;
                    paint.setColor(columnData.getColor());
                    if (isClickRect1 && isClickRect(left, right, top, bottom)) {
                        paint.setColor(ColorUtils.getDarkerColor(columnData.getColor()));
                        isClickRect = true;
                        clickPoint = new PointF((left + right) / 2, top);
                        clickPosition = j;
                        clickColumnPosition = i;
                        if(onClickColumnListener != null){
                            onClickColumnListener.onClickColumn(columnData,j);
                        }
                    }
                    drawBar(canvas, paint, left, right, top + (bottom - top) - getAnimValue(bottom - top), bottom,value);
                }
            }

        if(levelLine != null) {
            drawLevelLine(canvas, zoomRect,paint);
        }
        if(isClickRect && containsRect(clickPoint.x,clickPoint.y)) {
            drawCross(canvas,clickPoint.x,clickPoint.y,zoomRect,paint);
            super.matrixRectEnd(canvas, rect);
            drawMark(clickPoint.x,clickPoint.y,clickPosition,clickColumnPosition,chartData);
        }
    }

    @Override
    protected void matrixRectEnd(Canvas canvas, Rect rect) {

    }

    @Override
    protected float getStartY(Rect zoomRect, double value, int direction){
        ScaleData scaleData = chartData.getScaleData();
        double minValue = scaleData.getMinScaleValue(direction);
        double totalScaleLength = scaleData.getTotalScaleLength(direction);
        return (float) (zoomRect.bottom -(value - minValue) * zoomRect.height() / totalScaleLength);
    }

    protected void drawBar(Canvas canvas, Paint paint, float left, float right, float top, float bottom,double value) {
        canvas.drawRect(left, top, right, bottom, paint);
        drawPointText(canvas,value,(right + left) / 2, top,  paint);
    }




    private void drawCross(Canvas canvas,float x,float y, Rect rect,Paint paint){

        if(isOpenCross() && getCross() != null) {
            PointF pointF = new PointF(x,y);
            getCross().drawCross(canvas,pointF,rect,paint);
        }
    }
    private void drawMark(float x, float y, int position,int columnPosition,  ChartData<LineData> chartData){

        if(markView != null && isOpenMark()){
            markView.drawMark(x,y,chartData.getCharXDataList().get(position),
                    chartData.getColumnDataList().get(columnPosition),position);
        }
    }

    private  boolean isClickRect(float left, float right, float top, float bottom) {
        return pointF != null && pointF.x >= left && pointF.x <= right && pointF.y >= top && pointF.y <= bottom;
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
