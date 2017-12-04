package com.bin.david.smartchart.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bin.david.smartchart.R;
import com.daivd.chart.utils.DensityUtils;


/**
 * Created by huang on 2017/11/30.
 */

public class WeatherView extends LinearLayout {

    private int columnWidth;
    private int columnSize;
    private int width;
    private int height;


    public WeatherView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public WeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }



    private void init(Context context, @Nullable AttributeSet attrs){

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.weatherView);
        columnWidth = (int) a.getDimension(R.styleable.weatherView_weatherColumnWidth,
                DensityUtils.dp2px(context,50));
        columnSize = a.getInt(R.styleable.weatherView_weatherColumnCount, 7);
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width= MeasureSpec.getSize(widthMeasureSpec);
        int height = columnWidth *columnSize;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }




}
