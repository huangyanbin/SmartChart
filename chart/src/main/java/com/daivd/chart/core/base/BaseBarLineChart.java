package com.daivd.chart.core.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import com.daivd.chart.axis.HorizontalAxis;
import com.daivd.chart.axis.IAxis;
import com.daivd.chart.axis.VerticalAxis;
import com.daivd.chart.core.base.BaseChart;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.provider.IProvider;


/**
 * Created by huang on 2017/9/26.
 * 线性和柱状图
 */

public abstract class BaseBarLineChart<P extends IProvider<C>,C extends LineData> extends BaseChart<P,C> {


    protected HorizontalAxis horizontalAxis; //横轴
    protected VerticalAxis leftVerticalAxis;//竖轴
    protected VerticalAxis rightVerticalAxis;//竖轴


    public BaseBarLineChart(Context context) {
        super(context);
        init();
    }

    public BaseBarLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseBarLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseBarLineChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected  void init(){
       horizontalAxis = new HorizontalAxis();
       leftVerticalAxis = new VerticalAxis(IAxis.AxisDirection.LEFT);
       rightVerticalAxis = new VerticalAxis(IAxis.AxisDirection.RIGHT);
       super.init();

    }



    protected  void drawContent(Canvas canvas){
        horizontalAxis.computeScale(chartData, chartRect, paint);
        if (chartData.getScaleData().isLeftHasValue)
            leftVerticalAxis.computeScale(chartData, chartRect, paint);
        if (chartData.getScaleData().isRightHasValue)
            rightVerticalAxis.computeScale(chartData, chartRect, paint);
        if (chartData.getScaleData().isLeftHasValue)
            leftVerticalAxis.draw(canvas, chartRect, matrixHelper, paint, chartData);
        if (chartData.getScaleData().isRightHasValue)
            rightVerticalAxis.draw(canvas, chartRect, matrixHelper, paint, chartData);
        horizontalAxis.draw(canvas, chartRect, matrixHelper, paint, chartData);
        computeScaleRect();
        provider.drawProvider(canvas, chartRect, matrixHelper, paint);
    }



    /**
     * 计算绘制刻度之后的大小
     */
    private void computeScaleRect(){

        Rect rect = chartData.getScaleData().scaleRect;
        computeChartRect(rect);
    }





    public HorizontalAxis getHorizontalAxis() {
        return horizontalAxis;
    }



    public VerticalAxis getLeftVerticalAxis() {
        return leftVerticalAxis;
    }



    public VerticalAxis getRightVerticalAxis() {
        return rightVerticalAxis;
    }








}
