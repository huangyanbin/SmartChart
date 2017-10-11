package com.daivd.chart.matrix;

import android.os.Handler;
import android.view.MotionEvent;

/**
 * Created by huang on 2017/10/10.
 */

public class RotateHelper {

    private int mRadius;

    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;
    /**
     * 布局时的开始角度
     */
    private double mStartAngle = 0;


    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;

    private Handler mHandler = new Handler();
    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;
    private  boolean isRotate;

    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    private OnRotateListener listener;

    public RotateHelper(OnRotateListener listener){
       this.listener = listener;
    }


    public boolean dispatchTouchEvent(MotionEvent event) {
        if(!isRotate()){
            return false;
        }
        float x = event.getX();
        float y = event.getY();

        // Log.e("TAG", "x = " + x + " , y = " + y);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                // 如果当前已经在快速滚动
                if (isFling)
                {
                    // 移除快速滚动的回调
                    mHandler.removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                /**
                 * 获得开始的角度
                 */
                float start = getAngle(mLastX, mLastY);
                /**
                 * 获得当前的角度
                 */
                float end = getAngle(x, y);

                // Log.e("TAG", "start = " + start + " , end =" + end);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4)
                {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }
                // 重新布局
                listener.onRotate(mStartAngle);

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:

                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000
                        / (System.currentTimeMillis() - mDownTime);

                // Log.e("TAG", anglePrMillionSecond + " , mTmpAngel = " +
                // mTmpAngle);

                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling)
                {
                    // post一个任务，去自动滚动
                    mHandler.post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE)
                {
                    return true;
                }

                break;
        }
        return true;
    }

    /**
     * 自动滚动的任务
     *
     * @author zhy
     *
     */
    private class AutoFlingRunnable implements Runnable
    {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity)
        {
            this.angelPerSecond = velocity;
        }

        public void run()
        {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20)
            {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 30);
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            mHandler.postDelayed(this, 30);
            // 重新布局
            listener.onRotate(mStartAngle);
        }
    }



    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch)
    {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y)
    {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0)
        {
            return tmpY >= 0 ? 4 : 1;
        } else
        {
            return tmpY >= 0 ? 3 : 2;
        }

    }


    public interface  OnRotateListener{
        void  onRotate(double angle);
    }


    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public double getStartAngle() {
        return mStartAngle;
    }

    public boolean isRotate() {
        return isRotate;
    }

    public void setRotate(boolean rotate) {
        isRotate = rotate;
    }
}
