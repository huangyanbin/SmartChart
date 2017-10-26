package com.daivd.chart.provider.component.tip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;



/**
 * Created by huang on 2017/10/20.
 * 单行气泡提示
 */

public  abstract  class SingleLineBubbleTip<C> extends BaseBubbleTip<C,String> {

    public SingleLineBubbleTip(Context context, int backgroundDrawableID, int triangleDrawableID, Paint paint){
        super(context,backgroundDrawableID,triangleDrawableID,paint);
    }

    @Override
    public int getTextHeight(String content) {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        return  (int) (fontMetrics.bottom - fontMetrics.top);
    }


    @Override
    public int getTextWidth(String content) {
        return (int) getPaint().measureText(content);
    }

    @Override
    public void drawText(Canvas canvas, Rect tipRect, String content, int textWidth,int textHeight,  Paint paint) {
        canvas.drawText(content,tipRect.centerX()-textWidth/2,tipRect.centerY()+textHeight/2-deviation/2,paint);
    }


}
