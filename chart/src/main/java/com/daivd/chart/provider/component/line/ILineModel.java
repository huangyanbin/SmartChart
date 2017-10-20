package com.daivd.chart.provider.component.line;

import android.graphics.Path;

import java.util.List;

/**
 * Created by huang on 2017/10/15.
 */

public interface ILineModel {

     Path getLinePath(List<Float> pointX, List<Float> pointY);
}
