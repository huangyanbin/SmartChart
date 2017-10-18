package com.bin.david.smartchart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bin.david.smartchart.adapter.ZoomChartListAdapter;

import java.util.ArrayList;

/**
 * Created by huang on 2017/10/18.
 */

public class ZoomChartListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ZoomChartListAdapter itemAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> items = new ArrayList<>();
        for(int i = 0;i <100;i++){
            items.add(i+"");
        }
        itemAdapter = new ZoomChartListAdapter(items);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.openLoadAnimation();

    }
}
