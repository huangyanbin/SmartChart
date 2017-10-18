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
        items.add(new MainItem(LineChartActivity.class,"线性图"));
        items.add(new MainItem(ScatterChartActivity.class,"散点图"));
        items.add(new MainItem(AreaChartActivity.class,"面积图"));
        items.add(new MainItem(BarChartActivity.class,"柱状图"));
        items.add(new MainItem(Bar3DChartActivity.class,"3D柱状图"));
        items.add(new MainItem(PieChartActivity.class,"饼图"));
        items.add(new MainItem(RadarChartActivity.class,"雷达图"));
        items.add(new MainItem(RoseChartActivity.class,"玫瑰图"));
        items.add(new MainItem(ZoomChartListActivity.class,"缩放图表List(解决手势冲突)"));
        items.add(new MainItem(RotateChartListActivity.class,"旋转图表List(解决手势冲突)"));
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
