package com.daivd.chart.component.axis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.Gravity;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.matrix.MatrixHelper;


/**
 * 基本轴 负责轴的计算方位和绘制
 * @author huangyanbin
 */

public abstract class BaseAxis<V> implements IAxis<V> {

    /**
     * 轴线样式
     */
    protected LineStyle axisStyle = new LineStyle();
    /**
     * 刻度样式
     */
    protected FontStyle scaleStyle = new FontStyle();
    /**
     * 网格样式
     */
    protected LineStyle gridStyle = new LineStyle(); //网格样式
    /**
     * 是否绘制网格
     */
    protected boolean isDrawGrid; //是否绘制网格
    /**
     * 是否显示轴线
     */
    protected boolean isShowAxisLine = true;
    /**
     * 轴文字位置
     */
    protected int gravity = Gravity.CENTER;
    /**
     * 轴方位（上下左右，横向轴可以设置上下，竖轴可以设置左右）
     */
    protected int direction;
    /**
     * 是否是线性图
     * 因为线性图一般从0开始绘制轴文字，而柱状图从每个间隔中间开始绘制
     */
    protected boolean isLine;
    /**
     * 文字格式化
     */
    private IFormat<V> format;
    /**
     * 是否显示轴
     */
    private boolean isDisplay = true;

    /**
     *设置是否绘制网格
     * @param drawGrid 是否绘制
     */
    public void setDrawGrid(boolean drawGrid) {
        isDrawGrid = drawGrid;
    }

    /**
     * 获取轴样式
     * 默认赋予样式，可以直接get来使用
     * @return 轴样式
     */
    public LineStyle getAxisStyle() {
        return axisStyle;
    }

    /**
     * 设置轴样式
     * 如果你对LineStyle提供的样式不满意，可以自定义新的LineStyle
     * @param axisStyle 轴样式
     */
    public void setAxisStyle(LineStyle axisStyle) {
        this.axisStyle = axisStyle;
    }

    /**
     * 获取刻度字体样式
     *  默认赋予样式，可以直接get来使用
     * @return 刻度字体样式
     */
    public FontStyle getScaleStyle() {
        return scaleStyle;
    }

    /**
     * 设置刻度字体样式
     * 如果你对FontStyle提供的样式不满意，可以自定义新的FontStyle
     * @param scaleStyle 刻度字体样式
     */
    public void setScaleStyle(FontStyle scaleStyle) {
        this.scaleStyle = scaleStyle;
    }

    /**
     * 获取网格样式
     * @return 网格样式
     */
    public LineStyle getGridStyle() {
        return gridStyle;
    }

    /**
     * 设置网格样式
     * @param gridStyle 网格样式
     */
    public void setGridStyle(LineStyle gridStyle) {
        this.gridStyle = gridStyle;
    }

    /**
     * 设置轴偏移方位
     * @param gravity 偏移方位
     */
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }


    /**
     * 是否是线性图表
     * 因为线性图一般从0开始绘制轴文字，而柱状图从每个间隔中间开始绘制
     * @param isLine 是否是线性图表
     */
    public void isLine(boolean isLine) {
        this.isLine = isLine;
    }

    /**
     * 是否显示轴
     * @return 是否显示轴
     */
    public boolean isDisplay() {
        return isDisplay;
    }


    /**
     * 绘制轴
     * 判断是否需要绘制以及调用绘制轴刻度和绘制轴线
     * @param canvas 画布
     * @param rect  绘制范围
     * @param helper 缩放移动辅助类
     * @param paint  画笔
     * @param chartData 图表数据
     */
    @Override
    public void draw(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData<? extends BarData> chartData) {
        if(isDisplay) {
            drawScale(canvas, rect, helper, paint, chartData);
            drawAxis(canvas, rect, paint, chartData);
        }
    }

    /**
     * 绘制轴刻度
     * @param canvas 画布
     * @param rect 绘制范围
     * @param helper 缩放移动辅助类
     * @param paint 画笔
     * @param chartData 图表数据
     */
    protected void drawScale(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData<? extends BarData> chartData) {
        ScaleData scaleData = chartData.getScaleData();
        Rect clipRect = new Rect(rect);
        Rect scaleRect = scaleData.scaleRect;
        Rect zoomRect = helper.getZoomProviderRect(scaleData.getOffsetRect(new Rect(rect), scaleRect));

        chartData.getScaleData().zoom = helper.getZoom();

        if (direction == AxisDirection.BOTTOM || direction == AxisDirection.TOP) {
            zoomRect.top = rect.top;
            zoomRect.bottom = rect.bottom;
            clipRect.left = rect.left + scaleRect.left;
            clipRect.right = rect.right - scaleRect.right;
        } else {
            zoomRect.left = rect.left;
            zoomRect.right = rect.right;
            clipRect.top = rect.top + scaleRect.top;
            clipRect.bottom = rect.bottom - scaleRect.bottom;
        }
        drawScale(canvas, zoomRect, clipRect, paint, chartData);
    }

    /**
     * 绘制轴线
     * @param canvas 画布
     * @param rect 绘制范围
     * @param paint 画笔
     * @param chartData 图表数据
     */
    protected void drawAxis(Canvas canvas, Rect rect, Paint paint,  ChartData<? extends BarData> chartData) {
        if(isShowAxisLine) {
            Rect scaleRect = chartData.getScaleData().scaleRect;
            axisStyle.fillPaint(paint);
            int[] r = calculation(rect, scaleRect);
            Path path = new Path();
            path.moveTo(r[0],r[1]);
            path.lineTo(r[2],r[3]);
            canvas.drawPath(path, paint);
        }

    }

    /**
     * 计算轴范围
     * @param rect 原始范围
     * @param scaleRect 缩放范围
     * @return 轴上下左右的大小
     */
    protected abstract int[] calculation(Rect rect, Rect scaleRect);


    /**
     * 提供给子类绘制刻度抽象方法
     * @param canvas 画布
     * @param rect  原始范围
     * @param clipRect 裁切轴之后范围
     * @param paint  画笔
     * @param chartData 图表数据
     */
    protected abstract void drawScale(Canvas canvas, Rect rect, Rect clipRect, Paint paint,ChartData<? extends BarData> chartData);

    /**
     * 获取文字格式化
     * @return 文字格式化
     */
    public IFormat<V> getFormat() {
        return format;
    }

    /**
     * 设置文字格式化
     * @param format 文字格式化
     */
    @Override
    public void setFormat(IFormat<V> format) {
        this.format = format;
    }

    /**
     * 是否显示轴线
     * @return 是否显示轴线
     */
    public boolean isShowAxisLine() {
        return isShowAxisLine;
    }
    /**
     * 设置是否显示轴线
     * @param showAxisLine 是否显示轴线
     */
    public void setShowAxisLine(boolean showAxisLine) {
        isShowAxisLine = showAxisLine;
    }
    /**
     * 设置是否显示轴
     * @param isShow 是否显示轴
     */
    @Override
    public void setDisplay(boolean isShow) {
        this.isDisplay = isShow;
    }
}
