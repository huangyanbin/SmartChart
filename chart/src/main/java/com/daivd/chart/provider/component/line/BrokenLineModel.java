package com.daivd.chart.provider.component.line;

import android.graphics.Path;

import java.util.List;



public class BrokenLineModel implements ILineModel {



    @Override
    public Path getLinePath(List<Float> pointX, List<Float> pointY) {
        Path path = new Path();
        for(int i = 0; i < pointY.size();i++){
            float x = pointX.get(i);
            float y = pointY.get(i);
            if(i == 0){
                path.moveTo(x,y);
            }else{
                path.lineTo(x,y);
            }
        }
       return path;
    }
}
