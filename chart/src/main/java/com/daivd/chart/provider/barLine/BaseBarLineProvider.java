package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.provider.component.cross.ICross;
import com.daivd.chart.provider.component.grid.IGrid;
import com.daivd.chart.provider.component.level.ILevel;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.provider.BaseProvider;
import com.daivd.chart.provider.component.path.IPath;
import com.daivd.chart.provider.component.tip.ITip;

import java.util.ArrayList;
import java.util.List;

/**线和柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public abstract class BaseBarLineProvider<C extends BarData> extends BaseProvider<C> {

    private ICross cross;
    private boolean isOpenCross;
    protected ITip<C,?> tip;
    private List<ILevel> levelLine = new ArrayList<>();
    private float chartPercent = 1;
    private boolean isFromBottom;



    @Override
    public boolean calculationChild( ChartData<C> chartData) {
        this.chartData = chartData;
        ScaleData scaleData =this.chartData.getScaleData();
        List<C> columnDataList  =  chartData.getColumnDataList();
        if(columnDataList == null || columnDataList.size() == 0){
            return  false;
        }
        scaleData.rowSize = chartData.getCharXDataList().size();
        int columnSize = columnDataList.size();
        for(int i = 0 ; i <columnSize; i++){
            BarData columnData = columnDataList.get(i);
            if(!columnData.isDraw()){
                continue;
            }
            List<Double> chartYDataList = columnData.getChartYDataList();
            if(chartYDataList == null || chartYDataList.size() == 0){
                throw new ChartException("Please set up Column data");
            }

            if(chartYDataList.size() != scaleData.rowSize){
                throw new ChartException("Column rows data inconsistency");
            }
            double[] scale = getColumnScale(chartYDataList);
            scale = setMaxMinValue(scale[0],scale[1]);
            if(columnData.getDirection() == IAxis.AxisDirection.LEFT){
                if(!scaleData.isLeftHasValue){
                    scaleData.maxLeftValue = scale[0];
                    scaleData.minLeftValue = scale[1];
                    scaleData.isLeftHasValue = true;
                }else{
                    scaleData.maxLeftValue = Math.max( scaleData.maxLeftValue,scale[0]);
                    scaleData.minLeftValue =  Math.min( scaleData.minLeftValue,scale[1]);
                }

            }else{
                if(!scaleData.isRightHasValue){
                    scaleData.maxRightValue = scale[0];
                    scaleData.minRightValue= scale[1];
                    scaleData.isRightHasValue = true;
                }else{
                    scaleData.maxRightValue = Math.max(scaleData.maxRightValue,scale[0]);
                    scaleData.minRightValue =  Math.min(scaleData.minRightValue,scale[1]);
                }
            }
        }
        return chartData.getScaleData().rowSize != 0;


    }

    private double[] getColumnScale(List<Double> values) {
        double maxValue = 0;
        double minValue =0;
        int size = values.size();
        for(int j= 0;j < size;j++) {
            double d = values.get(j) ;
            if(j == 0){
                maxValue = d;
                minValue = d;
            }
            if (maxValue < d){
                maxValue = d;
            }else if(minValue >d){
                minValue = d;
            }
        }
        return new double[] {maxValue,minValue};
    }

    protected float getStartY(Rect zoomRect, double value, int direction){
        ScaleData scaleData = chartData.getScaleData();
        double minValue = scaleData.getMinScaleValue(direction);
        double totalScaleLength = scaleData.getTotalScaleLength(direction);
        float length = (float) ((value - minValue) * zoomRect.height()*chartPercent / totalScaleLength);
        return zoomRect.bottom-(isFromBottom?0:(1-chartPercent)/2*zoomRect.height()) - length;

    }

    /**
     * 绘制水平线
     */
    void drawLevelLine(Canvas canvas, Rect zoomRect, Paint paint){

        if(levelLine.size() > 0) {
            for(ILevel level:levelLine){
                float levelY = getStartY(zoomRect,level.getValue(),level.getAxisDirection());
                if(containsRect(getProviderRect().centerX(),levelY)) {
                    level.drawLevel(canvas, getProviderRect(), levelY, paint);
                }
            }
        }
    }

    /**
     *绘制提示
     */
    void drawTip(Canvas canvas,float x, float y,C c,int position){
        if(tip != null){
            tip.drawTip(canvas,x,y,getProviderRect(),c,position);
        }
    }

     void drawMark(Canvas canvas,float x, float y,Rect rect, int position, int columnPosition) {

        if (markView != null && isOpenMark()) {
            markView.drawMark(canvas,x, y, rect,chartData.getCharXDataList().get(position),
                    chartData.getColumnDataList().get(columnPosition), position);
        }
    }




    public  abstract double[] setMaxMinValue(double maxMinValue, double minValue);



    public void addLevelLine(ILevel levelLine) {
        this.levelLine.add(levelLine);
    }



    public boolean isOpenCross() {
        return isOpenCross;
    }

    public void setOpenCross(boolean openCross) {
        isOpenCross = openCross;
    }

    public ICross getCross() {
        return cross;
    }

    public void setCross(ICross cross) {
        this.cross = cross;
    }

    public List<ILevel> getLevelLine() {
        return levelLine;
    }

    public ITip<C,?> getTip() {
        return tip;
    }

    public void setTip(ITip<C,?> tip) {
        this.tip = tip;
    }

    public float getChartPercent() {
        return chartPercent;
    }
    public boolean isFromBottom() {
        return isFromBottom;
    }

    public void setFromBottom(boolean fromBottom) {
        isFromBottom = fromBottom;
    }
    public void setChartPercent(float chartPercent) {
        this.chartPercent = chartPercent;
    }
}
