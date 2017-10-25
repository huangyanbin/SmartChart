package com.bin.david.smartchart;

import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.smartchart.bean.ChartStyle;
import com.bin.david.smartchart.view.BaseCheckDialog;
import com.bin.david.smartchart.view.QuickChartDialog;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.axis.IAxis;
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
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.List;

public class AreaChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private BaseCheckDialog<ChartStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        quickChartDialog = new QuickChartDialog();
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("Tokyo");
        chartYDataList.add("Paris");
        chartYDataList.add("Hong Kong");
        chartYDataList.add("Singapore");
        chartYDataList.add("Sydney");
        chartYDataList.add("Milano");
        chartYDataList.add("Shanghai");
        chartYDataList.add("Beijing");
        chartYDataList.add("Madrid");
        chartYDataList.add("Moscow");
        chartYDataList.add("Seoul");
        chartYDataList.add("Bangkok");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(35d);
        tempList1.add(40d);
        tempList1.add(10d);
        tempList1.add(26d);
        tempList1.add(-15d);
        tempList1.add(40d);
        tempList1.add(-10d);
        tempList1.add(26d);
        tempList1.add(-35d);
        tempList1.add(40d);
        tempList1.add(10d);
        LineData columnData1 = new LineData("Temperature","℃", IAxis.AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("Humidity","RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("Area Chart",chartYDataList,ColumnDatas);

        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis =  lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  lineChart.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        DoubleDriCross cross = new DoubleDriCross();
        LineStyle crossStyle = cross.getCrossStyle();
        crossStyle.setWidth(this,1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        lineChart.getProvider().setCross(cross);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(false);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(this));

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题方向
        lineChart.getChartTitle().setTitleDirection(IChartTitle.TOP);
        //设置标题比例
        lineChart.getChartTitle().setTitlePercent(0.2f);
        //设置标题样式
        //设置标题样式
        FontStyle fontStyle = lineChart.getChartTitle().getTextStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this,15);
        LevelLine levelLine = new LevelLine(30);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        lineChart.getLegend().setLegendDirection(ILegend.BOTTOM);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.CIRCLE);
        lineChart.getLegend().setLegendPercent(0.2f);
        lineChart.getProvider().setArea(true);
        lineChart.setChartData(chartData2);
        lineChart.setOnClickColumnListener(new OnClickColumnListener<LineData>() {
            @Override
            public void onClickColumn(LineData lineData, int position) {
               // Toast.makeText(AreaChartActivity.this,lineData.getChartYDataList().get(position)+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View view){
        changedStyle();
    }

    private void changedStyle() {

        if(chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("Chart Setting", new BaseCheckDialog.OnCheckChangeListener<ChartStyle>() {
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

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getProvider().setDrawLine(true);
                }else if(position ==1){
                    lineChart.getProvider().setDrawLine(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showArea(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getProvider().setArea(true);
                }else if(position ==1){
                    lineChart.getProvider().setArea(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }
    private void lengedClick(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"Yes","No"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getLegend().setSelectColumn(true);
                }else if(position ==1){
                    lineChart.getLegend().setSelectColumn(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }
    private void showHGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getLeftVerticalAxis().setDrawGrid(true);
                }else if(position ==1){
                    lineChart.getLeftVerticalAxis().setDrawGrid(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showVGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getHorizontalAxis().setDrawGrid(true);
                }else if(position ==1){
                    lineChart.getHorizontalAxis().setDrawGrid(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void cross(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"YES","No"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getProvider().setOpenCross(true);
                }else if(position ==1){
                    lineChart.getProvider().setOpenCross(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void mark(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"Yes","No"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getProvider().setOpenMark(true);
                }else if(position ==1){
                    lineChart.getProvider().setOpenMark(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }
    private void zoom(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"Yes","NO"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.setZoom(true);
                }else if(position ==1){
                    lineChart.setZoom(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showValue(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getProvider().setShowText(true);
                }else if(position ==1){
                    lineChart.getProvider().setShowText(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showTitle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.setShowChartName(true);
                }else if(position ==1){
                    lineChart.setShowChartName(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showPoint(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    Point point = new Point();
                    point.getPointStyle().setShape(PointStyle.CIRCLE);
                    //设置显示点的样式
                    lineChart.getProvider().setPoint(point);
                }else if(position ==1){
                    lineChart.getProvider().setPoint(null);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showPointStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"SQUARE","CIRCLE","RECT"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {

                Point point = new Point();
                PointStyle style =point.getPointStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                lineChart.getProvider().setPoint(point);
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showLegendStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"SQUARE","CIRCLE","RECT"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                Point legendPoint = (Point) lineChart.getLegend().getPoint();
                PointStyle style = legendPoint.getPointStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showTitlePos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"top","bottom","left","right"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getChartTitle().setTitleDirection(IChartTitle.TOP);
                }else if(position ==1){
                    lineChart.getChartTitle().setTitleDirection(IChartTitle.BOTTOM);
                } else if(position ==2){
                    lineChart.getChartTitle().setTitleDirection(IChartTitle.LEFT);
                }
                else {
                    lineChart.getChartTitle().setTitleDirection(IChartTitle.RIGHT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showLegendPos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"top","bottom","left","right"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.getLegend().setLegendDirection(ILegend.TOP);
                }else if(position ==1){
                    lineChart.getLegend().setLegendDirection(ILegend.BOTTOM);
                } else if(position ==2){
                    lineChart.getLegend().setLegendDirection(ILegend.LEFT);
                }
                else {
                    lineChart.getLegend().setLegendDirection(ILegend.RIGHT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showLineTypeSelectDialog(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"CURVE","LINE"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    lineChart.setLineModel(LineChart.CURVE_MODEL);
                }else{
                    lineChart.setLineModel(LineChart.LINE_MODEL);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showLineStyleSelectDialog(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"solid line","dotted line"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                LineStyle l  = lineChart.getProvider().getLineStyle();
                if(position == 0){
                    l.setEffect(new PathEffect());
                }else{
                    DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
                    l.setEffect(effects);
                }
                lineChart.startChartAnim(400);
            }
        });
    }
}
