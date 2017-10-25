package com.bin.david.smartchart.bean;

/**
 * Created by huang on 2017/10/13.
 */

public enum  ChartStyle {

  LINE_TYPE("Line type"),
  LINE_STYLE("Line style"),
  LEGEND_POSITION("Legend position"),
  LEGEND_TYPE("Legend type"),
    LEGEND_CLICK("Legend click"),
  LINE_SHOWPOINT("Show Point"),
    LINE_POINT_STYLE("Point style"),
    LINE_TEXT("Show point value"),
    ZOOM("Zoom"),
    SHOW_TITLE("Show title"),
    TITLE_POSITON("Show title position"),
    CROSS("Open cross"),
    MARK_VIEW("Show mark view"),
    H_GRID("Show horizontal grid"),
    SHOW_LINE("Show line"),
    SHOW_AREA("Show area"),
    V_GRID("Show vertical grid"),
    ROATE_ANGLE("Horizontal axis value angle");



    public String value;

     ChartStyle(String value){
        this.value = value;
    }


}
