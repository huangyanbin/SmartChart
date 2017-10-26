package com.daivd.chart.component.base;

import com.daivd.chart.data.style.FontStyle;

/**
 * Created by huang on 2017/10/26.
 */

public interface IChartTitle extends IComponent<String> {

    float getPercent();

    void setPercent(float percent);

    FontStyle getFontStyle();

    int getDirection();

    void setDirection(int direction);

    void setFontStyle(FontStyle fontStyle);
}
