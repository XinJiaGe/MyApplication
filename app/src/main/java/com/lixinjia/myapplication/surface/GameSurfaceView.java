package com.lixinjia.myapplication.surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;

import com.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 时间：2017/5/5
 * 说明：MainSurfaceView
 */

public class GameSurfaceView extends BaseSurfaceView {

    private Bitmap launcher;
    private float btX;
    private float btY;

    public GameSurfaceView(Context context) {
        super(context);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void created() {
        launcher = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onTouchDown(float rawX, float rawY) {
        btX = rawX;
        btY = rawY;
    }

    @Override
    protected void onTouchMove(float rawX, float rawY) {
        btX = rawX;
        btY = rawY;
    }

    @Override
    public void myDraw() {
        //刷屏，画布白色
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawBitmap(launcher,btX-launcher.getWidth()/2,btY-launcher.getHeight()/2,mPaint);
    }

    @Override
    public void logic() {

    }
    public void onDestroy(){
        stop();
    }
}
