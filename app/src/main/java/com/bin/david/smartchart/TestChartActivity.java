package com.bin.david.smartchart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.smartchart.helper.DrawHelper;
import com.daivd.chart.core.LineChart;


public class TestChartActivity extends AppCompatActivity {


    LineChart lineChart2;
    LineChart lineChart;
    LineChart lineChart3;
    LineChart lineChart4;
    LineChart lineChart5;
    LineChart lineChart6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart2 = (LineChart) findViewById(R.id.lineChart2);
        lineChart3= (LineChart) findViewById(R.id.lineChart3);
        lineChart4 = (LineChart) findViewById(R.id.lineChart4);
        lineChart5 = (LineChart) findViewById(R.id.lineChart5);
        lineChart6 = (LineChart) findViewById(R.id.lineChart6);
        DrawHelper.drawWeatherChart(this, lineChart);
        DrawHelper.drawFrozenSoilChart(this, lineChart2);
        DrawHelper.drawGroundTempChart(this, lineChart3);
        DrawHelper.drawFactorChart(this, lineChart4);
        DrawHelper.drawSoilChart(this, lineChart5);
        DrawHelper.drawHomeWeatherChart(this,lineChart6);
    }
}
