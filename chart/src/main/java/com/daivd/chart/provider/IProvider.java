package com.daivd.chart.provider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Interpolator;

import com.daivd.chart.core.base.BaseChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.IMark;
import com.daivd.chart.matrix.MatrixHelper;


/**
 * Created by huang on 2017/9/26.
 */

public interface IProvider<C extends ColumnData> {

     boolean calculation( ChartData<C> chartData);

     void drawProvider(Canvas canvas, Rect rect, MatrixHelper helper, Paint paint);

     void clickPoint(PointF point);
     void startAnim(BaseChart chartView, int duration, Interpolator interpolator);

     void setMarkView(IMark markView);

     void setOnClickColumnListener(OnClickColumnListener<C> onClickColumnListener);

}
