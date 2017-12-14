package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.provider.component.grid.IGrid;
import com.daivd.chart.provider.component.line.BrokenLineModel;
import com.daivd.chart.provider.component.line.ILineModel;
import com.daivd.chart.provider.component.path.IPath;
import com.daivd.chart.provider.component.path.LinePath;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 线内容绘制
 * Created by huang on 2017/9/26.
 */

public class LineProvider extends BaseBarLineProvider<LineData> {

    private LineStyle lineStyle = new LineStyle();
    private IPoint point;
    private int rowSize;
    private boolean isArea; //是否打开面积图
    private boolean isDrawLine = true;
    private int filterPointTextCount = 30; //最多显示30个点文字
    private ILineModel lineModel = new BrokenLineModel();
    private IPath path;
    private int perWidth;
    private IGrid grid;
    private boolean isStartZero =true;
    private int areaAlpha = 125;

    @Override
    public void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {

        List<LineData> columnDataList = chartData.getColumnDataList();
        int columnSize = columnDataList.size();
        rowSize = chartData.getCharXDataList().size();
        int filter = rowSize/filterPointTextCount;
        filter = filter <1 ?1 : filter;
        if(grid !=null) {
            grid.drawClickBg(canvas, pointF, zoomRect, perWidth, paint);
        }
        for (int i = 0; i < columnSize; i++) {
            LineData lineData = columnDataList.get(i);
            paint.setColor(lineData.getColor());
            if (!lineData.isDraw()) {
                continue;
            }
            List<Float> pointX = new ArrayList<>();
            List<Float> pointY = new ArrayList<>();
            List<Double> chartYDataList = lineData.getChartYDataList();

            for (int j = 0; j < rowSize; j++) {
                double value = chartYDataList.get(j);
                float x = getStartX(zoomRect, j);
                float y = getStartY(zoomRect, value, lineData.getDirection());
                if(grid !=null && i == 0) {
                    grid.drawGrid(canvas, x, zoomRect,  perWidth, paint);
                    grid.drawColumnContent(canvas,j,zoomRect,rect,perWidth,paint);
                }
                pointX.add(x);
                pointY.add(y);
                if(j % filter == 0) {
                    drawPointText(canvas, value, x, y,j,i, paint);
                }
            }
            canvas.save();
            canvas.clipRect(rect);
            drawLine(canvas, rect,zoomRect, paint, lineData, pointX, pointY);
            drawTip(canvas, pointX, pointY, lineData);
            canvas.restore();
            drawPoint(canvas, pointX, pointY,lineData, paint);

        }
        drawLevelLine(canvas, zoomRect, paint);
        drawClickCross(canvas, rect, zoomRect, paint);
    }

    private void drawLine(Canvas canvas, Rect rect,Rect zoomRect, Paint paint, LineData lineData, List<Float> pointX, List<Float> pointY) {
        LineStyle style = lineData.getLineStyle();
        style = style == null?lineStyle:style;
        style.fillPaint(paint);
        paint.setColor(lineData.getColor());
        if (isDrawLine) {
            ILineModel model = lineData.getLineModel();
            model = model == null ?lineModel:model;
            Path path=  model.getLinePath(pointX,pointY);
            if (isArea) {
                paint.setStyle(Paint.Style.FILL);
                path.lineTo(rect.right, rect.bottom);
                path.lineTo(rect.left, rect.bottom);
                path.close();
                int alphaColor = ColorUtils.changeAlpha(paint.getColor(), areaAlpha);
                paint.setColor(alphaColor);
            }
            getPath().drawPath(canvas,zoomRect,path,perWidth,paint,getProgress());
        }
    }



    @Override
    protected void matrixRectEnd(Canvas canvas, Rect rect) {

    }

    @Override
    protected void matrixRectStart(Canvas canvas, Rect rect) {

    }
    private float getStartX(Rect zoomRect, int position) {
        if(isStartZero){
            perWidth = (zoomRect.width()) / (rowSize-1);
            return (float) (position * perWidth+ zoomRect.left);
        }else {
            perWidth = (zoomRect.width()) / rowSize;
            return (float) (position * perWidth + perWidth / 2 + zoomRect.left);
        }
    }


    private void drawClickCross(Canvas canvas, Rect rect, Rect zoomRect, Paint paint) {

        if (pointF != null && (isOpenCross() || isOpenMark())) {
            int minPositionX = 0, minPositionY = 0;
            float centerX = 0, centerY = 0;
            LineData clickLineData = null;
            float minDisX = zoomRect.width();
            for (int i = 0; i < rowSize; i++) {
                float startX = getStartX(zoomRect, i);
                float disX = Math.abs(pointF.x - startX);
                if (disX < minDisX) {
                    minPositionX = i;
                    minDisX = disX;
                    centerX = startX;
                } else {
                    break;
                }
            }
            List<LineData> lineDates = chartData.getColumnDataList();
            float minDisY = zoomRect.height();
            boolean isHasLine = false;
            for (int j = 0; j < lineDates.size(); j++) {
                LineData lineData = lineDates.get(j);
                if (!lineData.isDraw()) {
                    continue;
                }
                isHasLine = true;
                double value = lineData.getChartYDataList().get(minPositionX);
                float startY = getStartY(zoomRect, value, lineData.getDirection());
                float disY = Math.abs(pointF.y - startY);
                if (disY < minDisY) {
                    centerY = startY;
                    minDisY = disY;
                    clickLineData = lineData;
                    minPositionY = j;
                }
            }
            if (!isHasLine || !containsRect(centerX, centerY)) {
                return;
            }
            if (onClickColumnListener != null) {
                onClickColumnListener.onClickColumn(clickLineData, minPositionX);
            }
            if (isOpenCross() && getCross() != null) {
                getCross().drawCross(canvas, new PointF(centerX, centerY), rect, paint);
            }
            drawMark(canvas, centerX, centerY, rect, minPositionX, minPositionY);
        }
    }

    private void drawPoint(Canvas canvas, List<Float> pointX, List<Float> pointY,LineData lineData, Paint paint) {
        IPoint linePoint = lineData.getPoint();
        linePoint = linePoint == null? point:linePoint;

        if (linePoint != null) {
            for (int i = 0; i < pointY.size(); i++) {

                float x = pointX.get(i);
                float y = pointY.get(i);
                if (containsRect(x, y)) {
                    linePoint.drawPoint(canvas, x, y, i,false, paint);
                }

            }
        }
    }

    private void drawTip(Canvas canvas, List<Float> pointX, List<Float> pointY, LineData lineData) {
        if (tip != null) {
            for (int i = 0; i < pointY.size(); i++) {
                float x = pointX.get(i);
                float y = pointY.get(i);
                drawTip(canvas, x, y, lineData, i);
            }
        }
    }


    public LineStyle getLineStyle() {
        return lineStyle;
    }


    @Override
    public double[] setMaxMinValue(double maxValue, double minValue) {
        double dis = Math.abs(maxValue - minValue);
        maxValue = maxValue + dis * 0.3;
        if (minValue > 0) {
            minValue = 0;
        } else {
            minValue = minValue - dis * 0.3;
        }
        return new double[]{maxValue, minValue};
    }
    public IPath getPath() {
        if(path == null){
            path = new LinePath();
        }
        return path;
    }

    public IGrid getGrid() {
        return grid;
    }

    public void setGrid(IGrid grid) {
        this.grid = grid;
    }

    public void setPath(IPath path) {
        this.path = path;
    }

    public void setArea(boolean isArea) {
        this.isArea = isArea;
    }

    /**
     * 设置线模式
     * @param lineModel
     */
    public void setLineModel(ILineModel lineModel) {
        this.lineModel = lineModel;
    }

    /**
     * 设置是否绘制线
     * @param drawLine 绘制线
     */
    public void setDrawLine(boolean drawLine) {
        isDrawLine = drawLine;
    }

    /**过滤图表文本数量
     * 当数据过多时，，来过滤文字显示，保持图表美观
     * @return 过滤图表文本数量
     */
    public int getFilterPointTextCount() {
        return filterPointTextCount;
    }
    /**过滤图表文本数量
     * 当数据过多时，可以使用该方法，来过滤文字显示，保持图表美观
     * @return
     */
    public void setFilterPointTextCount(int filterPointTextCount) {
        this.filterPointTextCount = filterPointTextCount;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    /**
     * 设置是否从0开始
     * @return  从0开始
     */
    public boolean isStartZero() {
        return isStartZero;
    }

    /**
     * 设置是否从0开始
     * @param startZero 从0开始
     */
    public void setStartZero(boolean startZero) {
        isStartZero = startZero;
    }

    /**
     * 获取面积图透明度
     * @return 透明度 0-255
     */
    public int getAreaAlpha() {
        return areaAlpha;
    }

    /**
     * 设置面积图的透明度
     * @param areaAlpha 透明度 0-255
     */
    public void setAreaAlpha(int areaAlpha) {
        this.areaAlpha = areaAlpha;
    }
}
