package com.daivd.chart.core;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.legend.BaseChartTitle;
import com.daivd.chart.legend.BaseLegend;
import com.daivd.chart.legend.EmptyView;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.IEmpty;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.matrix.ChartGestureObserver;
import com.daivd.chart.matrix.MatrixHelper;
import com.daivd.chart.provider.IProvider;


/**
 * Created by huang on 2017/9/26.
 */

public abstract class BaseChart<P extends IProvider,C extends ColumnData> extends View implements ChartGestureObserver,OnClickChartListener {

    protected int width; //高
    protected int height; //宽
    protected int paddingLeft = 10;
    protected int paddingRight= 10;
    protected int paddingTop = 10;
    protected int paddingBottom = 10;
    protected Rect chartRect = new Rect();
    protected ILegend legend;
    protected IChartTitle chartTitle;
    protected P provider;//内容绘制者
    protected Paint paint;
    protected ChartData<C> chartData;
    protected MatrixHelper matrixHelper;
    protected boolean isShowChartName = true;
    protected boolean isCharEmpty;
    protected IEmpty emptyView;

    public BaseChart(Context context) {
        super(context);
        init();
    }

    public BaseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected  void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartTitle = new BaseChartTitle();
        legend = new BaseLegend();
        legend.setOnClickChartListener(this);
        matrixHelper = new MatrixHelper(getContext());
        matrixHelper.register(this);
        emptyView = new EmptyView();
        provider = initProvider();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return matrixHelper.HandlerGestureDetector(event);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (chartData != null) {
            computePaddingRect();
            if (isShowChartName) {
                chartTitle.computeTitle(chartData, chartRect, paint);
                chartTitle.drawTitle(canvas, chartRect, paint);
                computeTitleRect();
            }
            if (!chartData.isEmpty()) {
                legend.computeLegend(chartData, chartRect, paint);
                legend.drawLegend(canvas, chartRect, paint);
                computeLegendRect();
            }
            if (!isCharEmpty) {
               drawContent(canvas);
            } else {
                emptyView.drawEmpty(canvas, chartRect, paint);
            }
        }
    }

    protected abstract void drawContent(Canvas canvas);

    /**
     * 计算绘制刻度之后的大小
     */
    private void computeTitleRect(){

        Rect rect = chartData.getScaleData().titleRect;
        computeChartRect(rect);
    }

    /**
     * 计算绘制图示之后的大小
     */
    private void computeLegendRect(){

        Rect rect = chartData.getScaleData().legendRect;
        computeChartRect(rect);
    }


    protected void computeChartRect(Rect rect) {
        chartRect.left = chartRect.left + rect.left;
        chartRect.top = chartRect.top +rect.top;
        chartRect.bottom = chartRect.bottom - rect.bottom;
        chartRect.right = chartRect.right -rect.right;
    }


    /**
     * 计算图表的区域
     * @return
     */
    private void computePaddingRect(){
        chartRect.left = paddingLeft;
        chartRect.top = paddingTop;
        chartRect.bottom = height -paddingBottom-paddingTop;
        chartRect.right = width - paddingRight-paddingLeft;
    }


    public void setPadding(int[] padding){
        this.paddingLeft = padding[0];
        this.paddingTop = padding[1];
        this.paddingRight = padding[2];
        this.paddingBottom= padding[3];

    }

    protected abstract P initProvider();

    public void setProvider(P provider) {
        this.provider = provider;
    }

    public P getProvider() {
        return provider;
    }

    public ChartData<C> getChartData() {
        return chartData;
    }

    public void setChartData(ChartData<C> chartData) {
        isCharEmpty = !provider.calculation(chartData);
        this.chartData = chartData;
        invalidate();
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
