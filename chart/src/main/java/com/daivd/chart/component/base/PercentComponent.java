package com.daivd.chart.component.base;

import android.graphics.Rect;

/**图表百分百组件
 * Created by huang on 2017/10/26.
 */

public abstract class PercentComponent<T> implements IComponent<T> {
    
    private static final float DEFAULT_PERCENT = 0.1f;
    private float percent = DEFAULT_PERCENT;
    private Rect rect = new Rect();
    protected int direction;
    
    @Override
    public void computeRect(Rect chartRect) {
        rect.left = chartRect.left;
        rect.right = chartRect.right;
        rect.top = chartRect.top;
        rect.bottom = chartRect.bottom;
        int h = (int) (chartRect.height()*percent);
        int w = (int) (chartRect.width()*percent);
        switch (direction){
            case TOP:
                rect.bottom = rect.top+h;
                chartRect.top = chartRect.top+h;
                break;
            case LEFT:
                rect.right = rect.left + w;
                chartRect.left = chartRect.left + w;
                break;
            case RIGHT:
                rect.left = rect.right -w;
                chartRect.right = chartRect.right - w;
                break;
            case BOTTOM:
                rect.top = rect.bottom -h;
                chartRect.bottom = chartRect.bottom -h;
                break;
        }
    }


    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
