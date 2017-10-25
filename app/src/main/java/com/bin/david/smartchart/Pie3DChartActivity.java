package com.bin.david.smartchart;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.daivd.chart.core.Pie3DChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.List;

public class Pie3DChartActivity extends AppCompatActivity {

    private Pie3DChart pie3DChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie3d);
        pie3DChart = (Pie3DChart) findViewById(R.id.pieChart);
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
        pie3DChart.setShowChartName(true);
        //设置标题样式
        FontStyle fontStyle = pie3DChart.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc3));
        fontStyle.setTextSpSize(this,15);
        pie3DChart.getProvider().setOpenMark(true);
        pie3DChart.getProvider().setMarkView(new BubbleMarkView(this));
        Point legendPoint = (Point) pie3DChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.CIRCLE);
        pie3DChart.getLegend().setLegendDirection(ILegend.TOP);
        pie3DChart.setRotate(true);
        pie3DChart.setChartData(chartData);
        pie3DChart.startChartAnim(1000);
        pie3DChart.setOnClickColumnListener(new OnClickColumnListener<PieData>() {
            @Override
            public void onClickColumn(PieData lineData, int position) {
                Toast.makeText(Pie3DChartActivity.this,lineData.getChartYDataList()+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
