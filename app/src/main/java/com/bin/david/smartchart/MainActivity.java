package com.bin.david.smartchart;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daivd.chart.axis.AxisDirection;
import com.daivd.chart.axis.BaseAxis;
import com.daivd.chart.core.ColumnChartView;
import com.daivd.chart.core.LineChartView;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.ColumnData;
import com.daivd.chart.data.style.FontStyle;
import com.daivd.chart.data.style.LineStyle;
import com.daivd.chart.mark.MsgMarkView;
import com.daivd.chart.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.line){
            Intent i = new Intent(this,LineChartActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this,ColumnChartActivity.class);
            startActivity(i);
        }
    }
}
