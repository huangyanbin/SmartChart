package com.daivd.chart.provider.component.tip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.utils.DensityUtils;

/**
 * Created by huang on 2017/10/20.
 * 气泡提示
 */

public abstract class MultiLineBubbleTip<C>  extends BaseBubbleTip<C,String[]>{

    private int lineSpacing;

    public MultiLineBubbleTip(Context context, int backgroundDrawableID, int triangleDrawableID, Paint paint) {
        super(context, backgroundDrawableID, triangleDrawableID, paint);
        lineSpacing = DensityUtils.dp2px(context,3);
    }

    @Override
    public int getTextHeight(String[] content) {
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();
        int textHeight= (int) (fontMetrics.bottom - fontMetrics.top);
        return  (textHeight +lineSpacing)*content.length - lineSpacing;
    }

    @Override
    public int getTextWidth(String[] content) {
        int maxLength = 0;
        for(int i = 0;i < content.length;i++) {
            int length = (int) getPaint().measureText(content[i]);
            if (length > maxLength) {
               maxLength = length;
            }
        }
        return maxLength;
    }

    @Override
    public void drawText(Canvas canvas, Rect tipRect, String[] content, int textWidth, int textHeight, Paint paint) {
        int lineHeight  =textHeight/content.length;
        for(int i = 0;i < content.length;i++) {
            String c = content[i];
            int bottom = tipRect.top+getVerticalPadding()+lineHeight+ (lineHeight)*i - lineSpacing-deviation/2;
            int left = tipRect.centerX()-textWidth/2;
            canvas.drawText(c,left,bottom,paint);

        }
    }

}
