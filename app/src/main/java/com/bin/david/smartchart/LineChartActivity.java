package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.core.LineChartView;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.LevelLine;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChartView lineChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        lineChartView = (LineChartView) findViewById(R.id.lineChart);
        lineChartView.setLineModel(LineChartView.CURVE_MODEL);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> groupData = new ArrayList<>();
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");
        groupData.add("华南");
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");
        groupData.add("华南");
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");
        groupData.add("华南");
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");
        groupData.add("华南");
        groupData.add("华北");
        groupData.add("华中");

        List<ColumnData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(30d);
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(30d);
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(30d);
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(7d);
        tempList1.add(100d);
        tempList1.add(26d);
        tempList1.add(35d);

        ColumnData columnData1 = new ColumnData("温度","℃",AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(34.6d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(34.6d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(34.6d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(34.6d);
        humidityList.add(60d);
        humidityList.add(50d);

        ColumnData columnData2 = new ColumnData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);


        ChartData chartData2 = new ChartData("线型图",groupData,ColumnDatas);

        lineChartView.setLineModel(LineChartView.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChartView.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChartView.getHorizontalAxis();
        verticalAxis.setAxisDirection(AxisDirection.LEFT);
        verticalAxis.setDrawGrid(true);
        horizontalAxis.setAxisDirection(AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        verticalAxis.getLineStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        LineStyle crossStyle = lineChartView.getProvider().getCrossStyle();
        crossStyle.setWidth(this,1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        lineChartView.setZoom(true);
        lineChartView.getProvider().setOpenCross(true);
        lineChartView.getProvider().setOpenMark(true);
        lineChartView.getProvider().setShowPoint(true);
        lineChartView.getProvider().getPointStyle().setShape(PointStyle.CIRCLE);
        lineChartView.getProvider().setMarkView(new MsgMarkView(this));
        lineChartView.setChartData(chartData2);
        lineChartView.setShowChartName(true);
        lineChartView.getProvider().setLevelLine(new LevelLine(true,20));
        lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);
        lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        lineChartView.startChartAnim(1000);

    }
}
