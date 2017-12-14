package com.daivd.chart.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.daivd.chart.component.base.ILegend;
import com.daivd.chart.component.base.PercentComponent;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.listener.OnClickLegendListener;
import com.daivd.chart.provider.component.point.ILegendPoint;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.daivd.chart.provider.component.point.Point;

import java.util.List;

/**
 * 图表图例
 * @author huangyanbin
 */

public class Legend<C extends ColumnData> extends PercentComponent<ChartData<C>> implements ILegend<C> {

    private FontStyle fontStyle;
    private ILegendPoint point;
    private int padding = 10;
    private PointF pointF;
    private boolean isSelectColumn = true;
    private OnClickLegendListener<C> onClickLegendListener;
    private boolean isDisplay = true;

    public Legend(){
        LegendPoint p = new LegendPoint();
        p.getPointStyle().setWidth(p.getPointStyle().getWidth()*2);
        point = p;
        fontStyle = new FontStyle();
    }

    @Override
    public void computeRect(Rect chartRect) {
        if(isDisplay) {
            super.computeRect(chartRect);
        }
    }

    /**
     * 绘制
     * @param canvas 画布
     * @param chartData 数据
     * @param paint 画笔
     */
    @Override
    public void draw(Canvas canvas, ChartData<C> chartData, Paint paint) {

        if(isDisplay) {
            paint.setTextAlign(Paint.Align.LEFT);
            Rect legendRect = getRect();
            List<C> columnDataList = chartData.getColumnDataList();
            int maxLegendNameLength = 0;
            int columnDataSize = columnDataList.size();
            String maxLengthColumnName = null;
            for (int i = 0; i < columnDataSize; i++) {
                ColumnData columnData = columnDataList.get(i);
                String name = columnData.getName();
                if (maxLegendNameLength < name.length()) {
                    maxLengthColumnName = name;
                    maxLegendNameLength = name.length();
                }
            }
            fontStyle.fillPaint(paint);
            int textWidth = (int) paint.measureText(maxLengthColumnName);//文本长度
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            int maxLegendLength = (int) (point.getWidth() + padding * 3 + textWidth);
            int columnSize = legendRect.width() / maxLegendLength; //列
            columnSize = columnSize > 0 ? columnSize : 1;
            int rowSize = columnDataSize / columnSize;
            rowSize = rowSize > 0 ? rowSize : 1;
            int perHeight = (int) (textHeight + padding);
            int perWidth = legendRect.width() / columnSize;
            int offsetY = (legendRect.height() - perHeight * rowSize) / 2;
            offsetY = offsetY > 0 ? offsetY : 0;
            int offsetX = columnDataSize < columnSize ? (columnSize - columnDataSize) * perWidth / 2 : 0;
            for (int i = 0; i < columnDataList.size(); i++) {
                int column = i % columnSize;
                int row = i / columnSize;
                int startX = offsetX + legendRect.left + column * perWidth + (perWidth - maxLegendLength) / 2;
                int startY = legendRect.top + offsetY + row * perHeight;
                C columnData = columnDataList.get(i);
                String name = columnData.getName();
                float pointWidth = point.getWidth();
                float pointHeight = point.getHeight();
                if (pointF != null && isClickRect(startX - pointWidth, startX + perWidth, startY - padding / 2, startY + perHeight + padding / 2)) {
                    if (isSelectColumn) {
                        columnData.setDraw(!columnData.isDraw());
                    }
                    pointF = null;
                    if (onClickLegendListener != null) {
                        onClickLegendListener.onClickLegend(columnData, this);
                    }
                }
                paint.setColor(columnData.getColor());
                drawPoint(canvas, columnData.isDraw(), startX, (int) (startY - textHeight / 2 + pointHeight / 2), paint);
                startX += pointWidth + padding;
                drawText(canvas, startX, startY, name, paint);
            }
        }
    }


    @Override
    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    /**
     * Determine whether to click or not rect
     */

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

    private void drawPoint(Canvas canvas,boolean isDraw,int x, int y, Paint paint){
        float w = point.getWidth();
        x += w/2;
        point.drawPoint(canvas,x,y,0,!isDraw,paint);

    }




    @Override
    public FontStyle getFontStyle() {
        return fontStyle;
    }


    @Override
    public void onClickLegend(PointF pointF) {
        this.pointF = pointF;
    }


    @Override
    public void setOnClickLegendListener(OnClickLegendListener<C> onClickLegendListener) {
       this.onClickLegendListener = onClickLegendListener;
    }

    @Override
    public int getPadding() {
        return padding;
    }
    @Override
    public void setPadding(int padding) {
        this.padding = padding;
    }

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(ILegendPoint point) {
        this.point = point;
    }
    @Override
    public void setSelectColumn(boolean selectColumn) {
        isSelectColumn = selectColumn;
    }

    @Override
    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }


}
