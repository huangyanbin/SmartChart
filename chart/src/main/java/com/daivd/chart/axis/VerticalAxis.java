package com.daivd.chart.axis;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.ScaleSetData;
import com.daivd.chart.exception.ChartException;

import java.util.List;

/**
 * Created by huang on 2017/9/26.
 * 竖轴
 */

public class VerticalAxis extends BaseAxis<Double> {

    private ScaleSetData scaleSetData = new ScaleSetData();

    public VerticalAxis(int direction) {
        this.direction = direction;
    }

    private java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

    @Override
    public void computeScale(ChartData<? extends LineData> chartData, Rect rect, Paint paint) {
        ScaleData scaleData = chartData.getScaleData();
        scaleData.resetScale(scaleSetData,direction);
        scaleStyle.fillPaint(paint);
        int length = Math.max(formatVerticalAxisData(scaleData.getMaxScaleValue(direction)).length(),
                formatVerticalAxisData(scaleData.getMinScaleValue(direction)).length());
        int textHeight = (int) (paint.measureText("1", 0, 1) * length);
        int dis = (int) (textHeight + scaleStyle.getPadding()  + axisStyle.getWidth());
        if (direction == AxisDirection.LEFT) {
            scaleData.scaleRect.left = dis;
        } else {
            scaleData.scaleRect.right = dis;
        }
    }

    @Override
    protected void drawScale(Canvas canvas, Rect zoomRect, Rect clipRect, Paint paint,  ChartData<? extends LineData> chartData) {
        ScaleData scaleData = chartData.getScaleData();
        List<Double> scaleList = scaleData.getScaleList(direction);
        float startX;
        if (direction == AxisDirection.LEFT) {
            startX = zoomRect.left + scaleStyle.getPadding();
        } else {
            startX = zoomRect.right - scaleData.scaleRect.right + scaleStyle.getPadding();
        }
        int bottom = zoomRect.bottom;
        int height = zoomRect.height();
        float textHeight = paint.measureText("1", 0, 1);
        int perHeight = height / (scaleList.size()-1);
        for (int i = 0; i < scaleList.size(); i++) {
            double value = scaleList.get(i);
            float startY = bottom - i * perHeight;
            if (clipRect.contains(clipRect.centerX(), (int) startY-1)) {
                drawText(canvas, startX, startY + textHeight / 2, value, paint);
                drawGrid(canvas, startY, zoomRect, scaleData.scaleRect, paint);
            }
        }

    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, float startX, float startY, double value, Paint paint) {
        scaleStyle.fillPaint(paint);
        String content = formatVerticalAxisData(value);
        canvas.drawText(content, startX, startY, paint);
    }

    /**
     * 绘制网格
     */
    public void drawGrid(Canvas canvas, float startY, Rect rect, Rect scaleRect, Paint paint) {
        if (gridStyle != null && isDrawGrid) {
            gridStyle.fillPaint(paint);
            Path path = new Path();
            path.moveTo(rect.left + scaleRect.left, startY);
            path.lineTo(rect.right - scaleRect.right, startY);
            canvas.drawPath(path, paint);
        }
    }


    protected int[] calculation(Rect rect, Rect scaleRect) {

        int startY = rect.top + scaleRect.top;
        int endY = rect.bottom - scaleRect.bottom;
        int startX, endX;
        if (direction == AxisDirection.LEFT) {
            startX = rect.left + scaleRect.left;
        } else {
            startX = rect.right - scaleRect.right;
        }
        endX = startX;
        return new int[]{startX, startY, endX, endY};
    }

    @Override
    public void setAxisDirection(int axisDirection) {
        if (axisDirection == AxisDirection.LEFT || axisDirection == AxisDirection.RIGHT) {
            this.direction = axisDirection;
        } else throw new ChartException("Can only set LEFT, RIGHT direction");
    }

    private String formatVerticalAxisData(double value) {
        if(getFormat() != null){
            return getFormat().format(value);
        }
        return df.format(value);
    }

    public void setStartZero(boolean isStartZero){
        this.scaleSetData.setStartZoom(isStartZero);
    }

    public void setMaxValue(double maxValue) {
        this.scaleSetData.setMaxValue(maxValue);
    }

    public void setMinValue(double minValue) {
        this.scaleSetData.setMinValue(minValue);
    }
}
