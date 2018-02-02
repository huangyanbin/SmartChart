package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.data.BarData;
import com.daivd.chart.utils.ColorUtils;

import java.util.List;


/**
 * 柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public class BarProvider<C extends BarData> extends BaseBarLineProvider<C> {

    private int categoryPadding = 20;
    private int seriesPadding = 10;


    @Override
    public void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        List<C> columnDataList = chartData.getColumnDataList();
        int columnSize = columnDataList.size();
        int rowSize = chartData.getCharXDataList().size();
        double width = (zoomRect.width()-(columnSize+1)*categoryPadding) / (columnSize * rowSize);
        PointF clickPoint = null;
        int clickPosition = 0;
        int clickColumnPosition = 0;
        boolean isClickRect1 = (pointF != null && rect.contains((int) pointF.x, (int) pointF.y));
        boolean isClickRect = false;

        for (int i = 0; i < columnSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                C columnData = columnDataList.get(i);
                if (!columnData.isDraw()) {
                    continue;
                }
                double value = columnData.getChartYDataList().get(j);
                int left = (int) (((j * columnSize + i) * width)
                        + j * categoryPadding + zoomRect.left);
                int right = (int) (left + width) - seriesPadding;
                int top = (int) getStartY(zoomRect, value, columnData.getDirection());
                int bottom = zoomRect.bottom;
                paint.setColor(columnData.getColor());
                if (isClickRect1 && isClickRect(left, right, top, bottom)) {
                    paint.setColor(ColorUtils.getDarkerColor(columnData.getColor()));
                    isClickRect = true;
                    clickPoint = new PointF((left + right) / 2, top);
                    clickPosition = j;
                    clickColumnPosition = i;
                    if (onClickColumnListener != null) {
                        onClickColumnListener.onClickColumn(columnData, j);
                    }
                }
                Rect barRect = new Rect(left, top, right, bottom);
                drawBar(canvas, barRect, value,i,j, paint);
                drawTip(canvas,left+barRect.width()/2,top,columnData,j);
            }
        }
        drawLevelLine(canvas, zoomRect, paint);
        if (isClickRect && containsRect(clickPoint.x, clickPoint.y)) {
            drawCross(canvas, clickPoint.x, clickPoint.y, zoomRect, paint);
            super.matrixRectEnd(canvas, rect);
            drawMark(canvas,clickPoint.x, clickPoint.y,rect, clickPosition, clickColumnPosition);
        }
    }



    @Override
    protected void matrixRectEnd(Canvas canvas, Rect rect) {

    }


    protected void drawBar(Canvas canvas, Rect rect,double value,  int position,int line,Paint paint) {
        canvas.drawRect(rect, paint);
        drawPointText(canvas, value, (rect.right + rect.left) / 2, rect.top, position,line,paint);
    }


    private void drawCross(Canvas canvas, float x, float y, Rect rect, Paint paint) {

        if (isOpenCross() && getCross() != null) {
            PointF pointF = new PointF(x, y);
            getCross().drawCross(canvas, pointF, rect, paint);
        }
    }



    protected boolean isClickRect(float left, float right, float top, float bottom) {
        return pointF != null && pointF.x >= left && pointF.x <= right && pointF.y >= top && pointF.y <= bottom;
    }


    @Override
    public double[] setMaxMinValue(double maxValue, double minValue) {
        double dis = Math.abs(maxValue - minValue);
        maxValue = maxValue + dis * 0.2;
        if (minValue > 0) {
            minValue = 0;
        } else {
            minValue = minValue - dis * 0.2;
        }
        return new double[]{maxValue, minValue};
    }

    public int getSeriesPadding() {
        return seriesPadding;
    }

    public void setSeriesPadding(int seriesPadding) {
        this.seriesPadding = seriesPadding;
    }

    public int getCategoryPadding() {
        return categoryPadding;
    }

    public void setCategoryPadding(int categoryPadding) {
        this.categoryPadding = categoryPadding;
    }
}
