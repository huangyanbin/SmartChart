package com.daivd.chart.provider.pie;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.PieData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.BaseProvider;
import com.daivd.chart.utils.ColorUtils;

import java.util.List;

/**
 * Created by huang on 2017/10/9.
 */

public class PieProvider extends BaseProvider<PieData> {

    private RectF oval;
    private int totalAngle = 360;
    private PointF centerPoint;
    private int centerRadius;
    private double clickAngle = -1;
    private final float startAngle = -90;
    private RotateHelper rotateHelper;
    private FontStyle textStyle = new FontStyle();
    private float centerCirclePercent = 0.3f;
    private boolean isShader;
    private boolean isClick;

    @Override
    public boolean calculationChild(ChartData<PieData> chartData) {
        return true;
    }


    @Override
    protected void matrixRect(Canvas canvas, Rect rect) {
        super.matrixRect(canvas, rect);
        if(rotateHelper != null && rotateHelper.isRotate()){
            canvas.rotate((float) rotateHelper.getStartAngle(),rect.centerX(),rect.centerY());
        }
    }

    @Override
    protected void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        int layerId = canvas.saveLayer(rect.left,rect.top,rect.right,rect.bottom, null, Canvas.ALL_SAVE_FLAG);
        float startAngle = this.startAngle;
        float totalAngle = getAnimValue(this.totalAngle);
        paint.setStyle(Paint.Style.FILL);
        int h = zoomRect.height();
        int w = zoomRect.width();
        int maxRadius = Math.min(w/2, h/2);
        centerPoint = new PointF(zoomRect.centerX(), zoomRect.centerY());
        int x = maxRadius / 10;
        centerRadius = maxRadius - x;
        if(rotateHelper != null){
            rotateHelper.setRadius(centerRadius);
            rotateHelper.setRect(rect);
        }
        oval= new RectF(zoomRect.centerX()-centerRadius,zoomRect.centerY() - centerRadius,
                zoomRect.centerX()+centerRadius,zoomRect.centerY() + centerRadius);
        List<PieData> pieDataList = chartData.getColumnDataList();
        float totalScores = 0f;
        for (PieData pieData: pieDataList) {
            totalScores += pieData.getChartYDataList();
        }
        for (int i = 0; i < pieDataList.size(); i++) {
            PieData pieData = pieDataList.get(i);
            double value = pieData.getChartYDataList();
            float sweepAngle = (float) (totalAngle * value / totalScores);
            if(pieData.isDraw()) {
                paint.setColor(pieData.getColor());
                if (clickAngle != -1 && clickAngle > startAngle && clickAngle < startAngle + sweepAngle) {
                    paint.setColor(ColorUtils.getDarkerColor(pieData.getColor()));
                    if(isClick && onClickColumnListener != null){
                        onClickColumnListener.onClickColumn(pieData,0);
                        isClick = false;
                    }
                }
                canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
                canvas.save();
                canvas.rotate(startAngle + sweepAngle / 2 - this.startAngle, zoomRect.centerX(), zoomRect.centerY());
                textStyle.fillPaint(paint);
                int textHeight = (int) paint.measureText("1", 0, 1);
                String val = String.valueOf(value);
                canvas.drawText(val, zoomRect.centerX() - val.length() * textHeight / 2, zoomRect.centerY() - maxRadius / 2, paint);
                canvas.restore();
            }
            startAngle += sweepAngle;
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        if(centerCirclePercent >0) {
            paint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(rect.centerX(),rect.centerY(),centerRadius *centerCirclePercent,paint);
        }
        canvas.restoreToCount(layerId);
        paint.setXfermode(null);
    }

    @Override
    public double[] setMaxMinValue(double maxMinValue, double minValue) {
        return new double[0];
    }


    @Override
    public void clickPoint(PointF point) {
        super.clickPoint(point);
        if(centerPoint != null){
            int x = (int) (point.x - centerPoint.x);
            int y = (int) (point.y - centerPoint.y);
            int z = (int) Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
            if (/*z >= centerRadius - circleBorder / 2 &&*/ z <= centerRadius  + 20) {
                double angle = Math.abs(Math.toDegrees(Math.atan((point.y - centerPoint.y) / (point.x - centerPoint.x))));
                if (x >= 0 && y < 0) {
                    angle = 90 - angle;
                } else if (x >= 0 && y >= 0) {
                    angle = 90 + angle;
                } else if (x < 0 && y >= 0) {
                    angle = 270 - angle;
                } else {
                    angle = 270 + angle;
                }
                angle = (angle -rotateHelper.getStartAngle())%360;
                angle = angle <0 ? 360+angle:angle;
                clickAngle = angle + startAngle;
                isClick = true;
                return;
            }
            clickAngle = -1;
        }

    }





    public void setCenterCirclepercent(float centerCirclePercent) {
        this.centerCirclePercent = centerCirclePercent;
    }

    public void setRotateHelper(RotateHelper rotateHelper) {
        this.rotateHelper = rotateHelper;
    }
}
