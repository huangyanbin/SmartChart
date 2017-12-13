package com.daivd.chart.provider.barLine;



import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.data.BarLineData;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.provider.component.line.BrokenLineModel;
import com.daivd.chart.provider.component.line.CurveLineModel;

import java.util.ArrayList;
import java.util.List;


/**状状图和线性图结合
 * Created by huang on 2017/9/26.
 */

public  class BarLineProvider extends BarProvider<BarLineData> {

    private List<Float> pointX = new ArrayList<>();
    private List<Float> pointY = new ArrayList<>();
    private LineStyle lineStyle = new LineStyle();


    @Override
    public void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        List<BarLineData> columnDataList = chartData.getColumnDataList();
        int barColumnSize = getBarColumnSize();
        int rowSize = chartData.getCharXDataList().size();
        double perWidth = zoomRect.width()/(barColumnSize*rowSize);
        double perBarWidth =perWidth - getCategoryPadding() / 2;
        int columnSize = columnDataList.size();
        int barPosition =-1;
        for (int i = 0; i < columnSize; i++) {
            BarLineData columnData = columnDataList.get(i);
            if (!columnData.isDraw()) {
                continue;
            }
            if(columnData.getModel() == BarLineData.BAR) {
                barPosition++;
            }
            for (int j = 0; j < rowSize; j++) {
                double value = columnData.getChartYDataList().get(j);
                int top = (int) getStartY(zoomRect, value, columnData.getDirection());
                if(columnData.getModel() == BarLineData.BAR) {
                    int left = (int) (((j * barColumnSize + barPosition) * perBarWidth)
                            + j * getCategoryPadding() + zoomRect.left);
                    int right = (int) (left + perBarWidth) - getSeriesPadding();
                    int bottom = zoomRect.bottom;
                    paint.setColor(columnData.getColor());
                    Rect barRect = new Rect(left, top, right, bottom);
                    drawBar(canvas, barRect, value,i,j, paint);
                }else{
                    float x = (float)(zoomRect.left+perWidth*(j+0.5));
                    drawLine(canvas,j== 0,j == rowSize-1,columnData,x,top,value,i,j,paint);
                }
            }
        }
        drawLevelLine(canvas, zoomRect, paint);
    }


    protected void drawLine(Canvas canvas, boolean isNewBar,boolean isLast, BarLineData columnData, float x,float y, double value,int position,int line, Paint paint) {

        if(columnData.getModel() ==BarLineData.CURVE
                || columnData.getModel() ==BarLineData.BROKEN){
            if(isNewBar){
                pointX.clear();
                pointY.clear();
            }
            pointX.add(x);
            pointY.add(y);
            drawPointText(canvas,value,x, y,position,line,paint);
            if(isLast){
                Path path;
                if(columnData.getModel() ==BarLineData.CURVE){
                    path = new CurveLineModel().getLinePath(pointX,pointY);
                }else{
                    path = new BrokenLineModel().getLinePath(pointX,pointY);
                }
                lineStyle.setColor(paint.getColor());
                lineStyle.fillPaint(paint);
                canvas.drawPath(path,paint);
            }
        }
    }

    protected int getBarColumnSize() {
        List<BarLineData> lineDates =  chartData.getColumnDataList();
        int barSize = 0;
        for(int i =0;i < lineDates.size();i++){
            if(lineDates.get(i).getModel() == BarLineData.BAR){
                barSize++;
            }
        }
        if(barSize == 0){
            throw new ChartException("There must be a set of bar data!");
        }
        return barSize;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }
}
