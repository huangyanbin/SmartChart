package com.bin.david.smartchart.bean;

/**
 * Created by huang on 2017/10/13.
 */

public class SelectItem {
    private ChartStyle style;
    private int position;

    public SelectItem(ChartStyle style) {
        this.style = style;
    }

    public ChartStyle getStyle() {
        return style;
    }

    public void setStyle(ChartStyle style) {
        this.style = style;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
