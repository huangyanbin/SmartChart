package com.bin.david.smartchart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bin.david.smartchart.bean.ChartStyle;
import com.bin.david.smartchart.view.BaseCheckDialog;
import com.bin.david.smartchart.custom.CustomMarkView;
import com.bin.david.smartchart.view.QuickChartDialog;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.axis.VerticalAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.listener.OnClickColumnListener;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.provider.component.point.LegendPoint;
import com.daivd.chart.provider.component.point.Point;
import com.daivd.chart.provider.component.tip.MultiLineBubbleTip;
import com.daivd.chart.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private BaseCheckDialog<ChartStyle> chartDialog;
    private QuickChartDialog quickChartDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        quickChartDialog = new QuickChartDialog();
        lineChart = (LineChart) findViewById(R.id.lineChart);
        Resources res = getResources();
        FontStyle.setDefaultTextSpSize(this, 12);
        List<String> chartYDataList = new ArrayList<>();
        chartYDataList.add("Tokyo");
        chartYDataList.add("Paris");
        chartYDataList.add("Hong Kong");
        chartYDataList.add("Singapore");
        chartYDataList.add("Tokyo");
        chartYDataList.add("Paris");
        chartYDataList.add("Hong Kong");
        chartYDataList.add("Singapore");
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        tempList1.add(26d);
        tempList1.add(-35d);
        tempList1.add(-40d);
        tempList1.add(10d);
        tempList1.add(26d);
        tempList1.add(-35d);
        tempList1.add(-40d);
        tempList1.add(10d);
        final LineData columnData1 = new LineData("Temperature", "℃", IAxis.AxisDirection.RIGHT, getResources().getColor(R.color.arc3), tempList1);
        ArrayList<Double> humidityList = new ArrayList<>();
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        humidityList.add(60d);
        humidityList.add(50d);
        humidityList.add(30d);
        humidityList.add(65d);
        LineData columnData2 = new LineData("Humidity", "RH%", getResources().getColor(R.color.arc2), humidityList);
        ColumnDatas.add(columnData1);
        ColumnDatas.add(columnData2);
        ChartData<LineData> chartData2 = new ChartData<>("Line chart", chartYDataList, ColumnDatas);

        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis = lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis = lineChart.getHorizontalAxis();
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
        verticalAxis.getAxisStyle().setWidth(this, 1);
        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        VerticalCross cross = new VerticalCross();
        LineStyle crossStyle = cross.getCrossStyle();
        crossStyle.setWidth(this, 1);
        crossStyle.setColor(res.getColor(R.color.arc21));
        lineChart.getProvider().setCross(cross);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new CustomMarkView(this));
        //设置显示点
        Point point = new Point();
        point.getPointStyle().setShape(PointStyle.CIRCLE);
        //设置显示点的样式
        lineChart.getProvider().setPoint(point);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtils.sp2px(this, 13));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        MultiLineBubbleTip tip = new MultiLineBubbleTip<LineData>(this,
                R.mipmap.round_rect, R.mipmap.triangle, paint) {
            @Override
            public boolean isShowTip(LineData lineData, int position) {
                return position == 2;
            }

            @Override
            public String[] format(LineData lineData, int position) {
                String title = lineData.getName();
                String value = lineData.getChartYDataList().get(position) + lineData.getUnit();
                return new String[]{title, value};
            }
        };
        tip.setColorFilter(Color.parseColor("#FA8072"));
        tip.setAlpha(0.8f);
        lineChart.getProvider().setTip(tip);
        //设置显示标题
        lineChart.setShowChartName(true);
        lineChart.getMatrixHelper().setWidthMultiple(3);
        //设置标题方向
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        //设置标题比例
        lineChart.getChartTitle().setPercent(0.2f);
        //设置标题样式
        FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this, 15);

        LevelLine levelLine = new LevelLine(20);
        DashPathEffect effects2 = new DashPathEffect(new float[]{1, 2, 2, 4}, 1);
        levelLine.getLineStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        LegendPoint legendPoint = (LegendPoint) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.RECT);
        lineChart.getLegend().setPercent(0.2f);
        lineChart.getHorizontalAxis().setRotateAngle(-45);
        lineChart.setFirstAnim(false);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(1000);
        lineChart.setOnClickColumnListener(new OnClickColumnListener<LineData>() {
            @Override
            public void onClickColumn(LineData lineData, int position) {
                //  Toast.makeText(LineChartActivity.this,lineData.getChartYDataList().get(position)+lineData.getUnit(),Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn) {
            changedStyle();
        } else {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, 321);
            Toast.makeText(this, "正在保存", Toast.LENGTH_SHORT).show();
            boolean isSuc = lineChart.save();
            Toast.makeText(this, isSuc ? "保存成功" : "保存失败", Toast.LENGTH_SHORT).show();

        }

    }

    private void changedStyle() {

        if (chartDialog == null) {
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
        chartDialog.show(this, true, items);
    }

    private void showLine(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getProvider().setDrawLine(true);
                } else if (position == 1) {
                    lineChart.getProvider().setDrawLine(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showArea(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getProvider().setArea(true);
                } else if (position == 1) {
                    lineChart.getProvider().setArea(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void lengedClick(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"Yes", "No"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getLegend().setSelectColumn(true);
                } else if (position == 1) {
                    lineChart.getLegend().setSelectColumn(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showHGrid(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getLeftVerticalAxis().setDrawGrid(true);
                } else if (position == 1) {
                    lineChart.getLeftVerticalAxis().setDrawGrid(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showVGrid(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getHorizontalAxis().setDrawGrid(true);
                } else if (position == 1) {
                    lineChart.getHorizontalAxis().setDrawGrid(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void cross(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"YES", "No"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getProvider().setOpenCross(true);
                } else if (position == 1) {
                    lineChart.getProvider().setOpenCross(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void mark(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"Yes", "No"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getProvider().setOpenMark(true);
                } else if (position == 1) {
                    lineChart.getProvider().setOpenMark(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void zoom(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"Yes", "NO"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.setZoom(true);
                } else if (position == 1) {
                    lineChart.setZoom(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showValue(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getProvider().setShowText(true);
                } else if (position == 1) {
                    lineChart.getProvider().setShowText(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showTitle(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.setShowChartName(true);
                } else if (position == 1) {
                    lineChart.setShowChartName(false);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showPoint(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"show", "hide"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    Point point = new Point();
                    point.getPointStyle().setShape(PointStyle.CIRCLE);
                    //设置显示点的样式
                    lineChart.getProvider().setPoint(point);
                } else if (position == 1) {
                    lineChart.getProvider().setPoint(null);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showPointStyle(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"SQUARE", "CIRCLE", "RECT"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {

                Point point = new Point();
                PointStyle style = point.getPointStyle();
                if (position == 0) {
                    style.setShape(PointStyle.SQUARE);
                } else if (position == 1) {
                    style.setShape(PointStyle.CIRCLE);
                } else if (position == 2) {
                    style.setShape(PointStyle.RECT);
                }
                lineChart.getProvider().setPoint(point);
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showLegendStyle(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"SQUARE", "CIRCLE", "RECT"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                LegendPoint legendPoint = (LegendPoint) lineChart.getLegend().getPoint();
                PointStyle style = legendPoint.getPointStyle();
                if (position == 0) {
                    style.setShape(PointStyle.SQUARE);
                } else if (position == 1) {
                    style.setShape(PointStyle.CIRCLE);
                } else if (position == 2) {
                    style.setShape(PointStyle.RECT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showTitlePos(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"top", "bottom", "left", "right"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getChartTitle().setDirection(IComponent.TOP);
                } else if (position == 1) {
                    lineChart.getChartTitle().setDirection(IComponent.BOTTOM);
                } else if (position == 2) {
                    lineChart.getChartTitle().setDirection(IComponent.LEFT);
                } else {
                    lineChart.getChartTitle().setDirection(IComponent.RIGHT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showLegendPos(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"top", "bottom", "left", "right"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.getLegend().setDirection(IComponent.TOP);
                } else if (position == 1) {
                    lineChart.getLegend().setDirection(IComponent.BOTTOM);
                } else if (position == 2) {
                    lineChart.getLegend().setDirection(IComponent.LEFT);
                } else {
                    lineChart.getLegend().setDirection(IComponent.RIGHT);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void showLineTypeSelectDialog(ChartStyle c) {
        quickChartDialog.showDialog(this, c, new String[]{"CURVE", "LINE"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    lineChart.setLineModel(LineChart.CURVE_MODEL);
                } else {
                    lineChart.setLineModel(LineChart.LINE_MODEL);
                }
                lineChart.startChartAnim(400);
            }
        });
    }

    private void showLineStyleSelectDialog(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"solid line", "dotted line"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                LineStyle l = lineChart.getProvider().getLineStyle();
                if (position == 0) {
                    l.setEffect(new PathEffect());
                } else {
                    DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
                    l.setEffect(effects);
                }
                lineChart.startChartAnim(400);
            }
        });
    }


    private void rotateAngle(ChartStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"30", "60", "90", "180", "-30", "-45", "-60", "-90", "-180"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                lineChart.getHorizontalAxis().setRotateAngle(Integer.valueOf(s));
                lineChart.startChartAnim(400);
            }
        });
    }

}
