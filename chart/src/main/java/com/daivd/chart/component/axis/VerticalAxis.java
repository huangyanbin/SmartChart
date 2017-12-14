package com.daivd.chart.component.axis;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.ScaleSetData;
import com.daivd.chart.exception.ChartException;

import java.util.List;

/**
 * 图表竖轴
 * @author huangyanbin
 */

public class VerticalAxis extends BaseAxis<Double> {

    /**
     * 图表刻度设置数据
     * <p>用于保存用户设置最大，最小等数据</p>
     */
    private ScaleSetData scaleSetData = new ScaleSetData();

    /**
     * 横轴构造方法
     * <p>可以设置横轴方向 左右方位</p>
     * @param direction 方位
     */
    public VerticalAxis(int direction) {
        this.direction = direction;
    }

    /**
     * 小数点格式化
     */
    private java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

    /**
     * 计算刻度大小
     * <p>通过计算刻度的宽高得到轴的大小，然后保存到scaleData对象中，以便后面的计算</p>
     * @param chartData 图表数据
     * @param rect 图表原始范围
     * @param paint 画笔
     */
    @Override
    public void computeScale(ChartData<? extends BarData> chartData, Rect rect, Paint paint) {
        if(isDisplay()) {
            ScaleData scaleData = chartData.getScaleData();
            scaleData.resetScale(scaleSetData, direction);
            scaleStyle.fillPaint(paint);
            int length = Math.max(formatVerticalAxisData(scaleData.getMaxScaleValue(direction)).length(),
                    formatVerticalAxisData(scaleData.getMinScaleValue(direction)).length());
            int textHeight = (int) (paint.measureText("1", 0, 1) * length);
            int dis = (int) (textHeight + scaleStyle.getPadding() + axisStyle.getWidth());
            if (direction == AxisDirection.LEFT) {
                scaleData.scaleRect.left = dis;
            } else {
                scaleData.scaleRect.right = dis;
            }
        }
    }
    /**
     * 绘制刻度
     * <p>通过zoomRect计算出每个刻度的宽度，迭代绘制刻度</p>
     * @param canvas 画布
     * @param zoomRect 缩放之后范围
     * @param rect  原始范围
     * @param paint  画笔
     * @param chartData 图表数据
     */
    @Override
    protected void drawScale(Canvas canvas, Rect zoomRect, Rect clipRect, Paint paint,  ChartData<? extends BarData> chartData) {
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
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(content, startX, startY, paint);
    }

    /**
     * 绘制网格
     */
    private void drawGrid(Canvas canvas, float startY, Rect rect, Rect scaleRect, Paint paint) {
        if (gridStyle != null && isDrawGrid) {
            gridStyle.fillPaint(paint);
            Path path = new Path();
            path.moveTo(rect.left + scaleRect.left, startY);
            path.lineTo(rect.right - scaleRect.right, startY);
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 计算出裁切轴之后的范围
     * @param rect 原始范围
     * @param scaleRect 缩放范围
     * @return 上下左右的大小
     */
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
    /**
     * 设置轴方位
     * <p>竖轴只能设置左右方位</p>
     * @param axisDirection 轴方位
     *
     */
    @Override
    public void setAxisDirection(int axisDirection) {
        if (axisDirection == AxisDirection.LEFT || axisDirection == AxisDirection.RIGHT) {
            this.direction = axisDirection;
        } else throw new ChartException("Can only set LEFT, RIGHT direction");
    }

    /**
     * 格式化竖轴数据
     * @param value 数值
     * @return 格式化竖轴之后数据
     */
    private String formatVerticalAxisData(double value) {
        if(getFormat() != null){
            return getFormat().format(value);
        }
        return df.format(value);
    }
    /**
     * 设置刻度是否从0开始
     */
    public void setStartZero(boolean isStartZero){
        this.scaleSetData.setStartZoom(isStartZero);
    }

    /**
     * 设置刻度最大值
     * @param maxValue 最大值
     */
    public void setMaxValue(double maxValue) {
        this.scaleSetData.setMaxValue(maxValue);
    }
    /**
     * 设置刻度最小值
     * @param minValue 最大值
     */
    public void setMinValue(double minValue) {
        this.scaleSetData.setMinValue(minValue);
    }

}
