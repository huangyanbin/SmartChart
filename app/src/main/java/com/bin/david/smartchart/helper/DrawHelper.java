package com.bin.david.smartchart.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.LruCache;
import android.view.Gravity;
import android.view.View;

import com.bin.david.smartchart.R;
import com.bin.david.smartchart.view.BubbleMarkView;
import com.daivd.chart.component.axis.HorizontalAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.provider.barLine.LineProvider;
import com.daivd.chart.provider.component.cross.ICross;
import com.daivd.chart.provider.component.grid.IGrid;
import com.daivd.chart.provider.component.level.ILevel;
import com.daivd.chart.provider.component.path.IPath;
import com.daivd.chart.provider.component.path.LinePath;
import com.daivd.chart.provider.component.point.IPoint;
import com.daivd.chart.provider.component.text.IText;
import com.daivd.chart.provider.component.tip.SingleLineBubbleTip;
import com.daivd.chart.utils.ColorUtils;
import com.daivd.chart.utils.DensityUtils;
import com.daivd.chart.utils.DrawUtils;

import java.util.ArrayList;

/**
 * Created by huang on 2017/12/1.
 */

public class DrawHelper {

    private LruCache<Integer, Bitmap> lruCache;
    private BitmapFactory.Options options = new BitmapFactory.Options();

    private static DrawHelper helper;

    public static DrawHelper getInstance() {
        if (helper == null) {
            synchronized (DrawHelper.class) {
                if (helper == null) {
                    helper = new DrawHelper();
                }
            }
        }
        return helper;
    }

    protected Bitmap getBitmap(Context context, int resID) {
        Bitmap bitmap = lruCache.get(resID);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resID, options);
            if (bitmap != null) {
                lruCache.put(resID, bitmap);
            }
        }
        return bitmap;
    }

    private DrawHelper() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);// kB
        int cacheSize = maxMemory / 16;
        lruCache = new LruCache<Integer, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;// KB
            }
        };
    }

    public static void drawSoilChart(final Context context, LineChart lineChart){
        lineChart.getLeftVerticalAxis().setDisplay(false);
        ArrayList<Double> temp1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            temp1.add((double) ((int) (Math.random() * 20)) + 20);
        }
        final ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            xDatas.add("11."+i );
        }
        lineChart.getMatrixHelper().setWidthMultiple(3);
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        final int lineWidth = DensityUtils.dp2px(context, 1.5f);

        final int dp10 = DensityUtils.dp2px(context, 10);
        final int whiteColor = ContextCompat.getColor(context, android.R.color.white);
        LineProvider provider = lineChart.getProvider();
        provider.getLineStyle().setWidth(lineWidth*1.3f);
        LineData lineData1 = new LineData("土壤含水率", "%", whiteColor
                , temp1);
        provider.setStartZero(false);
        HorizontalAxis horizontalAxis = lineChart.getHorizontalAxis();
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.TOP);
        horizontalAxis.getScaleStyle().setTextSpSize(context,12).setTextColor(whiteColor);
        horizontalAxis.setShowAxisLine(false);
        horizontalAxis.getScaleStyle().setPadding(dp10*2);
        horizontalAxis.setGravity(Gravity.CENTER);

        horizontalAxis.isLine(false);
        final int alphaColor = ColorUtils.changeAlpha(whiteColor,75);
        lineChart.getProvider().setGrid(new IGrid() {

            @Override
            public void drawGrid(Canvas canvas, float x, Rect rect, int perWidth, Paint paint) {
                    float centerX = x+perWidth/2;
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(lineWidth);
                    paint.setColor(ContextCompat.getColor(context, R.color.soil_grid_color));
                    canvas.drawLine(centerX, 0, centerX, rect.bottom, paint);

            }

            @Override
            public void drawClickBg(Canvas canvas, PointF pointF, Rect rect, int perWidth, Paint paint) {

            }

            @Override
            public void drawColumnContent(Canvas canvas, int position, Rect zoomRect, Rect rect, int perWidth, Paint paint) {

            }
        });
        provider.setPoint(new IPoint() {

            public void drawPoint(Canvas canvas, float x, float y, int position, boolean isShowDefaultColor, Paint paint) {
                if(position == xDatas.size()-2) {
                    paint.setColor(ContextCompat.getColor(context,R.color.soil_bg));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                    paint.setColor(whiteColor);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                }

            }
        });
        provider.setPath(new LinePath(){
            @Override
            public void drawPath(Canvas canvas, Rect rect, Path path, int perWidth, Paint paint, float progress) {
                paint.setColor(alphaColor);
                Path alphaPath = new Path(path);
                alphaPath.lineTo(rect.right-perWidth/2,rect.bottom);
                alphaPath.lineTo(rect.left+perWidth/2,rect.bottom);
                alphaPath.close();
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(alphaPath,paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(whiteColor);
                super.drawPath(canvas, rect, path, perWidth, paint, progress);
            }
        });
        ChartData<LineData> chartData = new ChartData<>("要素分析", xDatas, lineData1);
        lineChart.setChartData(chartData);
    }

    /**
     * 要素分析
     *
     * @param context
     * @param lineChart
     */
    public static void drawFactorChart(final Context context, LineChart lineChart) {
        lineChart.getLeftVerticalAxis().setDisplay(false);
        ArrayList<Double> temp1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            temp1.add((double) ((int) (Math.random() * 20)) + 20);
        }
        ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            xDatas.add(i + "");
        }
        lineChart.getMatrixHelper().setWidthMultiple(3);
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        final int lineWidth = DensityUtils.dp2px(context, 1.5f);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        final int lineColor = ContextCompat.getColor(context, R.color.low_temp_color);
        final int dp10 = DensityUtils.dp2px(context, 10);
        final int fontSize = DensityUtils.sp2px(context, 13);
        final int perColor = ContextCompat.getColor(context, R.color.temp_value_color);
        final int axisColor = ContextCompat.getColor(context,R.color.factor_axis_color);
        final int currentColor = ContextCompat.getColor(context,R.color.temp_text_color);
        LineProvider provider = lineChart.getProvider();
        provider.getLineStyle().setWidth((float) (lineWidth));
        LineData lineData1 = new LineData("空气温度", "℃", lineColor
                , temp1);

        provider.setStartZero(true);
        HorizontalAxis horizontalAxis = lineChart.getHorizontalAxis();
        horizontalAxis.getScaleStyle().setTextSpSize(context,12).setTextColor(perColor);
        horizontalAxis.getAxisStyle().setWidth(lineWidth).setColor(axisColor);
        horizontalAxis.setGravity(Gravity.LEFT);
        horizontalAxis.isLine(true);
        horizontalAxis.setFormat(new IFormat<String>() {
            @Override
            public String format(String s) {
                return (s.length() ==1 ?"0"+s :s)+"时";
            }
        });

        provider.setText(new IText() {

            public void drawText(Canvas canvas, String value, float x, float y, int position, int line, Paint paint) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(fontSize);
                paint.setColor(currentColor);
                int textSize = DrawUtils.getTextHeight(paint);
                int dis = dp10 / 2 + textSize;
                canvas.drawText(value + "℃", x, y + dis, paint);
            }
        });
        lineChart.getProvider().setGrid(new IGrid() {
            @Override
            public void drawGrid(Canvas canvas, float x, Rect rect, int perWidth, Paint paint) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(lineWidth);
                paint.setColor(ContextCompat.getColor(context, R.color.factor_grid_color));
                canvas.drawLine(x + perWidth / 2, rect.top, x + perWidth / 2, rect.bottom, paint);
            }

            @Override
            public void drawClickBg(Canvas canvas, PointF pointF, Rect rect, int perWidth, Paint paint) {

            }

            @Override
            public void drawColumnContent(Canvas canvas, int position, Rect zoomRect, Rect rect, int perWidth, Paint paint) {

            }
        });
        provider.setPoint(new IPoint() {
            @Override
            public void drawPoint(Canvas canvas, float x, float y, int position, boolean isShowDefaultColor, Paint paint) {
                int oldColor = paint.getColor();
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(x, y, dp10 / 2, paint);
                paint.setColor(oldColor);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(x, y, dp10 / 2, paint);

            }
        });
        ChartData<LineData> chartData = new ChartData<>("要素分析", xDatas, lineData1);
        lineChart.setChartData(chartData);
    }

    /**
     * 绘制观测站地温
     */
    public static void drawGroundTempChart(final Context context, LineChart lineChart) {
        final ArrayList<Double> temp1 = new ArrayList<>();
        ArrayList<Double> temp2 = new ArrayList<>();
        ArrayList<Double> temp3 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            temp1.add((double) ((int) (Math.random() * 10)) + 30);
        }
        for (int i = 0; i < 6; i++) {
            temp2.add((double) ((int) (Math.random() * 10)) + 20);
        }
        for (int i = 0; i < 6; i++) {
            temp3.add((double) ((int) (Math.random() * 10)) + 10);
        }
        final ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            xDatas.add(i + "");
        }
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        LineData lineData1 = new LineData("5cm", "℃",
                ContextCompat.getColor(context, R.color.ground_5cm), temp1);
        LineData lineData2 = new LineData("10cm", "℃",
                ContextCompat.getColor(context, R.color.ground_10cm), temp2);
        LineData lineData3 = new LineData("20cm", "℃",
                ContextCompat.getColor(context, R.color.ground_20cm), temp3);
        ChartData<LineData> chartData = new ChartData<>("地温", xDatas, lineData1, lineData2, lineData3);
        LineProvider provider = lineChart.getProvider();
        lineChart.getHorizontalAxis().setDisplay(false);
        lineChart.getLeftVerticalAxis().setDisplay(false);
        provider.setArea(true);
        provider.setAreaAlpha(255);
        provider.setStartZero(true);
        provider.setChartPercent(0.8f);
        provider.addLevelLine(getLevelLine(context, temp1.get(0),lineData1.getName()));
        provider.addLevelLine(getLevelLine(context, temp2.get(0),lineData2.getName()));
        provider.addLevelLine(getLevelLine(context, temp3.get(0),lineData3.getName()));
        provider.setMarkView(new BubbleMarkView(context, ContextCompat.getColor(context, R.color.ground_mark_text_color)));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(com.daivd.chart.utils.DensityUtils.sp2px(context, 13));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context, R.color.ground_mark_text_color));
        provider.setTip(new SingleLineBubbleTip<LineData>(context, R.mipmap.round, R.mipmap.transparent, paint
        ) {
            @Override
            public boolean isShowTip(LineData lineData, int position) {
                if (position == xDatas.size() - 2) {
                    return true;
                }
                return false;
            }

            @Override
            public String format(LineData lineData, int position) {
                double temp = lineData.getChartYDataList().get(position);
                return temp + lineData.getUnit();
            }
        });
        lineChart.setChartData(chartData);

    }

    private static ILevel getLevelLine(final Context context, final double value, final String val) {

        final int levelColor = ContextCompat.getColor(context, R.color.ground_level_color);
        return new ILevel() {
            @Override
            public int getAxisDirection() {
                return IAxis.AxisDirection.LEFT;
            }

            @Override
            public void drawLevel(Canvas canvas, Rect rect, float y, Paint paint) {
                paint.setColor(levelColor);
                paint.setTextSize(DensityUtils.sp2px(context, 12));
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(val , rect.left + 10, y + DrawUtils.getTextHeight(paint), paint);
            }

            @Override
            public double getValue() {
                return value;
            }

        };

    }


    /**
     * 绘制观测站冻土
     */
    public static void drawFrozenSoilChart(final Context context, LineChart lineChart) {
        lineChart.getLeftVerticalAxis().setDisplay(false);
        ArrayList<Double> temp1 = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            temp1.add((double) ((int) (Math.random() * 20)) + 20);
        }
        ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            xDatas.add(i + "");
        }
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        final int lineWidth = DensityUtils.dp2px(context, 2f);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        final int lineColor = ContextCompat.getColor(context, R.color.frozen_line_color);
        final int crossColor = ContextCompat.getColor(context, R.color.frozen_cross_color);
        final int shadowColor = ContextCompat.getColor(context, R.color.frozen_shadow_color);
        LineProvider provider = lineChart.getProvider();
        provider.getLineStyle().setWidth((float) (lineWidth * 1.2));
        LineData lineData1 = new LineData("冻土", "cm", lineColor
                , temp1);

        provider.setStartZero(true);
        HorizontalAxis horizontalAxis = lineChart.getHorizontalAxis();
        horizontalAxis.getScaleStyle().setTextColor(lineColor);
        horizontalAxis.setGravity(Gravity.LEFT);
        horizontalAxis.isLine(true);
        horizontalAxis.getAxisStyle().setColor(crossColor).setWidth(lineWidth);
        provider.setMarkView(new BubbleMarkView(context, ContextCompat.getColor(context, R.color.frozen_mark_text_color)));
        lineChart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        provider.setCross(new ICross() {
            @Override
            public void drawCross(Canvas canvas, PointF pointF, Rect rect, Paint paint) {
                paint.setColor(lineColor);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(pointF.x, pointF.y, lineWidth * 2, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(lineWidth);
                paint.setColor(crossColor);
                canvas.drawLine(pointF.x, pointF.y, pointF.x, rect.bottom, paint);
            }
        });
        provider.setPath(new IPath() {
            @Override
            public void drawPath(Canvas canvas, Rect rect, Path path, int perWidth, Paint paint, float progress) {

                paint.setShadowLayer(40, 10, 50, shadowColor);
                canvas.drawPath(path, paint);
                paint.clearShadowLayer();
            }
        });
        ChartData<LineData> chartData = new ChartData<>("观测站", xDatas, lineData1);
        lineChart.setChartData(chartData);
    }

    /**
     * 绘制天气
     *
     * @param context
     */
    public static void drawWeatherChart(final Context context, LineChart lineChart) {
        ArrayList<Double> temp1 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            temp1.add((double) ((int) (Math.random() * 20)) + 20);
        }
        ArrayList<Double> temp2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            temp2.add((double) ((int) (Math.random() * 20)));
        }
        ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            xDatas.add("");
        }
        lineChart.getMatrixHelper().setWidthMultiple(7/5f);
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        final android.graphics.Point selectPosition = new android.graphics.Point(1, 0);
        final int lineWidth = DensityUtils.dp2px(context, 1.5f);
        final int dp10 = DensityUtils.dp2px(context, 10);
        final int fontSize = DensityUtils.sp2px(context, 13);
        final int clickColor = ContextCompat.getColor(context, R.color.temp_click_text_color);
        final int perColor = ContextCompat.getColor(context, R.color.temp_value_color);
        final int currentColor = ContextCompat.getColor(context, R.color.temp_text_color);
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(clickColor, PorterDuff.Mode.SRC_IN);

        FontStyle.setDefaultTextSpSize(context, 14);
        LineData lineData1 = new LineData("温度1", "℃",
                ContextCompat.getColor(context, R.color.high_temp_color), temp1);
        LineData lineData2 = new LineData("温度2", "℃",
                ContextCompat.getColor(context, R.color.low_temp_color), temp2);
        ChartData<LineData> chartData = new ChartData<>("天气", xDatas, lineData1, lineData2);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        lineChart.getHorizontalAxis().setDisplay(false);
        lineChart.getLeftVerticalAxis().setDisplay(false);
        LineProvider provider = lineChart.getProvider();
        provider.getLineStyle().setWidth(lineWidth);
        provider.setStartZero(false);
        provider.setChartPercent(0.37f);
        provider.setPoint(new IPoint() {
            @Override
            public void drawPoint(Canvas canvas, float x, float y, int position, boolean isShowDefaultColor, Paint paint) {
                if (position == selectPosition.x) {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                } else {
                    int oldColor = paint.getColor();
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                    paint.setColor(oldColor);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                }
            }
        });
        provider.setText(new IText() {
            @Override
            public void drawText(Canvas canvas, String value, float x, float y, int position, int line, Paint paint) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(fontSize);
                paint.setColor(perColor);
                int textSize = DrawUtils.getTextHeight(paint);
                int dis = dp10 / 2 + textSize;
                canvas.drawText(value + "℃", x, line == 0 ? y - dis + textSize / 2 : y + dis, paint);
            }
        });

        provider.setPath(new IPath() {
            @Override
            public void drawPath(Canvas canvas, Rect rect, Path path, int perWidth, Paint paint, float progress) {
                if (progress != 1) {
                    canvas.save();
                    canvas.clipRect(rect.left, rect.top, rect.width() * progress, rect.bottom);
                }
                canvas.save();
                DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
                canvas.clipRect(rect.left, rect.top, rect.left + perWidth * 3 / 2, rect.bottom);
                paint.setPathEffect(effects);
                canvas.drawPath(path, paint);
                canvas.restore();
                canvas.save();
                paint.setPathEffect(new PathEffect());
                canvas.clipRect(rect.left + perWidth * 3 / 2, rect.top, rect.right, rect.bottom);
                canvas.drawPath(path, paint);
                canvas.restore();
                if (progress != 1) {
                    canvas.restore();
                }
            }
        });
        final Rect imgRect = new Rect();
        final Rect drawRect = new Rect();
        lineChart.getProvider().setGrid(new IGrid() {
            PointF pointF = new PointF();

            @Override
            public void drawGrid(Canvas canvas, float x, Rect rect, int perWidth, Paint paint) {
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(lineWidth);
                paint.setColor(ContextCompat.getColor(context, R.color.temp_grid_color));
                canvas.drawLine(x + perWidth / 2, rect.top, x + perWidth / 2, rect.bottom, paint);
            }

            @Override
            public void drawClickBg(Canvas canvas, PointF pointF, Rect rect, int perWidth, Paint paint) {
                if (pointF != null) {
                    int position;
                    if (this.pointF.x != pointF.x && this.pointF.y != pointF.y) {
                        this.pointF.set(pointF);
                        position = (int) ((pointF.x - rect.left) / perWidth);
                        selectPosition.x = position;
                    } else {
                        position = selectPosition.x;
                    }
                    int left = rect.left + position * perWidth;
                    paint.setColor(ContextCompat.getColor(context, R.color.temp_click_bg_color));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(left, rect.top, left + perWidth, rect.bottom, paint);
                }
            }

            @Override
            public void drawColumnContent(Canvas canvas, int position, Rect zoomRect, Rect rect, int perWidth, Paint paint) {

                int left = zoomRect.left + perWidth * position;
                int right = left + perWidth;
                if (rect.contains(left, rect.centerY()) || rect.contains(right, rect.centerY())) {
                    int centerX = (left + right) / 2;
                    paint.setStyle(Paint.Style.FILL);
                    if (position == selectPosition.x) {
                        paint.setColor(clickColor);
                    } else if (position == 0) {
                        paint.setColor(perColor);
                    } else {
                        paint.setColor(currentColor);
                    }
                    paint.setTextSize(fontSize);
                    paint.setTextAlign(Paint.Align.CENTER);
                    canvas.drawText("今天", centerX, DrawUtils.getTextCenterY(dp10 * 2, paint), paint);
                    canvas.drawText("12/01", centerX, DrawUtils.getTextCenterY((int) (dp10 * 3.5), paint), paint);
                    canvas.drawText("晴", centerX, DrawUtils.getTextCenterY(dp10 * 6, paint), paint);
                    drawBitmap(canvas, centerX, (int) (dp10 * 9.3), R.mipmap.sun, paint, position == selectPosition.x);
                    if (position % 2 == 0) {
                        canvas.drawText("西北风", centerX, DrawUtils.getTextCenterY((int) (dp10 * 12.3), paint), paint);
                        canvas.drawText("3级", centerX, DrawUtils.getTextCenterY((int) (dp10 * 12.5) + DrawUtils.getTextHeight(paint), paint), paint);
                    } else {
                        canvas.drawText("西北风", centerX, DrawUtils.getTextCenterY((int) (dp10 * 13.1), paint), paint);
                    }
                    canvas.drawText("微风", centerX, DrawUtils.getTextCenterY(zoomRect.bottom - (int) (dp10 * 3.2), paint), paint);
                    drawBitmap(canvas, centerX, (int) (zoomRect.bottom - dp10 * 6.2), R.mipmap.wind, paint, false);
                    drawBitmap(canvas, centerX, (int) (zoomRect.bottom - dp10 * 10.4), R.mipmap.rain, paint, position == selectPosition.x);
                    canvas.drawText("晴", centerX, DrawUtils.getTextCenterY(zoomRect.bottom - (int) (dp10 * 13.5), paint), paint);
                }
            }

            private void drawBitmap(Canvas canvas, int x, int y, int drawableID, Paint paint, boolean isColorFilter) {

                Bitmap bitmap = DrawHelper.getInstance().getBitmap(context, drawableID);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                imgRect.set(0, 0, width, height);
                int imgHalfWidth = (int) (dp10 * 1.25);
                drawRect.set(x - imgHalfWidth, y - imgHalfWidth, x + imgHalfWidth, y + imgHalfWidth);
                if (isColorFilter) {
                    paint.setColorFilter(colorFilter);
                    canvas.drawBitmap(bitmap, imgRect, drawRect, paint);
                    paint.setColorFilter(null);
                } else {
                    canvas.drawBitmap(bitmap, imgRect, drawRect, paint);
                }
            }
        });
        lineChart.setChartData(chartData);
        lineChart.startChartAnim(1000);
    }
    /**
     * 绘制首页天气
     *
     * @param context
     */
    public static void drawHomeWeatherChart(final Context context, LineChart lineChart) {
        ArrayList<Double> temp1 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            temp1.add((double) ((int) (Math.random() * 20)) + 20);
        }
        ArrayList<Double> temp2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            temp2.add((double) ((int) (Math.random() * 20)));
        }
        ArrayList<String> xDatas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            xDatas.add("");
        }
        lineChart.getMatrixHelper().setWidthMultiple(7/5f);
        lineChart.setShowChartName(false);
        lineChart.getLegend().setDisplay(false);
        final android.graphics.Point selectPosition = new android.graphics.Point(1, 0);
        final int lineWidth = DensityUtils.dp2px(context, 1.5f);
        final int dp10 = DensityUtils.dp2px(context, 10);
        final int fontSize = DensityUtils.sp2px(context, 13);
        final int clickColor = ContextCompat.getColor(context, R.color.temp_click_text_color);
        final int perColor = ContextCompat.getColor(context, R.color.temp_value_color);
        final int currentColor = ContextCompat.getColor(context, R.color.temp_text_color);
        final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(clickColor, PorterDuff.Mode.SRC_IN);

        FontStyle.setDefaultTextSpSize(context, 14);
        LineData lineData1 = new LineData("温度1", "℃",
                ContextCompat.getColor(context, R.color.high_temp_color), temp1);
        LineData lineData2 = new LineData("温度2", "℃",
                ContextCompat.getColor(context, R.color.low_temp_color), temp2);
        ChartData<LineData> chartData = new ChartData<>("天气", xDatas, lineData1, lineData2);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        lineChart.getHorizontalAxis().setDisplay(false);
        lineChart.getLeftVerticalAxis().setDisplay(false);
        LineProvider provider = lineChart.getProvider();
        provider.getLineStyle().setWidth(lineWidth);
        provider.setStartZero(false);
        provider.setChartPercent(0.37f);
        provider.setPoint(new IPoint() {
            @Override
            public void drawPoint(Canvas canvas, float x, float y, int position, boolean isShowDefaultColor, Paint paint) {
                if (position == selectPosition.x) {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                } else {
                    int oldColor = paint.getColor();
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                    paint.setColor(oldColor);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(x, y, dp10 / 2, paint);
                }
            }
        });
        provider.setText(new IText() {
            @Override
            public void drawText(Canvas canvas, String value, float x, float y, int position, int line, Paint paint) {
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setStyle(Paint.Style.FILL);
                paint.setTextSize(fontSize);
                paint.setColor(perColor);
                int textSize = DrawUtils.getTextHeight(paint);
                int dis = dp10 / 2 + textSize;
                canvas.drawText(value + "℃", x, line == 0 ? y - dis + textSize / 2 : y + dis, paint);
            }
        });

        provider.setPath(new IPath() {
            @Override
            public void drawPath(Canvas canvas, Rect rect, Path path, int perWidth, Paint paint, float progress) {
                if (progress != 1) {
                    canvas.save();
                    canvas.clipRect(rect.left, rect.top, rect.width() * progress, rect.bottom);
                }
                canvas.save();
                DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
                canvas.clipRect(rect.left, rect.top, rect.left + perWidth * 3 / 2, rect.bottom);
                paint.setPathEffect(effects);
                canvas.drawPath(path, paint);
                canvas.restore();
                canvas.save();
                paint.setPathEffect(new PathEffect());
                canvas.clipRect(rect.left + perWidth * 3 / 2, rect.top, rect.right, rect.bottom);
                canvas.drawPath(path, paint);
                canvas.restore();
                if (progress != 1) {
                    canvas.restore();
                }
            }
        });
        final Rect imgRect = new Rect();
        final Rect drawRect = new Rect();
        lineChart.getProvider().setGrid(new IGrid() {
            PointF pointF = new PointF();

            @Override
            public void drawGrid(Canvas canvas, float x, Rect rect, int perWidth, Paint paint) {
                /*paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(lineWidth);
                paint.setColor(ContextCompat.getColor(context, R.color.temp_grid_color));
                canvas.drawLine(x + perWidth / 2, rect.top, x + perWidth / 2, rect.bottom, paint);*/
            }

            @Override
            public void drawClickBg(Canvas canvas, PointF pointF, Rect rect, int perWidth, Paint paint) {
                if (pointF != null) {
                    int position;
                    if (this.pointF.x != pointF.x && this.pointF.y != pointF.y) {
                        this.pointF.set(pointF);
                        position = (int) ((pointF.x - rect.left) / perWidth);
                        selectPosition.x = position;
                    } else {
                        position = selectPosition.x;
                    }
                    int left = rect.left + position * perWidth;
                    paint.setColor(ContextCompat.getColor(context, R.color.temp_click_bg_color));
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawRect(left, rect.top, left + perWidth, rect.bottom, paint);
                }
            }

            @Override
            public void drawColumnContent(Canvas canvas, int position, Rect zoomRect, Rect rect, int perWidth, Paint paint) {

                int left = zoomRect.left + perWidth * position;
                int right = left + perWidth;
                if (rect.contains(left, rect.centerY()) || rect.contains(right, rect.centerY())) {
                    int centerX = (left + right) / 2;
                    paint.setStyle(Paint.Style.FILL);
                    if (position == selectPosition.x) {
                        paint.setColor(clickColor);
                    } else if (position == 0) {
                        paint.setColor(perColor);
                    } else {
                        paint.setColor(currentColor);
                    }
                    paint.setTextSize(fontSize);
                    paint.setTextAlign(Paint.Align.CENTER);
                    int textHeight = DrawUtils.getTextHeight(paint);
                    canvas.drawText("12/01", centerX, DrawUtils.getTextCenterY((int) (dp10 * 1.3f)+textHeight/2, paint), paint);
                    canvas.drawText("今天", centerX, DrawUtils.getTextCenterY((int) (dp10 * 2.2+textHeight), paint), paint);
                    canvas.drawText("晴", centerX, DrawUtils.getTextCenterY((int) (dp10 * 8.7f), paint), paint);
                    drawBitmap(canvas, centerX, (int) (dp10 * 6.2), R.mipmap.sun, paint, position == selectPosition.x);
                   /* if (position % 2 == 0) {
                        canvas.drawText("西北风", centerX, DrawUtils.getTextCenterY((int) (dp10 * 12.3), paint), paint);
                        canvas.drawText("3级", centerX, DrawUtils.getTextCenterY((int) (dp10 * 12.5) + DrawUtils.getTextHeight(paint), paint), paint);
                    } else {
                        canvas.drawText("西北风", centerX, DrawUtils.getTextCenterY((int) (dp10 * 13.1), paint), paint);
                    }*/
                    canvas.drawText("微风", centerX, DrawUtils.getTextCenterY(zoomRect.bottom - (int) (dp10 * 2.2), paint), paint);
                    drawBitmap(canvas, centerX, (int) (zoomRect.bottom - dp10 * 4.3), R.mipmap.wind, paint, false);
                    drawBitmap(canvas, centerX, (int) (zoomRect.bottom - dp10 * 9.2), R.mipmap.rain, paint, position == selectPosition.x);
                    canvas.drawText("晴", centerX, DrawUtils.getTextCenterY(zoomRect.bottom - (int) (dp10 * 6.8), paint), paint);
                }
            }

            private void drawBitmap(Canvas canvas, int x, int y, int drawableID, Paint paint, boolean isColorFilter) {

                Bitmap bitmap = DrawHelper.getInstance().getBitmap(context, drawableID);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                imgRect.set(0, 0, width, height);
                int imgHalfWidth = (int) (dp10 * 1.25);
                drawRect.set(x - imgHalfWidth, y - imgHalfWidth, x + imgHalfWidth, y + imgHalfWidth);
                if (isColorFilter) {
                    paint.setColorFilter(colorFilter);
                    canvas.drawBitmap(bitmap, imgRect, drawRect, paint);
                    paint.setColorFilter(null);
                } else {
                    canvas.drawBitmap(bitmap, imgRect, drawRect, paint);
                }
            }
        });
        lineChart.setChartData(chartData);
        lineChart.startChartAnim(1000);
    }

}
