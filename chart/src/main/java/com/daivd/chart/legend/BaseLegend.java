package com.daivd.chart.legend;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.listener.OnClickLegendListener;

import java.util.List;

/**
 * Created by huang on 2017/9/29.
 */

public class BaseLegend<C extends ColumnData> implements ILegend<C> {

    private static final float DEFAULT_PERCENT = 0.2f;
    private FontStyle fontStyle;
    private PointStyle legendStyle ;
    private float percent = DEFAULT_PERCENT;
    private int legendDirection = BOTTOM;
    private ChartData<C> chartData;
    private int padding = 10;
    private PointF pointF;
    private boolean isSelectColumn = true;
    private OnClickLegendListener<C> onClickLegendListener;

    public BaseLegend(){
        legendStyle = new PointStyle();
        legendStyle.setShape(PointStyle.SQUARE);
        legendStyle.setWidth(25);
        fontStyle = new FontStyle();
    }
    @Override
    public void computeLegend(ChartData chartData, Rect rect, Paint paint) {
        this.chartData = chartData;
        Rect legendRect = chartData.getScaleData().legendRect;
        switch (legendDirection){
            case TOP:
                legendRect.top = (int) ((rect.bottom - rect.top)*percent);
                break;
            case LEFT:
                legendRect.left = (int) ((rect.right - rect.left)*percent);
                break;
            case RIGHT:
                legendRect.right = (int) ((rect.right - rect.left)*percent);
                break;
            case BOTTOM:
                legendRect.bottom = (int) ((rect.bottom - rect.top)*percent);
                break;
        }
    }

    @Override
    public void drawLegend(Canvas canvas, Rect rect, Paint paint) {

        int startX = 0,startY = 0;
        int textHeight = (int)paint.measureText("1",0,1);
        int offsetY = Math.max(textHeight,(int) legendStyle.getWidth());
        switch (legendDirection) {
            case TOP:
                startY = (int) (rect.top + rect.height() * percent/2);
                startX = rect.left+(rect.right - rect.left)/2;
                break;
            case LEFT:
                startX = (int) (rect.left +  rect.width()* percent/2);
                startY =rect.top + (rect.bottom -rect.top)/2;
                break;
            case RIGHT:
                startX = (int) (rect.right - rect.width()* percent/2);
                startY =rect.top + (rect.bottom -rect.top)/2;
                break;
            case BOTTOM:
                startY = (int) (rect.bottom - rect.height()* percent/2);
                startX =  rect.left+(rect.right - rect.left)/2;
                break;
        }
        List<C> columnDataList = chartData.getColumnDataList();
        startY+=offsetY;
        int tempStartX =startX;
        for(int i = 0;i <columnDataList.size();i++){
            ColumnData columnData = columnDataList.get(i);
            String name =  columnData.getName();
            tempStartX+= legendStyle.getWidth()+padding;
            tempStartX+= textHeight*name.length()*2+padding;
        }
        startX  = startX-(tempStartX - startX)/2;
        for(int i = 0;i <columnDataList.size();i++){
            C columnData = columnDataList.get(i);
            String name =  columnData.getName();
            float right =startX + legendStyle.getWidth()+padding*2+textHeight*name.length()*3;
            if(pointF != null && isClickRect(startX,right,startY-offsetY, startY+ offsetY*2)){
                if(isSelectColumn) {
                    columnData.setDraw(!columnData.isDraw());
                }
                pointF = null;
                if(onClickLegendListener != null){
                    onClickLegendListener.onClickLegend(columnData,this);
                }
            }
            paint.setColor(columnData.getColor());
            drawPoint(canvas,columnData.isDraw(),startX,(int) (startY-legendStyle.getWidth()/2),paint);
            startX+= legendStyle.getWidth()+padding;
            drawText(canvas,startX,startY,name,paint);
            startX+= textHeight*name.length()*3+padding;

        }
    }


    private  boolean isClickRect(float left, float right, float top, float bottom){
        if(pointF != null) {
            return  pointF.x >= left && pointF.x <= right && pointF.y >= top && pointF.y <= bottom;
        }
        return false;
    }


    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, int startX, int startY, String  content, Paint paint) {
        fontStyle.fillPaint(paint);
        canvas.drawText(content, startX, startY, paint);
    }

    protected void drawPoint(Canvas canvas,boolean isDraw,int x, int y, Paint paint){
        if(isDraw){
            int oldColor = paint.getColor();
            legendStyle.fillPaint(paint);
            paint.setColor(oldColor);
        }else{
            legendStyle.fillPaint(paint);
        }
        float w = legendStyle.getWidth();
        if(legendStyle.getShape() == PointStyle.CIRCLE) {
            canvas.drawCircle(x, y, w/2, paint);
        }else if(legendStyle.getShape() == PointStyle.SQUARE){
            canvas.drawRect(x-w/2,y-w/2,x+w/2,y+w/2,paint);
        }else if(legendStyle.getShape() == PointStyle.RECT){
            canvas.drawRect(x-w/2,y-w/3,x+w/2,y+w/3,paint);
        }

    }



    @Override
    public void setLegendPercent(float percent) {

        this.percent = percent;
        if(this.percent >0.5){
            this.percent = 0.5f;
        }
    }

    @Override
    public FontStyle getTextStyle() {
        return fontStyle;
    }

    public PointStyle getLegendStyle() {
        return legendStyle;
    }

    @Override
    public void setLegendDirection(int legendDirection) {
        this.legendDirection = legendDirection;
    }

    @Override
    public void onClickLegend(PointF pointF) {
        this.pointF = pointF;
    }



    @Override
    public void setOnClickLegendListener(OnClickLegendListener<C> onClickLegendListener) {
       this.onClickLegendListener = onClickLegendListener;
    }


    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }


    public void setSelectColumn(boolean selectColumn) {
        isSelectColumn = selectColumn;
    }
}
