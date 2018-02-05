package com.bin.david.smartchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bin.david.smartchart.adapter.ItemAdapter;
import com.bin.david.smartchart.adapter.RotateChartListAdapter;
import com.bin.david.smartchart.bean.MainItem;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<MainItem> items = new ArrayList<>();
        items.add(new MainItem(RotateActivity.class,"Rotate Test"));
        items.add(new MainItem(LineChartActivity.class,"Line chart"));
        items.add(new MainItem(ScatterChartActivity.class,"Scatter chart"));
        items.add(new MainItem(AreaChartActivity.class,"Area chart"));
        items.add(new MainItem(BarChartActivity.class,"Bar chart"));
        items.add(new MainItem(Bar3DChartActivity.class,"3D bar chart"));
        items.add(new MainItem(PieChartActivity.class,"pie chart"));
        items.add(new MainItem(Pie3DChartActivity.class,"3D pie chart"));
        items.add(new MainItem(RadarChartActivity.class,"Radar chart"));
        items.add(new MainItem(RoseChartActivity.class,"Rose chart"));
        items.add(new MainItem(BarLineChartActivity.class,"Bar line chart"));
       // items.add(new MainItem(TestChartActivity.class,"Custom line chart1"));
        items.add(new MainItem(CustomLineChartActivity.class,"Custom line chart2"));
        items.add(new MainItem(ZoomChartListActivity.class,"scale chart List(Solving gesture conflict)"));
        items.add(new MainItem(RotateChartListActivity.class,"rotate chart List(Solving gesture conflict)"));
        itemAdapter = new ItemAdapter(items);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.openLoadAnimation();
        itemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               MainItem mainItem = (MainItem) adapter.getData().get(position);
                Intent i = new Intent(MainActivity.this,mainItem.clazz);
                startActivity(i);
            }
        });
    }



}
