package com.bin.david.smartchart.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.bin.david.smartchart.R;

/**
 * Created by huang on 2017/10/24.
 */

public class RotateView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private Rect bitmapRect;
    private Rect rect;
    private Camera camera;
    private int rotateAngle;
    private int rotateX;
    private int rotateY;

    public RotateView(Context context) {
        this(context,null);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();


    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg2);
        bitmapRect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        rect = new Rect();
        camera = new Camera();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0,0,newZ);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect.top = h/4;
        rect.left = w/4;
        rect.right = w -rect.left;
        rect.bottom = h-rect.top;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        camera.save();
        canvas.translate(rect.centerX(),rect.centerY());
        canvas.rotate(-rotateAngle);
        camera.rotateX(-rotateY);
        camera.applyToCanvas(canvas);
        canvas.rotate(rotateAngle);
        canvas.translate(-rect.centerX(),-rect.centerY());

        canvas.save();
        canvas.translate(rect.centerX(),rect.centerY());
        camera.rotateX(-rotateX);
        camera.applyToCanvas(canvas);
        canvas.translate(-rect.centerX(),-rect.centerY());
        canvas.clipRect(rect.left,rect.top,rect.right,rect.bottom-rect.height()/2);
        canvas.drawBitmap(bitmap,bitmapRect,rect,paint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rect.left,rect.bottom-rect.height()/2,rect.right,rect.bottom);
        canvas.drawBitmap(bitmap,bitmapRect,rect,paint);
        canvas.restore();

        camera.restore();
        canvas.restore();


    }

    public int getRotateX() {
        return rotateX;
    }
    @Keep
    public void setRotateX(int rotateX) {
        this.rotateX = rotateX;
        invalidate();
    }

    public int getRotateAngle() {
        return rotateAngle;
    }
    @Keep
    public void setRotateAngle(int rotateAngle) {
        this.rotateAngle = rotateAngle;
        invalidate();
    }

    public int getRotateY() {
        return rotateY;
    }
    @Keep
    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
        invalidate();
    }


}
