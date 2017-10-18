package com.bin.david.smartchart.bean;

/**
 * Created by huang on 2017/10/13.
 */

public enum  ChartStyle {

  LINE_TYPE("选择线种类"),
  LINE_STYLE("选择线样式"),
  LEGEND_POSITION("选择图示位置"),
  LEGEND_TYPE("选择图示样式"),
    LEGEND_CLICK("图示是否点击"),
  LINE_SHOWPOINT("是否显示点"),
    LINE_POINT_STYLE("选择点样式"),
    LINE_TEXT("选择是否显示点值"),
    ZOOM("是否缩放"),
    SHOW_TITLE("是否显示标题"),
    TITLE_POSITON("选择标题位置"),
    CROSS("是否打开十字轴"),
    MARK_VIEW("是否显示MarkView"),
    H_GRID("是否显示横向网格"),
    SHOW_LINE("是否显示线"),
    SHOW_AREA("是否显示面积图"),
    V_GRID("是否显示纵向网格"),
    ROATE_ANGLE("横轴旋转角度");



    public String value;

     ChartStyle(String value){
        this.value = value;
    }


}
