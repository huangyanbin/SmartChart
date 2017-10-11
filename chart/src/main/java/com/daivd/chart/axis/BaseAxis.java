package com.daivd.chart.axis;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.matrix.MatrixHelper;


/**
 * Created by huang on 2017/9/26.
 */

public  abstract class BaseAxis implements IAxis {

    protected LineStyle lineStyle = new LineStyle();
    protected FontStyle scaleStyle = new FontStyle();
    protected LineStyle gridStyle = new LineStyle(); //网格样式
    protected boolean isDrawGrid; //是否绘制网格
    protected int  gravity = Gravity.CENTER;
    protected AxisDirection direction;
    protected boolean isLine;

    public void setDrawGrid(boolean drawGrid) {
        isDrawGrid = drawGrid;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public FontStyle getScaleStyle() {
        return scaleStyle;
    }

    public void setScaleStyle(FontStyle scaleStyle) {
        this.scaleStyle = scaleStyle;
    }

    public LineStyle getGridStyle() {
        return gridStyle;
    }

    public void setGridStyle(LineStyle gridStyle) {
        this.gridStyle = gridStyle;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    public void isLine(boolean isLine){
        this.isLine = isLine;
    }

    @Override
    public void draw(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint, ChartData<LineData> chartData) {
        drawScale(canvas,rect,helper,paint,chartData);
        drawAxis(canvas,rect,paint,chartData);
    }


    protected void drawScale(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint,  ChartData<LineData> chartData) {
        ScaleData scaleData = chartData.getScaleData();
        Rect clipRect = new Rect(rect);
        Rect scaleRect = scaleData.scaleRect;
           Rect zoomRect = helper.getZoomProviderRect(scaleData.getOffsetRect(new Rect(rect),scaleRect));

           chartData.getScaleData().zoom = helper.getZoom();
            int padding =  scaleStyle.getPadding();
            if(direction == AxisDirection.BOTTOM || direction == AxisDirection.TOP){
                zoomRect.top = rect.top;
                zoomRect.bottom = rect.bottom;
                clipRect.left = rect.left + scaleRect.left/2-padding;
                clipRect.right = rect.right - scaleRect.right/2-padding;
            }else{
                zoomRect.left = rect.left;
                zoomRect.right = rect.right;
                clipRect.top = rect.top + scaleRect.top/2-padding;
                clipRect.bottom = rect.bottom - scaleRect.bottom/2-padding;
            }
            canvas.save();
            canvas.clipRect(clipRect);
            drawScale(canvas,zoomRect,clipRect,paint,chartData);
            canvas.restore();

    }

    @Override
    public String formatVerticalAxisData(double value) {
        return String.valueOf(value);
    }

    @Override
    public String formatHorizontalAxisData(String value) {
        return value;
    }

    protected   abstract void drawAxis(Canvas canvas, Rect rect, Paint paint, ChartData<LineData> chartData);

    protected   abstract void drawScale(Canvas canvas, Rect rect,Rect clipRect, Paint paint,  ChartData<LineData> chartData);

}
