package com.daivd.chart.provider.component.mark;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private int padding = 30;
    private Bitmap triangleBitmap;
    private NinePatch ninePatch;

    public MsgMarkView(Context context) {
        this.context = context;
        markRect = new Rect();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(DensityUtils.sp2px(context,13));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        textHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
        triangleBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.triangle));

    }

    @Override
    public void drawMark(Canvas canvas,float x, float y, String content, LineData data, int position) {
        String text = content+data.getChartYDataList().get(position)+data.getUnit();
        int textWidth = (int) paint.measureText(text);
        int w = textWidth +padding*2;
        int h = textHeight + padding*2;
        int drawableID = R.mipmap.round_rect;
        markRect.left = (int)x - w/2;
        markRect.right =(int)x + w/2;
        markRect.bottom =  (int)y-triangleBitmap.getHeight()+5;
        markRect.top = markRect.bottom - h;
        if(markRect.left < getRect().left){
            markRect.left = getRect().left-triangleBitmap.getWidth()/2-10;
            markRect.right = markRect.left+w;
        }else if(markRect.right > getRect().right){
            markRect.right = getRect().right+triangleBitmap.getWidth()/2+10;
            markRect.left = markRect.right-w;
        }
        if (markRect.top < getRect().top) {
            canvas.save();
            canvas.rotate(180,x,y);
            drawMarkBitmap(canvas,markRect,drawableID);
            canvas.drawBitmap(triangleBitmap,x-triangleBitmap.getWidth()/2,
                    y-triangleBitmap.getHeight(),paint);
            canvas.rotate(180,markRect.centerX(),markRect.centerY());
            canvas.drawText(text,markRect.centerX()-textWidth/2,markRect.centerY()+textHeight/2,paint);
            canvas.restore();

        }else {
            drawMarkBitmap(canvas,markRect,drawableID);
           canvas.drawBitmap(triangleBitmap,x-triangleBitmap.getWidth()/2,
                    y-triangleBitmap.getHeight(),paint);
            canvas.drawText(text,markRect.centerX()-textWidth/2,markRect.centerY()+textHeight/2,paint);
        }
    }



    protected void drawMarkBitmap(Canvas canvas,Rect rect,int drawableID){
        if(ninePatch == null) {
            Bitmap bmp_9 = BitmapFactory.decodeResource(context.getResources(),
                    drawableID);
            ninePatch = new NinePatch(bmp_9, bmp_9.getNinePatchChunk(), null);
        }
        ninePatch.draw(canvas, rect);
    }
}
