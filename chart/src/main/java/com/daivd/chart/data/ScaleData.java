package com.daivd.chart.data;

import android.graphics.Rect;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.matrix.MatrixHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2017/9/26.
 * Scale data
 */

public class ScaleData {

    public boolean isLeftHasValue = false;
    public boolean isRightHasValue = false;
    //竖轴数据
    public double maxLeftValue; //最大值
    public double minLeftValue; //最小值

    //竖轴数据
    public double maxRightValue; //最大值
    public double minRightValue; //最小值

    public int totalScale = 5; //刻度数量

    public Rect scaleRect = new Rect();

    //横轴数据
    public int  rowSize; //列数据
    public float zoom = MatrixHelper.MIN_ZOOM; //放大比例


    public List<Double> getScaleList(int direction){

        List<Double> scaleList = new ArrayList<>();
        int total = (int) (totalScale *zoom);
        double scale = getTotalScaleLength(direction) /(total-1);
        double minValue = getMinScaleValue(direction);
        for(int i = 0;i < total;i++){
            scaleList.add(minValue +scale*i);
        }
        return scaleList;
    }

    /**
     * 获取最大刻度
     * @param direction
     * @return
     */
    public double getMaxScaleValue(int direction){
        if(direction == IAxis.AxisDirection.LEFT){
            return  maxLeftValue;
        }
        return  maxRightValue;
    }

    /**
     * 获取最小刻度
     * @param direction
     * @return
     */
    public double getMinScaleValue(int direction){
        if(direction == IAxis.AxisDirection.LEFT){
            return  minLeftValue;
        }
        return  minRightValue;
    }

    /**
     *获取总刻度值
     * @param direction 方向
     * @return 总刻度
     */
    public double getTotalScaleLength(int direction){
        if(direction == IAxis.AxisDirection.LEFT){
            return maxLeftValue - minLeftValue;
        }
        return  maxRightValue - minRightValue;
    }


    public Rect getOffsetRect(Rect rect, Rect offsetRect){
        rect.left = rect.left + offsetRect.left;
        rect.right = rect.right - offsetRect.right;
        rect.top = rect.top + offsetRect.top;
        rect.bottom = rect.bottom - offsetRect.bottom;
        return rect;

    }

    public void resetScale(ScaleSetData scaleSetData,int direction){
        if(direction == IAxis.AxisDirection.LEFT){
            if(scaleSetData.isStartZoom()){
                minLeftValue = 0;
            }else {
               if(scaleSetData.isHasMinValue()){
                   minLeftValue = scaleSetData.getMinValue();
               }
            }
            if(scaleSetData.isHasMaxValue()){
                maxLeftValue = scaleSetData.getMaxValue();
            }
        }else{
            if(scaleSetData.isStartZoom()){
                minRightValue = 0;
            }else {
                if(scaleSetData.isHasMinValue()){
                    minRightValue = scaleSetData.getMinValue();
                }
            }
            if(scaleSetData.isHasMaxValue()){
                maxRightValue = scaleSetData.getMaxValue();
            }
        }
    }
}
