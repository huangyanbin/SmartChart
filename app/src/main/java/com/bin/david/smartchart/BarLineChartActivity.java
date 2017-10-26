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
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.axis.VerticalAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.BarLineChart;
import com.daivd.chart.data.BarLineData;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.group.cross.DoubleDriCross;
import com.daivd.chart.group.level.LevelLine;
import com.daivd.chart.group.mark.BubbleMarkView;
import com.daivd.chart.group.point.Point;

import java.util.ArrayList;
import java.util.List;

public class BarLineChartActivity extends AppCompatActivity {

    private BarLineChart barLineChart;
    private BaseCheckDialog<ChartStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_line);
        quickChartDialog = new QuickChartDialog();
        barLineChart = (BarLineChart) findViewById(R.id.barLineChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this,12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("Tokyo");
        chartYDataList.add("Paris");
        chartYDataList.add("Hong Kong");
        chartYDataList.add("Singapore");
        List<BarLineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(-35d);
        tempList1.add(-40d);
        tempList1.add(10d);
        BarLineData columnData1 = new BarLineData("Temperature",BarLineData.BAR,"℃", IAxis.AxisDirection.RIGHT,getResources().getColor(R.color.arc3),tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        BarLineData columnData2 = new BarLineData("Humidity",BarLineData.CURVE,"RH%",getResources().getColor(R.color.arc2),humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<BarLineData> chartData2 = new ChartData<>("BarLineChart",chartYDataList,ColumnDatas);

        BaseAxis verticalAxis =  barLineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis=  barLineChart.getHorizontalAxis();
        VerticalAxis rightAxis = barLineChart.getRightVerticalAxis();
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
        verticalAxis.getAxisStyle().setWidth(this,1);
        DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this,1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        DoubleDriCross cross = new DoubleDriCross();
        LineStyle crossStyle = cross.getCrossStyle();
        crossStyle.setWidth(this,1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        barLineChart.getProvider().setCross(cross);
        barLineChart.setZoom(true);
        //开启十字架
        barLineChart.getProvider().setOpenCross(true);
        //开启MarkView
        barLineChart.getProvider().setOpenMark(true);
        //设置MarkView
        barLineChart.getProvider().setMarkView(new BubbleMarkView(this));


        //设置显示标题
        barLineChart.setShowChartName(true);
        //设置标题方向
        barLineChart.getChartTitle().setDirection(IComponent.TOP);
        //设置标题比例
        barLineChart.getChartTitle().setPercent(0.2f);
        //设置标题样式
        FontStyle fontStyle = barLineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this,15);

        LevelLine levelLine = new LevelLine(20);
        DashPathEffect effects2 = new DashPathEffect(new float[] { 1, 2,2,4}, 1);
        levelLine.getLineStyle().setWidth(this,1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        barLineChart.getProvider().addLevelLine(levelLine);
        barLineChart.getLegend().setDirection(IComponent.BOTTOM);
        Point legendPoint = (Point)barLineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.RECT);
        barLineChart.getLegend().setPercent(0.2f);
        barLineChart.getHorizontalAxis().setRotateAngle(-45);
        barLineChart.setFirstAnim(false);
        barLineChart.setChartData(chartData2);
        barLineChart.startChartAnim(1000);



    }

    public void onClick(View view){
        changedStyle();
    }

    private void changedStyle() {

        if(chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("ChartSetting", new BaseCheckDialog.OnCheckChangeListener<ChartStyle>() {
                @Override
                public String getItemText(ChartStyle chartStyle) {
                    return chartStyle.value;
                }

                @Override
                public void onItemClick(ChartStyle item, int position) {
                    switch (item) {
                        case LINE_TYPE:
                            //showLineTypeSelectDialog(item);
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
                      /*  case LINE_SHOWPOINT:
                            showPoint(item);
                            break;
                        case LINE_POINT_STYLE:
                            showPointStyle(item);
                            break;*/
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
                      /*  case SHOW_LINE:
                            showLine(item);
                            break;
                        case SHOW_AREA:
                            showArea(item);
                            break;*/
                        case ROATE_ANGLE:
                            rotateAngle(item);
                            break;
                    }
                }
            });
        }
        ArrayList<ChartStyle> items = new ArrayList<>();
        items.add(ChartStyle.ROATE_ANGLE);
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

   /* private void showLine(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setDrawLine(true);
                }else if(position ==1){
                    barLineChart.getProvider().setDrawLine(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }*/

   /* private void showArea(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setArea(true);
                }else if(position ==1){
                    barLineChart.getProvider().setArea(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }*/
    private void lengedClick(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"yes","no"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getLegend().setSelectColumn(true);
                }else if(position ==1){
                    barLineChart.getLegend().setSelectColumn(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }
    private void showHGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getLeftVerticalAxis().setDrawGrid(true);
                }else if(position ==1){
                    barLineChart.getLeftVerticalAxis().setDrawGrid(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }


    private void showVGrid(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getHorizontalAxis().setDrawGrid(true);
                }else if(position ==1){
                    barLineChart.getHorizontalAxis().setDrawGrid(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }

    private void cross(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"yes","no"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setOpenCross(true);
                }else if(position ==1){
                     barLineChart.getProvider().setOpenCross(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }

    private void mark(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"yes","no"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setOpenMark(true);
                }else if(position ==1){
                    barLineChart.getProvider().setOpenMark(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }
    private void zoom(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"yes","no"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.setZoom(true);
                }else if(position ==1){
                    barLineChart.setZoom(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }

    private void showValue(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setShowText(true);
                }else if(position ==1){
                    barLineChart.getProvider().setShowText(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }

    private void showTitle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"show","hide"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.setShowChartName(true);
                }else if(position ==1){
                    barLineChart.setShowChartName(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }

  /*  private void showPoint(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"显示","隐藏"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getProvider().setShowPoint(true);
                }else if(position ==1){
                    barLineChart.getProvider().setShowPoint(false);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }*/

   /* private void showPointStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"正方形","圆形","长方形"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                PointStyle style = barLineChart.getProvider().getPointStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }*/

    private void showLegendStyle(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"SQUARE","CIRCLE","RECT"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                Point legendPoint = (Point)barLineChart.getLegend().getPoint();
                PointStyle style = legendPoint.getPointStyle();
                if(position == 0){
                    style.setShape(PointStyle.SQUARE);
                }else if(position ==1){
                    style.setShape(PointStyle.CIRCLE);
                } else if(position ==2){
                    style.setShape(PointStyle.RECT);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }


    private void showTitlePos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"top","bottom","left","right"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getChartTitle().setDirection(IComponent.TOP);
                }else if(position ==1){
                    barLineChart.getChartTitle().setDirection(IComponent.BOTTOM);
                } else if(position ==2){
                    barLineChart.getChartTitle().setDirection(IComponent.LEFT);
                }
                else {
                    barLineChart.getChartTitle().setDirection(IComponent.RIGHT);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }


    private void showLegendPos(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"top","bottom","left","right"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.getLegend().setDirection(IComponent.TOP);
                }else if(position ==1){
                    barLineChart.getLegend().setDirection(IComponent.BOTTOM);
                } else if(position ==2){
                    barLineChart.getLegend().setDirection(IComponent.LEFT);
                }
                else {
                    barLineChart.getLegend().setDirection(IComponent.RIGHT);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }


  /*  private void showLineTypeSelectDialog(ChartStyle c) {
        quickChartDialog.showDialog(this,c,new String[]{"曲线","折线"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                if(position == 0){
                    barLineChart.setLineModel(LineChart.CURVE_MODEL);
                }else{
                    barLineChart.setLineModel(LineChart.LINE_MODEL);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }*/

    private void showLineStyleSelectDialog(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"CURVE","LINE"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                LineStyle l  = barLineChart.getProvider().getLineStyle();
                if(position == 0){
                   l.setEffect(new PathEffect());
                }else{
                    DashPathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8}, 1);
                    l.setEffect(effects);
                }
                barLineChart.startChartAnim(400);
            }
        });
    }
    private void rotateAngle(ChartStyle c) {

        quickChartDialog.showDialog(this,c,new String[]{"30","60","90","180","-30","-45","-60","-90","-180"},new QuickChartDialog.OnCheckChangeAdapter(){

            @Override
            public void onItemClick(String s, int position) {
                barLineChart.getHorizontalAxis().setRotateAngle(Integer.valueOf(s));
                barLineChart.startChartAnim(400);
            }
        });
    }

}
