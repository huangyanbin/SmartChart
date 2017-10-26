package com.daivd.chart.provider.rose;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.RoseData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.BaseProvider;

import java.util.List;


/**
 * Created by huang on 2017/10/9.
 */

public class RoseProvider extends BaseProvider<RoseData> {

    private int centerRadius;
    private RotateHelper rotateHelper;
    private FontStyle scaleStyle = new FontStyle();
    private LineStyle lineStyle = new LineStyle();
    private float startAngle=0;
    protected LineStyle gridStyle = new LineStyle(); //网格样式
    private int textHeight;
    private  boolean isShowScale;
    private IFormat<Double> scaleFormat;

    @Override
    protected void matrixRectStart(Canvas canvas, Rect rect) {
        super.matrixRectStart(canvas, rect);
        if (rotateHelper != null && rotateHelper.isRotate()) {
            canvas.rotate((float) rotateHelper.getStartAngle(), rect.centerX(), rect.centerY());
        }
    }

    @Override
    protected void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        int w = zoomRect.width();
        int h = zoomRect.height();
        int maxRadius = Math.min(w / 2, h / 2);
        textHeight = (int) (paint.measureText("1", 0, 1));
        scaleStyle.fillPaint(paint);
        String oneXData = chartData.getCharXDataList().get(0);
        int x = maxRadius / 10 + textHeight * oneXData.length();
        centerRadius = maxRadius - x;
        if (rotateHelper != null) {
            rotateHelper.setOriginRect(rect);
            rotateHelper.setRadius(centerRadius);
        }
        drawRadarBorder(canvas, zoomRect, paint);
        drawRadarLines(canvas, zoomRect, paint);
        drawLinePath(canvas, zoomRect, rect, paint);
        drawQuadrantText(canvas, zoomRect, paint);
        drawScale(canvas,zoomRect,paint);
    }

    private void drawLinePath(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {

        List<String> charXDataList = chartData.getCharXDataList();
        int count = charXDataList.size();
        ScaleData scaleData = chartData.getScaleData();
        List<RoseData> columnDataList = chartData.getColumnDataList();
        double maxScale = scaleData.getMaxScaleValue(IAxis.AxisDirection.LEFT);
        float angle = 360f / count;
        startAngle = angle/2;
        paint.setStyle(Paint.Style.FILL);
        for (int j = 0; j < count; j++) {
            double value = 0;
            for (BarData lineData :columnDataList) {
                if(lineData.isDraw()) {
                    value += lineData.getChartYDataList().get(j);
                }
            }
            for (int i = columnDataList.size() - 1; i >= 0; i--) {
                BarData lineData = columnDataList.get(i);
                if(lineData.isDraw()) {
                    float curR = getAnimValue((float) (value * centerRadius / maxScale));
                    RectF rectF = new RectF(zoomRect.centerX() - curR, zoomRect.centerY() - curR, zoomRect.centerX() + curR, zoomRect.centerY() + curR);
                    paint.setColor(lineData.getColor());
                    canvas.drawArc(rectF, angle * j + angle / 6 - startAngle - 90, angle - angle / 6, true, paint);
                    value -= lineData.getChartYDataList().get(j);
                }
            }

        }
    }

    private void drawRadarBorder(Canvas canvas, Rect zoomRect, Paint paint) {
        gridStyle.fillPaint(paint);
        canvas.drawCircle(zoomRect.centerX(), zoomRect.centerY(), centerRadius, paint);
        canvas.drawCircle(zoomRect.centerX(), zoomRect.centerY(), centerRadius / 2, paint);
    }

    private void drawScale(Canvas canvas, Rect zoomRect, Paint paint){
        double maxScale = chartData.getScaleData().getMaxScaleValue(IAxis.AxisDirection.LEFT);
        if(isShowScale) {
            scaleStyle.fillPaint(paint);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float fontHeight = fontMetrics.descent - fontMetrics.ascent;
            canvas.drawText(getFormatValue(maxScale),
                    zoomRect.centerX(), zoomRect.centerY() - centerRadius+fontHeight, paint);
            canvas.drawText(getFormatValue(maxScale/2),
                    zoomRect.centerX(),zoomRect.centerY()-centerRadius/2+fontHeight,paint);
        }
    }

    /**
     * 绘制象限文字
     *
     * @param canvas
     */
    private void drawQuadrantText(Canvas canvas, Rect zoomRect, Paint paint) {
        scaleStyle.fillPaint(paint);
        List<String> charXDataList = chartData.getCharXDataList();
        int count = charXDataList.size();
        float angle = (float) (Math.PI*2/count);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            float realAngle = (float) (angle*i - Math.PI/2);
            String xData = charXDataList.get(i);
            float x = (float) (zoomRect.centerX()+(centerRadius+fontHeight/2)*Math.cos(realAngle));
            float y = (float) (zoomRect.centerY()+(centerRadius+fontHeight/2)*Math.sin(realAngle))+5;
            if(realAngle>=0&&realAngle<=Math.PI/2){//第4象限
                canvas.drawText(xData, x,y,paint);
            }else if(realAngle<0){//第3象限
                canvas.drawText(xData, x,y,paint);
            }else if(realAngle>Math.PI/2&&realAngle<=Math.PI){//第2象限
                float dis = paint.measureText(xData);//文本长度
                canvas.drawText(xData, x-dis,y,paint);
            }else if(realAngle>=Math.PI&&realAngle<3*Math.PI/2){//第1象限
                float dis = paint.measureText(xData);//文本长度
                canvas.drawText(xData, x-dis,y,paint);
            }
        }
    }

    /**
     * 绘制直线
     */
    private void drawRadarLines(Canvas canvas, Rect zoomRect, Paint paint) {
        gridStyle.fillPaint(paint);
        Path path = new Path();
        int count = chartData.getCharXDataList().size();
        float angle = (float) (Math.PI * 2 / count);

        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(zoomRect.centerX(), zoomRect.centerY());
            float x = (float) (zoomRect.centerX() + centerRadius * Math.cos(angle * i-Math.PI/2));
            float y = (float) (zoomRect.centerY() + centerRadius * Math.sin(angle * i-Math.PI/2));
            path.lineTo(x, y);
            canvas.drawPath(path, paint);
        }
    }


    private String getFormatValue(double value){
        return scaleFormat !=null ? scaleFormat.format(value):String.valueOf(value);
    }

   /* @Override
    public void clickPoint(PointF point) {
        super.clickPoint(point);
        if(centerPoint != null){
            int x = (int) (point.x - centerPoint.x);
            int y = (int) (point.y - centerPoint.y);
            int z = (int) Math.sqrt(Math.pow(Math.abs(x), 2) + Math.pow(Math.abs(y), 2));
            if (*//*z >= centerRadius - circleBorder / 2 &&*//* z <= centerRadius  + 20) {
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
                return;
            }
            clickAngle = -1;
        }

    }*/

    void setSelection(int position) {

    }


    @Override
    public boolean calculationChild(ChartData<RoseData> chartData) {
        this.chartData = chartData;
        ScaleData scaleData = this.chartData.getScaleData();
        scaleData.maxLeftValue = 0;
        List<RoseData> columnDatas = chartData.getColumnDataList();
        if (columnDatas == null || columnDatas.size() == 0) {
            return false;
        }
        int columnSize = columnDatas.size();

        for (int j = 0; j < chartData.getCharXDataList().size(); j++) {
            float maxValue = 0;
            for (int i = 0; i < columnSize; i++) {
                BarData columnData = columnDatas.get(i);
                if (!columnData.isDraw()) {
                    continue;
                }
                List<Double> datas = columnData.getChartYDataList();
                if (datas == null || datas.size() == 0) {
                    throw new ChartException("Please set up Column data");
                }
                scaleData.rowSize = datas.size();
                if (datas.size() != scaleData.rowSize) {
                    throw new ChartException("Column rows data inconsistency");
                }
                double value = datas.get(j);
                if (value < 0) {
                    throw new ChartException("The value cannot be <0");
                }
                maxValue += value;
            }
            if (!scaleData.isLeftHasValue) {
                scaleData.maxLeftValue = maxValue;
                scaleData.isLeftHasValue = true;
            } else {
                scaleData.maxLeftValue = Math.max(maxValue, scaleData.maxLeftValue);
            }
        }
            scaleData.maxLeftValue = setMaxMinValue(scaleData.maxLeftValue, 0)[0];
            return true;
        }


        @Override
        public double[] setMaxMinValue ( double maxValue, double minValue){
            double dis = Math.abs(maxValue - minValue);
            maxValue = maxValue + dis * 0.5;
            return new double[]{maxValue, minValue};
        }


    public FontStyle getScaleStyle() {
        return scaleStyle;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public LineStyle getGridStyle() {
        return gridStyle;
    }

    public void setRotateHelper(RotateHelper rotateHelper) {
        this.rotateHelper = rotateHelper;
    }



    public void setScaleFormat(IFormat<Double> scaleFormat) {
        this.scaleFormat = scaleFormat;
    }

    public void setShowScale(boolean showScale) {
        isShowScale = showScale;
    }
}
