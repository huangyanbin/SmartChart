package com.daivd.chart.axis;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.Gravity;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.exception.ChartException;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 */

public class HorizontalAxis extends BaseAxis {

    public HorizontalAxis() {
        direction = AxisDirection.BOTTOM;
    }
    private int padding = 10;

    @Override
    public void computeScale(ChartData chartData, Rect rect, Paint paint) {
        ScaleData scaleData = chartData.getScaleData();
        scaleStyle.fillPaint(paint);
        int textHeight = (int) (paint.measureText("1", 0, 1) * 2);
        int dis = (int) (textHeight + scaleStyle.getPadding() * 2 + lineStyle.getWidth());
        if (direction == AxisDirection.BOTTOM) {
            scaleData.scaleRect.bottom = dis;
        } else {
            scaleData.scaleRect.top = dis;
        }
    }

    protected void drawScale(Canvas canvas, Rect zoomRect, Rect clipRect, Paint paint, ChartData chartData) {
        //Log.e("huang","scale zoomRect:"+zoomRect.toString());
        ScaleData scaleData = chartData.getScaleData();
        List<String> groupDataList = chartData.getCharXDataList();
        int rowSize = scaleData.rowSize;
        int groupSize = groupDataList.size();
        if (groupSize != rowSize) {
            throw new ChartException("横竖轴数据不一致");
        }
        float startY;

        if (direction == AxisDirection.BOTTOM) {
            startY = zoomRect.bottom - scaleStyle.getPadding();
        } else {
            startY = zoomRect.top + scaleData.scaleRect.top - scaleStyle.getPadding();
        }
        int left = zoomRect.left ;
        int width = zoomRect.right - left;
        int perWidth = width / groupSize;
        int textHeight = (int) paint.measureText("1", 0, 1) * 2;
        int maxScaleSize = width /(textHeight*2+padding);
        int filterMultiple = groupSize/maxScaleSize <1 ? 1 : groupSize/maxScaleSize;
        for (int i = 0; i < groupSize; i++) {
            String content = groupDataList.get(i);
            int textWidth = textHeight * content.length();
            int startX = getGravityStartY(left, i, perWidth, textWidth);
            if (startX > clipRect.left && startX < clipRect.right) {
                if( i % filterMultiple == 0) {
                    drawText(canvas, content, startY, startX, paint);
                    int startGirdPos = direction == AxisDirection.BOTTOM ? perWidth : 0;
                    drawGrid(canvas, left + i * perWidth+startGirdPos, zoomRect, scaleData.scaleRect, paint);
                }
            }
        }
    }

    private int getGravityStartY(int left, int position, int perWidth, int textWidth) {
        int startX = left + position * perWidth;
        if (gravity == Gravity.CENTER) {
            startX += perWidth / 2;
        } else if (gravity == Gravity.RIGHT) {
            startX += perWidth;
        }
        return startX - textWidth / 2;
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, String contentStr, float startY, int startX, Paint paint) {
        String content = formatHorizontalAxisData(contentStr);
        scaleStyle.fillPaint(paint);
        canvas.drawText(content, startX, startY, paint);
    }

    /**
     * 绘制网格
     */
    public void drawGrid(Canvas canvas, int startX, Rect rect, Rect scaleRect, Paint paint) {
        if (gridStyle != null && isDrawGrid) {
            gridStyle.fillPaint(paint);
            Path path = new Path();
            path.moveTo(startX, rect.top + scaleRect.top);
            path.lineTo(startX, rect.bottom - scaleRect.bottom);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void drawAxis(Canvas canvas, Rect rect, Paint paint, ChartData chartData) {
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


}
