package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.core.RadarChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LevelLine;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class RadarChartActivity extends AppCompatActivity {

    private RadarChart radarChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        radarChart = (RadarChart) findViewById(R.id.radarChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(26d);
        tempList1.add(35d);
        LineData columnData1 = new LineData("温度","℃",AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("雷达图",chartYDataList,ColumnDatas);

        //开启MarkView
        radarChart.getProvider().setOpenMark(true);
        //设置MarkView
        radarChart.getProvider().setMarkView(new MsgMarkView(this));
        radarChart.setRotate(true);

        //设置显示标题
        radarChart.setShowChartName(true);
        //设置标题方向
        radarChart.getChartTitle().setTitleDirection(IChartTitle.TOP);
        //设置标题比例
        radarChart.getChartTitle().setTitlePercent(0.1f);
        //设置标题样式
        radarChart.getChartTitle().getTextStyle().setTextColor(res.getColor(R.color.arc23));
        radarChart.getLegend().setLegendDirection(ILegend.BOTTOM);
        radarChart.getLegend().getLegendStyle().setShape(PointStyle.SQUARE);
        radarChart.getLegend().setLegendPercent(0.2f);
        radarChart.setChartData(chartData2);
        radarChart.startChartAnim(1000);

    }
}
