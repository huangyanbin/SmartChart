package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LevelLine;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class AreaChartActivity extends AppCompatActivity {

    private LineChart lineChartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        lineChartView = (LineChart) findViewById(R.id.lineChart);
        lineChartView.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        LineData columnData1 = new LineData("温度","℃",AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("面积图",chartYDataList,ColumnDatas);

        lineChartView.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChartView.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChartView.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getLineStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        LineStyle crossStyle = lineChartView.getProvider().getCrossStyle();
        crossStyle.setWidth(this,1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        lineChartView.setZoom(true);
        //开启十字架
        lineChartView.getProvider().setOpenCross(false);
        //开启MarkView
        lineChartView.getProvider().setOpenMark(true);
        //设置MarkView
        lineChartView.getProvider().setMarkView(new MsgMarkView(this));
        //设置显示点
        lineChartView.getProvider().setShowPoint(false);
        //设置显示点的样式
        lineChartView.getProvider().getPointStyle().setShape(PointStyle.CIRCLE);

        //设置显示标题
        lineChartView.setShowChartName(true);
        //设置标题方向
        lineChartView.getChartTitle().setTitleDirection(IChartTitle.TOP);
        //设置标题比例
        lineChartView.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        lineChartView.getChartTitle().getTextStyle().setTextColor(res.getColor(R.color.arc21));
        lineChartView.getProvider().setLevelLine(new LevelLine(true,20));
        lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);
        lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        lineChartView.getLegend().setLegendPercent(0.2f);
        lineChartView.getProvider().setArea(true);
        lineChartView.setChartData(chartData2);
        lineChartView.setOnClickColumnListener(new OnClickColumnListener<LineData>() {
            @Override
            public void onClickColumn(LineData lineData, int position) {
                Toast.makeText(AreaChartActivity.this,lineData.getChartYDataList().get(position)+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
