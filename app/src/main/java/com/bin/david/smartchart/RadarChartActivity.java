package com.bin.david.smartchart;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.RadarChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.RadarData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.group.mark.BubbleMarkView;
import com.daivd.chart.group.point.Point;

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
        chartYDataList.add("Tokyo");
        chartYDataList.add("Paris");
        chartYDataList.add("Hong Kong");
        chartYDataList.add("Singapore");
        chartYDataList.add("Sydney");
        chartYDataList.add("Milano");
        List<RadarData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(26d);
        tempList1.add(35d);
        RadarData columnData1 = new RadarData("Temperature","℃",getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(30d);
        humidityList.add(65d);
        RadarData columnData2 = new RadarData("Humidity","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<RadarData> chartData2 = new ChartData<>("Radar chart",chartYDataList,ColumnDatas);

        //开启MarkView
        radarChart.getProvider().setOpenMark(true);
        //设置MarkView
        radarChart.getProvider().setMarkView(new BubbleMarkView(this));
        radarChart.setRotate(true);

        //设置显示标题
        radarChart.setShowChartName(true);
        //设置标题方向
        radarChart.getChartTitle().setDirection(IComponent.TOP);
        //设置标题比例
        radarChart.getChartTitle().setPercent(0.1f);
        //设置标题样式
        radarChart.getChartTitle().getFontStyle().setTextColor(res.getColor(R.color.arc23));
        radarChart.getLegend().setDirection(IComponent.BOTTOM);
        Point point = (Point)radarChart.getLegend().getPoint();
        point.getPointStyle().setShape(PointStyle.SQUARE);
        radarChart.getLegend().setPercent(0.2f);
        radarChart.setChartData(chartData2);
        radarChart.startChartAnim(1000);

    }
}
