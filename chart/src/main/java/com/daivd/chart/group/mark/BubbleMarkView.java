package com.daivd.chart.group.mark;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.group.tip.SingleLineBubbleTip;
import com.daivd.chart.data.LineData;
import com.daivd.chart.utils.DensityUtils;
import com.david.chart.R;

/**
 * Created by huang on 2017/9/28.
 */

public  class BubbleMarkView<C extends LineData> implements IMark<C> {

   private SingleLineBubbleTip<String> bubbleTip;
    private Paint paint;

    public BubbleMarkView(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtils.sp2px(context,13));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        bubbleTip = new SingleLineBubbleTip<String>(context,R.mipmap.round_rect,R.mipmap.triangle,paint){

            @Override
            public boolean isShowTip(String s,int position) {
                return true;
            }

            @Override
            public String format(String s, int position) {
                return s;
            }
        };
    }



    public Paint getPaint() {
        return paint;
    }

    @Override
    public void drawMark(Canvas canvas, float x, float y, Rect rect, String content, LineData data, int position) {

        String text = content + data.getChartYDataList().get(position) + data.getUnit();
        bubbleTip.drawTip(canvas, x, y, rect, text,position);
    }
}
