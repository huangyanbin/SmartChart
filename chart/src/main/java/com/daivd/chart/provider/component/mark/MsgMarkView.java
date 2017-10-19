package com.daivd.chart.provider.component.mark;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;

import com.daivd.chart.data.LineData;
import com.daivd.chart.utils.DensityUtils;
import com.david.chart.R;

/**
 * Created by huang on 2017/9/28.
 */

public  class MsgMarkView  extends MarkView{

    private Context context;
    private Rect markRect;
    private Paint paint;
    private int textHeight;
    private int padding = 10;

    public MsgMarkView(Context context) {
        this.context = context;
        markRect = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtils.sp2px(context,13));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        textHeight = (int) paint.measureText("1",0,1);

    }

    @Override
    public void drawMark(float x, float y, String content, LineData data, int position) {
        String text = content+data.getChartYDataList().get(position)+data.getUnit();
        int w = textHeight*text.length();
        int h = textHeight + padding*2;
        Canvas canvas = getCanvas();
        int drawableID = R.drawable.msg;
        markRect.left = (int)x - w;
        markRect.right =(int)x + w;
        markRect.top = (int)y - h*3;
        markRect.bottom =  (int)y;

        if (markRect.top < getRect().top) {
            canvas.save();
            canvas.rotate(180,x,y);
            drawMarkBitmap(markRect,drawableID);
            canvas.restore();
            canvas.drawText(text,x-w/3*2,(int)(y+h*2),paint);
        }else {
            drawMarkBitmap(markRect,drawableID);
            canvas.drawText(text, x - w / 3 * 2, (int) (y - h * 1.5), paint);
        }
    }

    protected void drawMarkBitmap(Rect rect,int drawableID){

        Bitmap bmp_9 = BitmapFactory.decodeResource(context.getResources(),
                drawableID);
        NinePatch np = new NinePatch(bmp_9, bmp_9.getNinePatchChunk(), null);
        np.draw(getCanvas(), rect);
    }
}
