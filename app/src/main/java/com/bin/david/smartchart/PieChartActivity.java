package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> groupData = new ArrayList<>();
        groupData.add("华北");
        groupData.add("华中");
        groupData.add("华东");
        groupData.add("华西");


        List<PieData> pieDatas = new ArrayList<>();


        PieData columnData1 = new PieData("华北","℃",getResources().getColor(R.color.arc1),20d);
        PieData columnData2 = new PieData("华中","℃",getResources().getColor(R.color.arc2),15d);
        PieData columnData3 = new PieData("华东","℃",getResources().getColor(R.color.arc3),25d);
        PieData columnData4 = new PieData("华西","℃",getResources().getColor(R.color.arc21),5d);
        pieDatas.add(columnData1);
        pieDatas.add(columnData2);
        pieDatas.add(columnData3);
        pieDatas.add(columnData4);
        ChartData<PieData> chartData = new ChartData<>("饼图",groupData,pieDatas);
        pieChart.setShowChartName(true);
        //设置标题样式
        FontStyle fontStyle = pieChart.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc3));
        fontStyle.setTextSpSize(this,15);
        pieChart.getProvider().setOpenMark(true);
        pieChart.getProvider().setMarkView(new MsgMarkView(this));
        pieChart.getLegend().getLegendStyle().setShape(PointStyle.CIRCLE);
        pieChart.getLegend().setLegendDirection(ILegend.TOP);
        pieChart.setRotate(true);
        pieChart.setChartData(chartData);
        pieChart.startChartAnim(1000);
        pieChart.setOnClickColumnListener(new OnClickColumnListener<PieData>() {
            @Override
            public void onClickColumn(PieData lineData, int position) {
                Toast.makeText(PieChartActivity.this,lineData.getChartYDataList()+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
