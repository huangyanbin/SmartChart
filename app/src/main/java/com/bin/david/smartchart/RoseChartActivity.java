package com.bin.david.smartchart;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daivd.chart.core.RoseChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.IFormat;
import com.daivd.chart.data.RoseData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.legend.IChartTitle;
import com.daivd.chart.legend.ILegend;
import com.daivd.chart.provider.component.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoseChartActivity extends AppCompatActivity {

    private RoseChart roseChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rose);

        roseChart = (RoseChart) findViewById(R.id.radarChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("北");
        chartYDataList.add("北北东");
        chartYDataList.add("东北");
        chartYDataList.add("东北东");
        chartYDataList.add("东");
        chartYDataList.add("东南东");
        chartYDataList.add("东南");
        chartYDataList.add("南南东");
        chartYDataList.add("南");
        chartYDataList.add("南南西");
        chartYDataList.add("西南");
        chartYDataList.add("西南西");
        chartYDataList.add("西");
        chartYDataList.add("西北西");
        chartYDataList.add("西北");
        chartYDataList.add("北北西");
        int[] colors = new int[]{res.getColor(R.color.arc1),res.getColor(R.color.arc2),res.getColor(R.color.arc3),res.getColor(R.color.arc21)
        ,res.getColor(R.color.cal_sign_color),res.getColor(R.color.cal_safe_color)};
        List<RoseData> lineDatas = new ArrayList<>();
        for(int j = 0; j<6; j++) {
            ArrayList<Double> dataList = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 16; i++) {
                double num = random.nextInt(20);
                dataList.add(num);
            }
            RoseData columnData1 = new RoseData(j*2+"-"+(j+1)*2+"m/s", "%", colors[j], dataList);
            lineDatas.add(columnData1);
        }
        ChartData<RoseData> chartData2 = new ChartData<>("玫瑰图",chartYDataList,lineDatas);

        //开启MarkView
        roseChart.getProvider().setOpenMark(true);
        //设置MarkView
        roseChart.getProvider().setMarkView(new MsgMarkView(this));
        roseChart.setRotate(true);
        roseChart.getProvider().setShowScale(true);
        //设置显示标题
        roseChart.setShowChartName(true);
        //设置标题方向
        roseChart.getChartTitle().setTitleDirection(IChartTitle.LEFT);
        //设置标题比例
        roseChart.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        FontStyle fontStyle = roseChart.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc23));
        fontStyle.setTextSpSize(this,16);
        roseChart.getLegend().setLegendDirection(ILegend.BOTTOM);
        roseChart.getLegend().getLegendStyle().setShape(PointStyle.SQUARE);
        roseChart.getLegend().setLegendPercent(0.2f);
        roseChart.getProvider().setScaleFormat(new IFormat<Double>() {
            @Override
            public String format(Double d) {
                return d+"%";
            }
        });
        roseChart.setChartData(chartData2);
        roseChart.setFirstAnim(false);
        roseChart.startChartAnim(1000);

    }
}
