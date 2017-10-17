package com.daivd.chart.axis;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.Gravity;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.exception.ChartException;

import java.util.List;

/**
 * Created by huangYanBin on 2017/9/26.
 * 横轴
 */

public class HorizontalAxis extends BaseAxis<String> {

    public HorizontalAxis() {
        direction = AxisDirection.BOTTOM;
    }
    private int rotateAngle= 0;
    private boolean isRotateAngle;
    private int textWidth;
    private int textHeight;
    @Override
    public void computeScale(ChartData<LineData> chartData, Rect rect, Paint paint) {
        ScaleData scaleData = chartData.getScaleData();
        scaleStyle.fillPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);

           int maxLength = 0;
           String maxLengthXData = null;
           for (String xData : chartData.getCharXDataList()) {
               String formatData = formatData(xData);
               if (maxLength < formatData.length()) {
                   maxLengthXData = formatData;
               }
           }
          textWidth = (int) paint.measureText(maxLengthXData);
        //计算旋转的高宽
        if(isRotateAngle) {
            int tempHeight = (int) (textWidth *Math.sin(rotateAngle*Math.PI/180)
                    + textHeight*Math.cos(rotateAngle*Math.PI/180));
            int tempWidth = (int) (textWidth *Math.cos(rotateAngle*Math.PI/180)
                    +textHeight*Math.sin(rotateAngle*Math.PI/180));
            textHeight = tempHeight;
            textWidth = tempWidth;
       }
        int dis = (int) (textHeight+scaleStyle.getPadding()*2  + lineStyle.getWidth());
        if (direction == AxisDirection.BOTTOM) {
            scaleData.scaleRect.bottom = dis;
        } else {
            scaleData.scaleRect.top = dis;
        }
    }

    protected void drawScale(Canvas canvas, Rect zoomRect, Rect rect, Paint paint,  ChartData<LineData> chartData) {

        ScaleData scaleData = chartData.getScaleData();
        List<String> groupDataList = chartData.getCharXDataList();
        int rowSize = scaleData.rowSize;
        int groupSize = groupDataList.size();
        if (groupSize != rowSize) {
            throw new ChartException("横竖轴数据不一致");
        }
        float startY;
        if (direction == AxisDirection.BOTTOM) {
            startY = zoomRect.bottom -textHeight- scaleStyle.getPadding();
        } else {
            startY = zoomRect.top + scaleData.scaleRect.top - scaleStyle.getPadding();
        }
        int left = zoomRect.left ;
        int width = zoomRect.right - left;
        int perWidth = width / (isLine? groupSize -1 : groupSize);
        int filterMultiple = textWidth / perWidth <1 ? 1 : textWidth / perWidth;
        for (int i = 0; i < groupSize; i++) {
            String content = groupDataList.get(i);
            int startX = getGravityStartX(left, i, perWidth);
            if (rect.contains(startX+1,rect.centerY())) {
                if( i % filterMultiple == 0) {
                    drawText(canvas, content,startX- textWidth / 2, startY, paint);
                    drawGrid(canvas, startX, rect, scaleData.scaleRect, paint);
                }
            }
        }
    }

    private int getGravityStartX(int left, int position, int perWidth) {
        int startX = left + position * perWidth;
        if (gravity == Gravity.CENTER) {
            startX += perWidth / 2;
        } else if (gravity == Gravity.RIGHT) {
            startX += perWidth;
        }
        return startX;
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, String contentStr,int startX,float startY, Paint paint) {
        String content = formatData(contentStr);
        scaleStyle.fillPaint(paint);
        if(isRotateAngle){
            canvas.save();
           // canvas.drawCircle(startX,startY,10,paint);
            canvas.rotate(rotateAngle,startX,startY);
            canvas.drawText(content, startX , startY, paint);
            canvas.restore();
        }else {
            //canvas.drawCircle(startX,startY,10,paint);
            canvas.drawText(content, startX, startY+textHeight, paint);
        }
    }

    private String formatData(String data){
        return  getFormat()!= null ? getFormat().format(data) :data;
    }

    /**
     * 绘制网格
     */
    public void drawGrid(Canvas canvas, float startX, Rect rect, Rect scaleRect, Paint paint) {
        if (gridStyle != null && isDrawGrid) {
           // if(rect.contains(startX,rect.centerY())) {
                gridStyle.fillPaint(paint);
                Path path = new Path();
                path.moveTo(startX, rect.top + scaleRect.top);
                path.lineTo(startX, rect.bottom - scaleRect.bottom);
                canvas.drawPath(path, paint);
            //}
        }
    }

    @Override
    protected void drawAxis(Canvas canvas, Rect rect, Paint paint,  ChartData<LineData> chartData) {
        Rect scaleRect = chartData.getScaleData().scaleRect;
        lineStyle.fillPaint(paint);
        paint.setStyle(Paint.Style.STROKE);
        int[] r = calculation(rect, scaleRect);
        canvas.drawLine(r[0], r[1], r[2], r[3], paint);

    }


    private int[] calculation(Rect rect, Rect scaleRect) {
        int startX = rect.left + scaleRect.left;
        int endX = rect.right - scaleRect.right;
        int startY, endY;
        if (direction == AxisDirection.BOTTOM) {
            startY = rect.bottom - scaleRect.bottom;
        } else {
            startY = rect.top + scaleRect.top;
        }
        endY = startY;
        return new int[]{startX, startY, endX, endY};
    }


    @Override
    public void setAxisDirection(AxisDirection axisDirection) {
        if (axisDirection == AxisDirection.BOTTOM || axisDirection == AxisDirection.TOP) {
            this.direction = axisDirection;
        } else throw new ChartException("只能设置BOTTOM,TOP方向");
    }



    public void setRotateAngle(int rotateAngle) {
        isRotateAngle = true;
        this.rotateAngle = rotateAngle;
    }
}
