package com.daivd.chart.component;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.daivd.chart.data.PicOption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by huang on 2017/10/30.
 * 图片生成
 */

public class PicGeneration<V extends View> {

    public boolean save(V v,PicOption option){
        getChartBitmap(v);
        return saveToGallery(v,option);
    }

    private Bitmap getChartBitmap(V v) {
        // 创建一个bitmap 根据我们自定义view的大小
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getWidth(),
                v.getHeight(), Bitmap.Config.RGB_565);
        // 绑定canvas
        Canvas canvas = new Canvas(returnedBitmap);
        // 获取视图的背景
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            // 如果有就绘制
            bgDrawable.draw(canvas);
        else
            // 没有就绘制白色
            canvas.drawColor(Color.WHITE);
        // 绘制
       v.draw(canvas);
        return returnedBitmap;
    }

    private boolean saveToGallery(V v, PicOption option) {
        String mFilePath;
        // 控制图片质量
        if (option.getQuality() < 0 || option.getQuality() > 100)
            option.setQuality(50);
        long currentTime = System.currentTimeMillis();

        File extBaseDir = Environment.getExternalStorageDirectory();
        File file = new File(extBaseDir.getAbsolutePath() + "/DCIM/" + option.getSubFolderPath());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }

        String mimeType = "";
        String fileName = option.getFileName();
        switch (option.getFormat()) {
            case PNG:
                mimeType = "image/png";
                if (!fileName.endsWith(".png"))
                    fileName += ".png";
                break;
            case WEBP:
                mimeType = "image/webp";
                if (!fileName.endsWith(".webp"))
                    fileName += ".webp";
                break;
            case JPEG:
            default:
                mimeType = "image/jpeg";
                if (!(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")))
                    fileName += ".jpg";
                break;
        }
        mFilePath = file.getAbsolutePath() + "/" + fileName;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(mFilePath);
            Bitmap b = getChartBitmap(v);
            b.compress(option.getFormat(), option.getQuality(), out);

            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

        long size = new File(mFilePath).length();

        ContentValues values = new ContentValues(8);

        // store the details
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, currentTime);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.DESCRIPTION, option.getFileDescription());
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.DATA, mFilePath);
        values.put(MediaStore.Images.Media.SIZE, size);
        return v.getContext().getContentResolver().
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) != null;
    }
}
