package com.daivd.chart.component.base;

import android.graphics.PointF;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.provider.component.point.ILegendPoint;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.listener.OnClickLegendListener;

/**
 * Created by huang on 2017/10/26.
 */

public interface ILegend<C extends ColumnData> extends IComponent<ChartData<C>> {

    float getPercent();

    void setPercent(float percent);

    FontStyle getFontStyle();

    int getDirection();

    void setDirection(int direction);

    void setFontStyle(FontStyle fontStyle);

    void onClickLegend(PointF pointF);

    void setOnClickLegendListener(OnClickLegendListener<C> onClickLegendListener);


    int getPadding();

    void setPadding(int padding);

    IPoint getPoint();

    void setPoint(ILegendPoint point);

    void setSelectColumn(boolean selectColumn);

    void setDisplay(boolean isDisplay);
}