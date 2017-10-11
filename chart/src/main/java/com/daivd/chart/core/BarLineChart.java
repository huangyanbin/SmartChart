package com.daivd.chart.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.axis.HorizontalAxis;
import com.daivd.chart.axis.VerticalAxis;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.IEmpty;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.provider.IProvider;


/**
 * Created by huang on 2017/9/26.
 */

public abstract class BarLineChart<P extends IProvider> extends BaseChart<P,LineData>  {


    protected BaseAxis horizontalAxis; //横轴
    protected BaseAxis leftVerticalAxis;//竖轴
    protected BaseAxis rightVerticalAxis;//竖轴


    public BarLineChart(Context context) {
        super(context);
        init();
    }

    public BarLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BarLineChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected  void init(){
       horizontalAxis = new HorizontalAxis();
       leftVerticalAxis = new VerticalAxis(AxisDirection.LEFT);
       rightVerticalAxis = new VerticalAxis(AxisDirection.RIGHT);
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





    public BaseAxis getHorizontalAxis() {
        return horizontalAxis;
    }

    public void setHorizontalAxis(BaseAxis horizontalAxis) {
        this.horizontalAxis = horizontalAxis;
    }

    public BaseAxis getLeftVerticalAxis() {
        return leftVerticalAxis;
    }

    public void setLeftVerticalAxis(BaseAxis leftVerticalAxis) {
        this.leftVerticalAxis = leftVerticalAxis;
    }

    public BaseAxis getRightVerticalAxis() {
        return rightVerticalAxis;
    }

    public void setRightVerticalAxis(BaseAxis rightVerticalAxis) {
        this.rightVerticalAxis = rightVerticalAxis;
    }








    public void startChartAnim(int duration){
        provider.startAnim(this,duration,new DecelerateInterpolator());
    }
    public void startChartAnim(int duration, Interpolator interpolator){
        provider.startAnim(this,duration,interpolator);
    }

    @Override
    public void onClick(float x, float y) {
        PointF pointF = new PointF(x,y);
        provider.clickPoint(pointF);
        legend.onClickLegend(pointF);
        invalidate();
    }

    @Override
    public void clickLegend(ILegend legend, ColumnData columnData) {
         isCharEmpty =  !provider.calculation(chartData);
        startChartAnim(400);
    }

    public IEmpty getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(IEmpty emptyView) {
        this.emptyView = emptyView;
    }

    public boolean isShowChartName() {
        return isShowChartName;
    }

    public void setShowChartName(boolean showChartName) {
        isShowChartName = showChartName;
    }

    public IChartTitle getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(IChartTitle chartTitle) {
        this.chartTitle = chartTitle;
    }

    public ILegend getLegend() {
        return legend;
    }

    public void setZoom(boolean zoom) {
        matrixHelper.setCanZoom(zoom);
    }

    @Override
    public void onViewChanged(float scale, float translateX, float translateY) {
        invalidate();
    }
}
