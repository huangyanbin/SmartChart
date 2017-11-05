package com.daivd.chart.data;

import android.graphics.Bitmap;

/**
 * Created by huang on 2017/10/30.
 * 保存图片Option
 */

public class PicOption {

    private String fileName;
    private String subFolderPath ="chart";
    private String fileDescription="chart pic";
    private Bitmap.CompressFormat format= Bitmap.CompressFormat.PNG;
    private int quality = 100;

    public PicOption(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }



    public String getSubFolderPath() {
        return subFolderPath;
    }

    public PicOption setSubFolderPath(String subFolderPath) {
        this.subFolderPath = subFolderPath;
        return this;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public PicOption setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
        return this;
    }

    public Bitmap.CompressFormat getFormat() {
        return format;
    }

    public PicOption setFormat(Bitmap.CompressFormat format) {
        this.format = format;
        return this;
    }

    public int getQuality() {
        return quality;
    }

    public PicOption setQuality(int quality) {
        this.quality = quality;
        return this;
    }
}
