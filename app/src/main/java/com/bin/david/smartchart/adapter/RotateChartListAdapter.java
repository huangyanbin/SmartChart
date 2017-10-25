package com.bin.david.smartchart.adapter;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.bin.david.smartchart.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daivd.chart.core.PieChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang on 2017/10/13.
 */

public class RotateChartListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    

    public RotateChartListAdapter(@Nullable List<String> data) {
        super(R.layout.item_rotate_chart, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
      PieChart pieChart =  helper.getView(R.id.pieChart);
      Resources res = mContext.getResources();
      FontStyle.setDefaultTextSpSize(mContext,12);
      List<String> chartYDataList = new ArrayList<>();
      chartYDataList.add("Tokyo");
      chartYDataList.add("Paris");
      chartYDataList.add("Hong Kong");
      chartYDataList.add("Singapore");


      List<PieData> pieDatas = new ArrayList<>();


      PieData columnData1 = new PieData("Tokyo","℃",res.getColor(R.color.arc1),20d);
      PieData columnData2 = new PieData("Paris","℃",res.getColor(R.color.arc2),15d);
      PieData columnData3 = new PieData("Hong Kong","℃",res.getColor(R.color.arc3),25d);
      PieData columnData4 = new PieData("Singapore","℃",res.getColor(R.color.arc21),5d);
      pieDatas.add(columnData1);
      pieDatas.add(columnData2);
      pieDatas.add(columnData3);
      pieDatas.add(columnData4);
      ChartData<PieData> chartData = new ChartData<>("pie chart",chartYDataList,pieDatas);
      pieChart.setShowChartName(true);
      //设置标题样式
      FontStyle fontStyle = pieChart.getChartTitle().getTextStyle();
      fontStyle.setTextColor(res.getColor(R.color.arc3));
      fontStyle.setTextSpSize(mContext,15);
      pieChart.getProvider().setOpenMark(true);
      pieChart.getProvider().setMarkView(new BubbleMarkView(mContext));
      Point legendPoint = (Point) pieChart.getLegend().getPoint();
      PointStyle style = legendPoint.getPointStyle();
      style.setShape(PointStyle.CIRCLE);
      pieChart.getLegend().setLegendDirection(ILegend.TOP);
      pieChart.setRotate(true);
      pieChart.setChartData(chartData);
      pieChart.startChartAnim(1000);
    }
}
