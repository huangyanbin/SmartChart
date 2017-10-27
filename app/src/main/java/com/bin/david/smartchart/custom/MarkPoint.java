package com.bin.david.smartchart.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.bin.david.smartchart.R;
import com.daivd.chart.provider.component.point.IPoint;

/**
 * Created by huang on 2017/10/26.
 */

public class MarkPoint implements IPoint {
    private Bitmap markBitmap;
    private Bitmap avatorBitmap;
    private Rect bitmapRect;
    private Rect avatorbitmapRect;
    private Rect rect;
    private int height;
    private int width;
    private PorterDuffXfermode xfermode;

    public MarkPoint(Context context,int height){
        markBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.mark);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        avatorBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.avator,options);
        bitmapRect = new Rect(0,0,markBitmap.getWidth(),markBitmap.getHeight());
        avatorbitmapRect = new Rect(0,0,avatorBitmap.getWidth(),avatorBitmap.getHeight());
        this.height = height;
        rect = new Rect();
        this.width = markBitmap.getWidth()*this.height/markBitmap.getHeight();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void drawPoint(Canvas canvas, float x, float y, boolean isShowDefaultColor, Paint paint) {
        rect.top = (int) (y - height);
        rect.bottom = (int) y;
        rect.right = (int) (x + width/2);
        rect.left = (int)(x - width/2);
        int layerId = canvas.saveLayer(rect.left,rect.top,rect.right,rect.bottom , null, Canvas.ALL_SAVE_FLAG);

       canvas.drawBitmap(markBitmap,bitmapRect,rect,paint);
       paint.setXfermode(xfermode);
       rect.bottom = rect.top+rect.width();
       canvas.drawBitmap(avatorBitmap,avatorbitmapRect,rect,paint);
       paint.setXfermode(null);
        canvas.restoreToCount(layerId);//将自己创建的画布Layer绘制到画布默认的Layer
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }


}
