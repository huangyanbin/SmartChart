package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.smartchart.bean.ChartStyle;
import com.bin.david.smartchart.view.BaseCheckDialog;
import com.bin.david.smartchart.view.QuickChartDialog;
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
import com.daivd.chart.mark.MsgMarkView;

import java.util.ArrayList;
import java.util.List;

public class ScatterChartActivity extends AppCompatActivity {

    private LineChart lineChartView;
    private BaseCheckDialog<ChartStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        quickChartDialog = new QuickChartDialog();
        lineChartView = (LineChart) findViewById(R.id.lineChart);
        lineChartView.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        chartYDataList.add("华北");
        chartYDataList.add("华中");
        chartYDataList.add("华东");
        chartYDataList.add("华西");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(-20d);
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(-40d);
        tempList1.add(10d);
        tempList1.add(26d);
        tempList1.add(-15d);
        tempList1.add(40d);
        tempList1.add(10d);
        LineData columnData1 = new LineData("温度","℃",AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(60d);
        humidityList.add(100d);
        humidityList.add(80d);
        humidityList.add(65d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("湿度","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("散点图",chartYDataList,ColumnDatas);

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
        lineChartView.getProvider().setOpenCross(true);
        //开启MarkView
        lineChartView.getProvider().setOpenMark(true);
        //设置MarkView
        lineChartView.getProvider().setMarkView(new MsgMarkView(this));
        //设置显示点
        lineChartView.getProvider().setShowPoint(true);
        //设置显示点的样式
        lineChartView.getProvider().getPointStyle().setShape(PointStyle.CIRCLE);

        //设置显示标题
        lineChartView.setShowChartName(true);
        //设置标题方向
        lineChartView.getChartTitle().setTitleDirection(IChartTitle.BOTTOM);
        //设置标题比例
        lineChartView.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        //设置标题样式
        FontStyle fontStyle = lineChartView.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this,15);
        LevelLine levelLine = new LevelLine(true,20);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChartView.getProvider().setLevelLine(levelLine);
        lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);
        lineChartView.getLegend().getLegendStyle().setShape(PointStyle.RECT);
        lineChartView.getLegend().setLegendPercent(0.2f);
        lineChartView.getProvider().setDrawLine(false);
        lineChartView.setChartData(chartData2);
        lineChartView.startChartAnim(1000);

    }

    public void onClick(View view){
        changedStyle();
    }

    private void changedStyle() {

        if(chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("Chart配置", new BaseCheckDialog.OnCheckChangeListener<ChartStyle>() {
                @Override
                public String getItemText(ChartStyle chartStyle) {
                    return chartStyle.value;
                }

                @Override
                public void onItemClick(ChartStyle item, int position) {
                    switch (item) {
                        case LINE_TYPE:
                            showLineTypeSelectDialog(item);
                            break;
                        case LINE_STYLE:
                            showLineStyleSelectDialog(item);
                            break;
                        case LEGEND_POSITION:
                            showLegendPos(item);
                            break;
                        case LEGEND_TYPE:
                            showLegendStyle(item);
                            break;
                        case LINE_SHOWPOINT:
                            showPoint(item);
                            break;
                        case LINE_POINT_STYLE:
                            showPointStyle(item);
                            break;
                        case LINE_TEXT:
                            showValue(item);
                            break;
                        case TITLE_POSITON:
                            showTitlePos(item);
                            break;
                        case ZOOM:
                            zoom(item);
                            break;
                        case SHOW_TITLE:
                            showTitle(item);
                            break;
                        case LEGEND_CLICK:
                            lengedClick(item);
                            break;
                        case CROSS:
                            cross(item);
                            break;
                        case MARK_VIEW:
                            mark(item);
                            break;
                        case H_GRID:
                            showHGrid(item);
                            break;
                        case V_GRID:
                            showVGrid(item);
                            break;
                        case SHOW_LINE:
                            showLine(item);
                            break;
                        case SHOW_AREA:
                            showArea(item);
                            break;
                    }
                }
            });
        }
        ArrayList<ChartStyle> items = new ArrayList<>();
        items.add(ChartStyle.SHOW_TITLE);
        items.add(ChartStyle.ZOOM);
        items.add(ChartStyle.TITLE_POSITON);
        items.add(ChartStyle.LEGEND_POSITION);
        items.add(ChartStyle.LEGEND_TYPE);
        items.add(ChartStyle.LEGEND_CLICK);
        items.add(ChartStyle.LINE_TYPE);
        items.add(ChartStyle.LINE_STYLE);
        items.add(ChartStyle.LINE_SHOWPOINT);
        items.add(ChartStyle.LINE_POINT_STYLE);
        items.add(ChartStyle.LINE_TEXT);
        items.add(ChartStyle.CROSS);
        items.add(ChartStyle.MARK_VIEW);
        items.add(ChartStyle.H_GRID);
        items.add(ChartStyle.V_GRID);
        items.add(ChartStyle.SHOW_LINE);
        items.add(ChartStyle.SHOW_AREA);
        chartDialog.show(this,true,items);
    }

    private void showLine(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setDrawLine(true);
                }else if(position ==1){
                    lineChartView.getProvider().setDrawLine(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showArea(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setArea(true);
                }else if(position ==1){
                    lineChartView.getProvider().setArea(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }
    private void lengedClick(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"是","否"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getLegend().setSelectColumn(true);
                }else if(position ==1){
                    lineChartView.getLegend().setSelectColumn(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }
    private void showHGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getLeftVerticalAxis().setDrawGrid(true);
                }else if(position ==1){
                    lineChartView.getLeftVerticalAxis().setDrawGrid(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }


    private void showVGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getHorizontalAxis().setDrawGrid(true);
                }else if(position ==1){
                    lineChartView.getHorizontalAxis().setDrawGrid(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void cross(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"是","否"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setOpenCross(true);
                }else if(position ==1){
                    lineChartView.getProvider().setOpenCross(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void mark(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"是","否"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setOpenMark(true);
                }else if(position ==1){
                    lineChartView.getProvider().setOpenMark(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }
    private void zoom(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"是","否"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.setZoom(true);
                }else if(position ==1){
                    lineChartView.setZoom(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showValue(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setShowText(true);
                }else if(position ==1){
                    lineChartView.getProvider().setShowText(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showTitle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.setShowChartName(true);
                }else if(position ==1){
                    lineChartView.setShowChartName(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showPoint(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getProvider().setShowPoint(true);
                }else if(position ==1){
                    lineChartView.getProvider().setShowPoint(false);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showPointStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"正方形","圆形","长方形"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                PointStyle style = lineChartView.getProvider().getPointStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showLegendStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"正方形","圆形","长方形"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                PointStyle style = lineChartView.getLegend().getLegendStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }


    private void showTitlePos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"上","下","左","右"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getChartTitle().setTitleDirection(IChartTitle.TOP);
                }else if(position ==1){
                    lineChartView.getChartTitle().setTitleDirection(IChartTitle.BOTTOM);
                } else if(position ==2){
                    lineChartView.getChartTitle().setTitleDirection(IChartTitle.LEFT);
                }
                else {
                    lineChartView.getChartTitle().setTitleDirection(IChartTitle.RIGHT);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }


    private void showLegendPos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"上","下","左","右"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.getLegend().setLegendDirection(ILegend.TOP);
                }else if(position ==1){
                    lineChartView.getLegend().setLegendDirection(ILegend.BOTTOM);
                } else if(position ==2){
                    lineChartView.getLegend().setLegendDirection(ILegend.LEFT);
                }
                else {
                    lineChartView.getLegend().setLegendDirection(ILegend.RIGHT);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }


    private void showLineTypeSelectDialog(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"曲线","折线"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChartView.setLineModel(LineChart.CURVE_MODEL);
                }else{
                    lineChartView.setLineModel(LineChart.LINE_MODEL);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }

    private void showLineStyleSelectDialog(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"实线","虚线"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                LineStyle l  = lineChartView.getProvider().getLineStyle();
                if(position == 0){
                    l.setEffect(new PathEffect());
                }else{
                    DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
                    l.setEffect(effects);
                }
                lineChartView.startChartAnim(400);
            }
        });
    }
}
