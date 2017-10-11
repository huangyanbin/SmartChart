package com.bin.david.smartchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        }else if(v.getId() == R.id.column3D){
            Intent i = new Intent(this,Bar3DChartActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.pie){
            Intent i = new Intent(this,PieChartActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this,BarChartActivity.class);
            startActivity(i);
        }
    }
}
