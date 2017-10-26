package com.daivd.chart.provider.radar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.format.IFormat;
import com.daivd.chart.data.BarData;
import com.daivd.chart.data.RadarData;
import com.daivd.chart.data.ScaleData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.exception.ChartException;
import com.daivd.chart.matrix.RotateHelper;
import com.daivd.chart.provider.BaseProvider;

import java.util.List;


/**
 * Created by huang on 2017/10/9.
 *雷达图内容绘制
 */

public class RadarProvider extends BaseProvider<RadarData> {

    private int centerRadius;
    private RotateHelper rotateHelper;
    private FontStyle scaleStyle = new FontStyle();
    private LineStyle lineStyle = new LineStyle();
    protected LineStyle gridStyle = new LineStyle(); //网格样式
    private int textHeight;
    private boolean isShowScale;
    private IFormat<Double> scaleFormat;

    @Override
    protected void matrixRectStart(Canvas canvas, Rect rect) {
        super.matrixRectStart(canvas, rect);
        if(rotateHelper != null && rotateHelper.isRotate()){
            canvas.rotate((float) rotateHelper.getStartAngle(),rect.centerX(),rect.centerY());
        }
    }

    @Override
    protected void drawProvider(Canvas canvas, Rect zoomRect, Rect rect, Paint paint) {
        int w = zoomRect.width();
        int h = zoomRect.height();
        int maxRadius = Math.min(w/2, h/2);
        textHeight = (int) (paint.measureText("1",0,1));
        scaleStyle.fillPaint(paint);
        String oneXData = chartData.getCharXDataList().get(0);
        int x = maxRadius / 10 + textHeight * oneXData.length();
        centerRadius = maxRadius - x;
        if(rotateHelper != null){
            rotateHelper.setOriginRect(rect);
            rotateHelper.setRadius(centerRadius);
        }
        drawRadarBorder(canvas,zoomRect,paint);
        drawRadarLines(canvas,zoomRect,paint);
        drawLinePath(canvas,zoomRect,paint);
        drawQuadrantText(canvas,zoomRect,paint);
    }

    private void drawLinePath(Canvas canvas,Rect zoomRect,Paint paint){
        List<String> charXDataList = chartData.getCharXDataList();
        int count = charXDataList.size();
        ScaleData scaleData = chartData.getScaleData();
        List<RadarData>  columnDataList = chartData.getColumnDataList();
        double scaleLength = scaleData.getTotalScaleLength(IAxis.AxisDirection.LEFT);
        float angle = (float) (Math.PI*2/count);
        Path path = new Path();
        lineStyle.fillPaint(paint);
        for(int i = 0;i <columnDataList.size();i++){
            BarData columnData = columnDataList.get(i);
            if(columnData.isDraw()) {
                paint.setColor(columnData.getColor());
                path.reset();
                for (int j = 0; j < columnData.getChartYDataList().size(); j++) {
                    double value = columnData.getChartYDataList().get(j);
                    float curR = getAnimValue((float) ( value * centerRadius / scaleLength));
                    if (j == 0) {
                        path.moveTo(zoomRect.centerX() + curR, zoomRect.centerY());
                    } else {
                        //根据半径，计算出蜘蛛丝上每个点的坐标
                        float x = (float) (zoomRect.centerX() + curR * Math.cos(angle * j));
                        float y = (float) (zoomRect.centerY() + curR * Math.sin(angle * j));
                        path.lineTo(x, y);
                    }
                }
                path.close();
                canvas.drawPath(path, paint);
            }
        }
    }

    private void drawRadarBorder(Canvas canvas,Rect zoomRect,Paint paint){
        List<String> charXDataList = chartData.getCharXDataList();
        int count = charXDataList.size();
        float angle = (float) (Math.PI*2/count);
        ScaleData scaleData = chartData.getScaleData();
        List<Double> scaleList= scaleData.getScaleList(IAxis.AxisDirection.LEFT);
        double maxScale = scaleData.getMaxScaleValue(IAxis.AxisDirection.LEFT);
        Path path = new Path();
        for(int i = 0;i < scaleList.size();i++){
            path.reset();
            double value = scaleList.get(i);
            float curR = (float)(value*centerRadius/maxScale);
            for(int j = 0; j <count;j++){
                float y;
                float x;
                if(j==0){
                    x = zoomRect.centerX()+curR;
                    y =  zoomRect.centerY();
                    path.moveTo(x,y);
                }else{
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                   x= (float) (zoomRect.centerX()+curR*Math.cos(angle*j));
                   y =  (float) (zoomRect.centerY()+curR*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            gridStyle.fillPaint(paint);
            path.close();
            canvas.drawPath(path,paint);
            scaleStyle.fillPaint(paint);
            int textHeight = (int) paint.measureText("1",0,1);
            if(isShowScale) {
                String valueStr = getFormatValue(value);
                canvas.drawText(valueStr, zoomRect.centerX() - textHeight*valueStr.length()/2 , (float)( zoomRect.centerY() - curR*Math.sin(Math.PI/3)), paint);
            }
        }
    }

    private String getFormatValue(double value){
        return scaleFormat !=null ? scaleFormat.format(value):String.valueOf(value);
    }

    /**
     * 绘制象限文字
     * @param canvas
     */
    private void drawQuadrantText(Canvas canvas,Rect zoomRect,Paint paint){
        scaleStyle.fillPaint(paint);
        List<String> charXDataList = chartData.getCharXDataList();
        int count = charXDataList.size();
        float angle = (float) (Math.PI*2/count);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            String xData = charXDataList.get(i);
            float x = (float) (zoomRect.centerX()+(centerRadius+fontHeight/2)*Math.cos(angle*i));
            float y = (float) (zoomRect.centerY()+(centerRadius+fontHeight/2)*Math.sin(angle*i));
            if(angle*i>=0&&angle*i<=Math.PI/2){//第4象限
                canvas.drawText(xData, x,y,paint);
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){//第3象限
                canvas.drawText(xData, x,y,paint);
            }else if(angle*i>Math.PI/2&&angle*i<=Math.PI){//第2象限
                float dis = paint.measureText(xData);//文本长度
                canvas.drawText(xData, x-dis,y,paint);
            }else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){//第1象限
                float dis = paint.measureText(xData);//文本长度
                canvas.drawText(xData, x-dis,y,paint);
            }
        }
    }

    /**
     * 绘制直线
     */
    private void drawRadarLines(Canvas canvas,Rect zoomRect,Paint paint){
        gridStyle.fillPaint(paint);
        Path path = new Path();
        int count = chartData.getCharXDataList().size();
        float angle = (float) (Math.PI*2/count);
        for(int i=0;i<count;i++){
            path.reset();
            path.moveTo(zoomRect.centerX(), zoomRect.centerY());
            float x = (float) (zoomRect.centerX()+centerRadius*Math.cos(angle*i));
            float y = (float) (zoomRect.centerY()+centerRadius*Math.sin(angle*i));
            path.lineTo(x, y);
            canvas.drawPath(path, paint);
        }
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




    @Override
    public boolean calculationChild( ChartData<RadarData> chartData) {
        this.chartData = chartData;
        ScaleData scaleData =this.chartData.getScaleData();
        List<RadarData> columnDatas  =  chartData.getColumnDataList();
        if(columnDatas == null || columnDatas.size() == 0){
            return  false;
        }
        int columnSize = columnDatas.size();
        for(int i = 0 ; i <columnSize; i++){
            BarData columnData = columnDatas.get(i);
            if(!columnData.isDraw()){
                continue;
            }
            List<Double> datas = columnData.getChartYDataList();
            if(datas == null || datas.size() == 0){
                throw new ChartException("Please set up Column data");
            }
            scaleData.rowSize = datas.size();
            if(datas.size() != scaleData.rowSize){
                throw new ChartException("Column rows data inconsistency");
            }
            double[] scale = getColumnScale(datas);
            scale = setMaxMinValue(scale[0],scale[1]);
            if(columnData.getDirection() == IAxis.AxisDirection.LEFT){
                if(!scaleData.isLeftHasValue){
                    scaleData.maxLeftValue = scale[0];
                    scaleData.minLeftValue = scale[1];
                    scaleData.isLeftHasValue = true;
                }else{
                    scaleData.maxLeftValue = Math.max( scaleData.maxLeftValue,scale[0]);
                    scaleData.minLeftValue =  Math.min( scaleData.minLeftValue,scale[1]);
                }

            }else{
                if(!scaleData.isRightHasValue){
                    scaleData.maxRightValue = scale[0];
                    scaleData.minRightValue= scale[1];
                    scaleData.isRightHasValue = true;
                }else{
                    scaleData.maxRightValue = Math.max(scaleData.maxRightValue,scale[0]);
                    scaleData.minRightValue =  Math.min(scaleData.minRightValue,scale[1]);
                }
            }
        }
        if(chartData.getScaleData().rowSize == 0){
            return false;
        }
        return true;



    }

    @Override
    public double[] setMaxMinValue(double maxValue, double minValue) {
        double dis = Math.abs(maxValue -minValue);
        maxValue = maxValue + dis*0.2;
        if(minValue >0){
            minValue = 0;
        }else{
            minValue = minValue - dis*0.2;
        }
        return new double[]{maxValue,minValue};
    }

    private double[] getColumnScale(List<Double> values) {
        double maxValue = 0;
        double minValue =0;
        int size = values.size();
        for(int j= 0;j < size;j++) {
            double d = values.get(j) ;
            if(j == 0){
                maxValue = d;
                minValue = d;
            }
            if (maxValue < d){
                maxValue = d;
            }else if(minValue >d){
                minValue = d;
            }
        }
        return new double[] {maxValue,minValue};
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
