package com.daivd.chart.provider.barLine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.axis.IAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.provider.component.cross.ICross;
import com.daivd.chart.provider.component.level.ILevel;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.provider.BaseProvider;

import java.util.ArrayList;
import java.util.List;

/**线和柱状内容绘制
 * Created by huang on 2017/9/26.
 */

public abstract class BarLineProvider extends BaseProvider<LineData> {

    private ICross cross;
    private boolean isOpenCross;
    protected List<ILevel> levelLine = new ArrayList<>();


    @Override
    public boolean calculationChild( ChartData<LineData> chartData) {
        this.chartData = chartData;
        ScaleData scaleData =this.chartData.getScaleData();
        List<LineData> columnDataList  =  chartData.getColumnDataList();
        if(columnDataList == null || columnDataList.size() == 0){
            return  false;
        }
        scaleData.rowSize = chartData.getCharXDataList().size();
        int columnSize = columnDataList.size();
        for(int i = 0 ; i <columnSize; i++){
            LineData columnData = columnDataList.get(i);
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

    /**
     * 绘制水平线
     */
    void drawLevelLine(Canvas canvas, Rect zoomRect, Paint paint){

        if(levelLine.size() > 0) {
            for(ILevel level:levelLine){
                float levelY = getStartY(zoomRect,level.getValue(),level.getAxisDirection());
                level.drawLevel(canvas,getProviderRect(),levelY,paint);
            }
        }
    }

    protected abstract float getStartY(Rect zoomRect, double value, int direction);




    public  abstract double[] setMaxMinValue(double maxMinValue, double minValue);



    public void addLevelLine(LevelLine levelLine) {
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

}
