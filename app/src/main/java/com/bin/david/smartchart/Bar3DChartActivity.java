package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.core.Bar3DChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LevelLine;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class Bar3DChartActivity extends AppCompatActivity {

    private Bar3DChart columnChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3dbar);
        columnChartView = (Bar3DChart) findViewById(R.id.columnChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> groupData = new ArrayList<>();
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");


        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);


        LineData columnData1 = new LineData("温度","℃",getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);

        LineData columnData2 = new LineData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData = new ChartData<>("柱状图",groupData,ColumnDatas);
        columnChartView.setChartData(chartData);
        columnChartView.startChartAnim(1000);
        columnChartView.setZoom(true);
        columnChartView.setShowChartName(true);
        columnChartView.getProvider().setOpenMark(true);
        columnChartView.getProvider().setOpenCross(true);
        LevelLine levelLine = new LevelLine(true,20);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc22)).setEffect(effects);
        columnChartView.getProvider().setLevelLine(levelLine);
        columnChartView.getProvider().setMarkView(new MsgMarkView(this));
        columnChartView.getLegend().getLegendStyle().setShape(PointStyle.CIRCLE);
        BaseAxis vaxis = columnChartView.getLeftVerticalAxis();
        vaxis.setDrawGrid(true);
        vaxis.getGridStyle().setColor(R.color.arc_inteval);
        columnChartView.getLegend().setLegendDirection(ILegend.TOP);


    }
}
