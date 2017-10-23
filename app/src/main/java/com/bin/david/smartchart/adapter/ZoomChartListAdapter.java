package com.bin.david.smartchart.adapter;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.support.annotation.Nullable;

import com.bin.david.smartchart.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daivd.chart.axis.HorizontalAxis;
import com.daivd.chart.axis.IAxis;
import com.daivd.chart.axis.VerticalAxis;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.provider.component.cross.DoubleDriCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2017/10/13.
 */

public class ZoomChartListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    

    public ZoomChartListAdapter(@Nullable List<String> data) {
        super(R.layout.item_zoom_chart, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       LineChart lineChart =  helper.getView(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = mContext.getResources();
        FontStyle.setDefaultTextSpSize(mContext,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(-35d);
        tempList1.add(-40d);
        tempList1.add(10d);
        LineData columnData1 = new LineData("温度","℃", IAxis.AxisDirection.RIGHT,res.getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("湿度","RH%",res.getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("线型图",chartYDataList,ColumnDatas);

        lineChart.setLineModel(LineChart.CURVE_MODEL);
        VerticalAxis verticalAxis =  lineChart.getLeftVerticalAxis();
        HorizontalAxis horizontalAxis=  lineChart.getHorizontalAxis();
        VerticalAxis rightAxis = lineChart.getRightVerticalAxis();
        rightAxis.setStartZero(false);
        rightAxis.setMaxValue(200);
        rightAxis.setMinValue(-50);
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(mContext,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(mContext,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(mContext,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
      DoubleDriCross cross = new DoubleDriCross();
      LineStyle crossStyle = cross.getCrossStyle();
      crossStyle.setWidth(mContext,1);
      crossStyle.setColor(res.getColor(R.color.arc21));
      lineChart.getProvider().setCross(cross);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(mContext));
        //设置显示点
        Point point = new Point();
        point.getPointStyle().setShape(PointStyle.CIRCLE);
        //设置显示点的样式
        lineChart.getProvider().setPoint(point);

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题方向
        lineChart.getChartTitle().setTitleDirection(IChartTitle.TOP);
        //设置标题比例
        lineChart.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        FontStyle fontStyle = lineChart.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(mContext,15);
        LevelLine levelLine = new LevelLine(20);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(mContext,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        lineChart.getLegend().setLegendDirection(ILegend.BOTTOM);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.RECT);
        lineChart.getLegend().setLegendPercent(0.2f);
        lineChart.getHorizontalAxis().setRotateAngle(-45);
        lineChart.setFirstAnim(false);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(1000);
    }
}
