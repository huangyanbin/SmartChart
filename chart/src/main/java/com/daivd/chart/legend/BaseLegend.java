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
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.provider.component.point.Point;

import java.util.List;

import static android.R.attr.startY;

/**BaseLegend
 * Created by huang on 2017/9/29.
 */

public class BaseLegend<C extends ColumnData> implements ILegend<C> {

    private static final float DEFAULT_PERCENT = 0.2f;
    private FontStyle fontStyle;
    private IPoint point;
    private float percent = DEFAULT_PERCENT;
    private int legendDirection = BOTTOM;
    private ChartData<C> chartData;
    private int padding = 10;
    private PointF pointF;
    private boolean isSelectColumn = true;
    private OnClickLegendListener<C> onClickLegendListener;


    public BaseLegend(){
        point = new Point();
        ((Point)point).getPointStyle().setWidth(point.getWidth()*2);
        fontStyle = new FontStyle();
    }

    /**
     * Calculate the size of the legend so that the legend position can be calculated
     */
    @Override
    public void computeLegend(ChartData<C> chartData, Rect rect, Paint paint) {
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

    /**
     *draw legend
     */
    @Override
    public void drawLegend(Canvas canvas, Rect rect, Paint paint) {
        Rect legendRect = new Rect(rect);
        switch (legendDirection) {
            case TOP:
                legendRect.bottom = rect.top + (int) (rect.height() * percent);
                break;
            case LEFT:
                legendRect.right = rect.left+ (int) (rect.width()*percent);
                break;
            case RIGHT:
                legendRect.left = rect.right- (int) (rect.width()*percent);
                break;
            case BOTTOM:
                legendRect.top = rect.bottom- (int) (rect.height() * percent);
                break;
        }
        List<C> columnDataList = chartData.getColumnDataList();
        int  maxLegendNameLength = 0;
        int columnDataSize = columnDataList.size();
        String maxLengthColumnName = null;
        for(int i = 0;i <columnDataSize;i++){
            ColumnData columnData = columnDataList.get(i);
            String name =  columnData.getName();
            if(maxLegendNameLength < name.length()){
                maxLengthColumnName = name;
                maxLegendNameLength= name.length();
            }
        }
        fontStyle.fillPaint(paint);
        int textWidth = (int) paint.measureText(maxLengthColumnName);//文本长度
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        int maxLegendLength = (int) (point.getWidth()+padding*3+textWidth);
        int columnSize = legendRect.width()/maxLegendLength; //列
        columnSize = columnSize >0?columnSize:1;
        int rowSize = columnDataSize/columnSize;
        rowSize = rowSize >0?rowSize:1;
        int perHeight = (int) (textHeight + padding);
        int perWidth =  legendRect.width()/columnSize;
        int offsetY = (legendRect.height()-perHeight*rowSize)/2;
        offsetY =  offsetY >0 ?offsetY:0;
        int offsetX = columnDataSize< columnSize ? (columnSize - columnDataSize)*perWidth/2:0;
        for(int i = 0;i <columnDataList.size();i++) {
            int column = i % columnSize;
            int row = i / columnSize;
            int startX = offsetX+legendRect.left + column  *perWidth +(perWidth-maxLegendLength)/2;
            int startY = legendRect.top +offsetY+ row * perHeight;
            C columnData = columnDataList.get(i);
            String name = columnData.getName();
            float pointWidth = point.getWidth();
            if (pointF != null && isClickRect(startX-pointWidth/2, startX +perWidth-pointWidth/2, startY-padding/2, startY + perHeight+padding/2)) {
                if (isSelectColumn) {
                    columnData.setDraw(!columnData.isDraw());
                }
                pointF = null;
                if (onClickLegendListener != null) {
                    onClickLegendListener.onClickLegend(columnData, this);
                }
            }
            paint.setColor(columnData.getColor());
            drawPoint(canvas, columnData.isDraw(), startX, (int) (startY - pointWidth / 2), paint);
            startX += pointWidth + padding;
            drawText(canvas, startX, startY, name, paint);
        }
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

    protected void drawPoint(Canvas canvas,boolean isDraw,int x, int y, Paint paint){
        float w = point.getWidth();
        x += w/2;
        point.drawPoint(canvas,x,y,!isDraw,paint);

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

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    public void setSelectColumn(boolean selectColumn) {
        isSelectColumn = selectColumn;
    }
}
