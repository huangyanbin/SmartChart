package com.daivd.chart.core.base;

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

import com.daivd.chart.component.Legend;
import com.daivd.chart.component.PicGeneration;
import com.daivd.chart.component.base.IChartTitle;
import com.daivd.chart.component.base.ILegend;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.PicOption;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.component.ChartTitle;
import com.daivd.chart.component.EmptyView;
import com.daivd.chart.component.base.IEmpty;
import com.daivd.chart.listener.OnChartChangeListener;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.listener.OnClickLegendListener;
import com.daivd.chart.listener.ChartGestureObserver;
import com.daivd.chart.matrix.MatrixHelper;
import com.daivd.chart.provider.IProvider;


/**基本图表
 * Created by huang on 2017/9/26.
 */

public abstract class BaseChart<P extends IProvider<C>,C extends ColumnData> extends View
        implements ChartGestureObserver,OnClickLegendListener<C>,OnChartChangeListener {

    protected int width; //高
    protected int height; //宽
    protected int paddingLeft = 10;
    protected int paddingRight = 10;
    protected int paddingTop = 30;
    protected int paddingBottom = 30;
    protected Rect chartRect = new Rect();
    protected ILegend<C> legend;
    protected IChartTitle chartTitle;
    protected P provider;//内容绘制者
    protected Paint paint;
    protected ChartData<C> chartData;
    protected MatrixHelper matrixHelper;
    protected boolean isShowChartName = true;
    protected boolean isCharEmpty;
    protected IEmpty emptyView;
    private OnClickLegendListener<C> onClickLegendListener;
    private boolean isFirstAnim =true;

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



    /**
     * 初始化组件
     */
    protected void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartTitle = new ChartTitle();
        legend = new Legend<>();
        legend.setOnClickLegendListener(this);
        matrixHelper = new MatrixHelper(getContext());
        matrixHelper.register(this);
        matrixHelper.setOnTableChangeListener(this);
        emptyView = new EmptyView();
        provider = initProvider();
    }

    /**
     *将触摸事件交给Iouch处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return matrixHelper.handlerTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        matrixHelper.onDisallowInterceptEvent(this,event);
        return super.dispatchTouchEvent(event);
    }

    /**
     * 获取大小
     */
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
            resetScaleData();
            computePaddingRect();
            if (isShowChartName) {
                //计算标题的大小
                chartTitle.computeRect(chartRect);
                chartTitle.draw(canvas, chartData.getChartName(), paint);
            }
            if (!chartData.isEmpty()) {
                //计算图例的大小
                legend.computeRect(chartRect);
                //绘制图例
                legend.draw(canvas, chartData, paint);
            }
            if (!isCharEmpty) {
                //绘制内容
                drawContent(canvas);
            } else {
                //绘制空白
                emptyView.computeRect(chartRect);
                emptyView.draw(canvas, paint);
            }
        }
    }
    //待改善
    private void resetScaleData(){
        ScaleData scaleData = chartData.getScaleData();
        scaleData.scaleRect.set(0,0,0,0);
    }

    protected abstract void drawContent(Canvas canvas);






    protected void computeChartRect(Rect rect) {
        chartRect.left = chartRect.left + rect.left;
        chartRect.top = chartRect.top + rect.top;
        chartRect.bottom = chartRect.bottom - rect.bottom;
        chartRect.right = chartRect.right - rect.right;
    }


    /**
     * 计算图表的区域
     *
     * @return
     */
    private void computePaddingRect() {
        chartRect.left = paddingLeft;
        chartRect.top = paddingTop;
        chartRect.bottom = height - paddingBottom;
        chartRect.right = width - paddingRight;
    }


    public void setPadding(int[] padding) {
        this.paddingLeft = padding[0];
        this.paddingTop = padding[1];
        this.paddingRight = padding[2];
        this.paddingBottom = padding[3];

    }

    protected abstract P initProvider();

    /**
     * 设置图内容绘制者
     */
    public void setProvider(P provider) {
        this.provider = provider;
    }

    public P getProvider() {
        return provider;
    }

    public ChartData<C> getChartData() {
        return chartData;
    }



    /**
     * 设置图表数据源
     */
    public void setChartData(ChartData<C> chartData) {
        isCharEmpty = !provider.calculation(chartData);
        this.chartData = chartData;
        invalidate();
    }

    private int duration = 400;
    private Interpolator interpolator;

    /**
     * 动画
     */
    private void startChartAnim() {

        if (interpolator == null)
            startChartAnim(duration);
        else
            startChartAnim(duration, interpolator);
    }

    /**
     * 动画
     * @param duration 时间
     */
    public void startChartAnim(int duration){
        new DecelerateInterpolator();
        provider.startAnim(this,duration,new DecelerateInterpolator());
    }

    public void startChartAnim(int duration, Interpolator interpolator){

        this.duration = duration;
        this.interpolator = interpolator;
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
    public void onClickLegend(C c, Legend<C> legend){
        isCharEmpty =  !provider.calculation(chartData);
        if(!isFirstAnim) {
             startChartAnim();
        }
        if(onClickLegendListener != null){
            onClickLegendListener.onClickLegend(c,legend);
        }
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

    public ILegend<C> getLegend() {
        return legend;
    }

    /**
     * 设置是否可以缩放
     * @param zoom
     */
    public void setZoom(boolean zoom) {
        matrixHelper.setCanZoom(zoom);
    }

    @Override
    public void onTableChanged(float translateX, float translateY) {
        invalidate();
    }

    public MatrixHelper getMatrixHelper() {
        return matrixHelper;
    }

    public void setOnClickColumnListener(OnClickColumnListener<C> onClickColumnListener) {
        provider.setOnClickColumnListener(onClickColumnListener);
    }


    public void setOnClickLegendListener(OnClickLegendListener<C> onClickLegendListener) {
        this.onClickLegendListener = onClickLegendListener;
    }

    /**
     * 是否动画只执行一次
     * @param isFirstAnim
     */
    public void setFirstAnim(boolean isFirstAnim) {
        this.isFirstAnim = isFirstAnim;
    }

    public boolean save(){
      return save(new PicOption(chartData.getChartName()));
    }

    public boolean save(PicOption picOption){
        return new PicGeneration<BaseChart>().save(this,picOption);
    }
}