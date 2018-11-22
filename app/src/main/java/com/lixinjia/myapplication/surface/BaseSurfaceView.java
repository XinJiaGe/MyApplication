package com.lixinjia.myapplication.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * 作者：李忻佳.
 * 时间：2016/12/21.
 * 说明：BaseDrawSurfaceView
 */

public abstract class BaseSurfaceView extends SurfaceView implements Callback,Runnable {
    //用于控制SurfaceView
    private SurfaceHolder sfh;
    //声明一个画笔
    protected Paint mPaint;
    //声明一条线程
    private Thread th;
    //声明一个画布
    protected Canvas mCanvas;
    //声明屏幕的宽高
    private int screenW, screenH;
    //设置画布绘图无锯齿
    private PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    /**
     * SurfaceView初始化函数
     */
    protected void init(Context context){
        //实例SurfaceHolder
        sfh = this.getHolder();
        //为SurfaceView添加状态监听
        sfh.addCallback(this);
        //实例一个画笔
        mPaint = new Paint();
        //设置画笔颜色为白色
        mPaint.setColor(Color.WHITE);
        //设置焦点
        setFocusable(true);
    }

    /**
     * 视图创建
     */
    public abstract void created();
    /**
     * 游戏绘图内容
     */
    public abstract void myDraw();
    /**
     * 游戏逻辑
     */
    public abstract void logic();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long start = System.currentTimeMillis();
            myDraws();
            logic();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 游戏绘图
     */
    public void myDraws() {
        try {
            mCanvas = sfh.lockCanvas();
            if (mCanvas != null) {
                //----设置画布绘图无锯齿
                mCanvas.setDrawFilter(pfd);
                mCanvas.drawColor(Color.WHITE);
                myDraw();
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (mCanvas != null)
                sfh.unlockCanvasAndPost(mCanvas);
        }
    }
    /**
     * 触屏事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event.getX(),event.getY());
                break;
        }
        return true;
    }

    /**
     * 按键事件监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击按下事件
     * @param rawX
     * @param rawY
     */
    protected void onTouchDown(float rawX, float rawY){
    }

    /**
     * 点击按下移动事件
     * @param rawX
     * @param rawY
     */
    protected void onTouchMove(float rawX, float rawY){
    }
    /**
     * 点击抬起事件
     * @param rawX
     * @param rawY
     */
    protected void onTouchUp(float rawX, float rawY){
    }
    /**
     * SurfaceView视图创建，响应此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        //实例线程
        th = new Thread(this);
        //启动线程
        th.start();
        created();
    }
    /**
     * SurfaceView视图状态发生改变，响应此函数
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }
    /**
     * SurfaceView视图消亡时，响应此函数
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }


    public BaseSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public int getScreenW() {
        return screenW;
    }

    public void setScreenW(int screenW) {
        this.screenW = screenW;
    }

    public int getScreenH() {
        return screenH;
    }

    public void setScreenH(int screenH) {
        this.screenH = screenH;
    }

    public PaintFlagsDrawFilter getPfd() {
        return pfd;
    }

    public void setPfd(PaintFlagsDrawFilter pfd) {
        this.pfd = pfd;
    }

    public SurfaceHolder getSfh() {
        return sfh;
    }

    public void setSfh(SurfaceHolder sfh) {
        this.sfh = sfh;
    }

    public void stop(){

    }
}
