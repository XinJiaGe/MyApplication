package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.jiage.util.JiaUtil;

/**
 * 作者：李忻佳.
 * 时间：2016/11/17.
 * 说明：柱状图
 */

public class TransverseHistogramView extends BaseDrawView {
    /**
     * 柱子宽度最大数据
     */
    private int xValueMax = 0;
    /**
     * 柱子透明度
     */
    private int columnAlpha = 100;
    /**
     * 柱子的高
     */
    private int columnHeight = 80;
    /**
     * 柱子之间间隔
     */
    private int interval = 50;
    /**
     * 是否绘制柱体上数据text
     */
    private boolean dataText = true;
    /**
     * 柱体上方的数据text的大小
     */
    private int dataTextSize = 30;
    /**
     * 描述
     */
    private String[] XText;
    /**
     * 数据
     */
    private double[] XData;
    /**
     * 柱体颜色
     */
    private String[] colors;
    /**
     * 字体大小
     */
    private int textSize = 50;
    /**
     * 描述离左边距离
     */
    private int textP = 20;
    /**
     * 离右边距距离
     */
    private int rightDistance = 10;
    /**
     * 离左边边距距离
     */
    private int leftDistance = 20;
    /**
     * 单位
     */
    private String company = "";

    private int topProgressively = 5;
    private int widthProgressively = 0;

    public  TransverseHistogramView(Context context) {
        super(context);
    }

    public TransverseHistogramView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransverseHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (XText != null) {
            canvasColumn();
        }
    }

    /**
     * 绘制数据
     */
    private void canvasColumn() {
        topProgressively = 5;
        widthProgressively = mWidth - rightDistance - leftDistance;
        mPaint.setTextSize(adaptation.setCanvasAdaptation(textSize));
        for (int i = 0; i < XText.length; i++) {
            String text = XText[i];
            //绘制柱体
            mPaint.setColor((colors != null && colors.length != XText.length) ? Color.BLACK : Color.parseColor(colors[i]));
            mPaint.setAlpha(columnAlpha);
            mPaint.setStyle(Paint.Style.FILL);//设置填满
            double width = adaptation.setCanvasAdaptation(widthProgressively) * XData[i] / xValueMax;
            Rect rect = new Rect(adaptation.setCanvasAdaptation(leftDistance), adaptation.setCanvasAdaptation(topProgressively),
                    (int) (adaptation.setCanvasAdaptation(leftDistance)+ width),
                    adaptation.setCanvasAdaptation(topProgressively) + adaptation.setCanvasAdaptation(columnHeight));
            mCanvas.drawRect(rect, mPaint);// 长方形
            //绘制边框
            mPaint.setAlpha(255);
            mPaint.setStyle(Paint.Style.STROKE);//设置不填满
            mCanvas.drawRect(rect, mPaint);// 长方形
            //绘制描述
            mPaint.setTextAlign(Paint.Align.LEFT);
            int textW = getTextWH(text, mPaint).width();
            int dataW = getTextWH(XData[i] + company, mPaint).width();
            //获取当前线到baseline线的距离
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float y = (fontMetrics.descent - fontMetrics.ascent);
            Log.e("TransverseHistogramView","y:"+y+"   columnHeight:"+adaptation.setCanvasAdaptation(columnHeight));
            if (width < textW + adaptation.setCanvasAdaptation(textP) + dataW) {
                mCanvas.drawText(text, (float) width+adaptation.setCanvasAdaptation(leftDistance) + adaptation.setCanvasAdaptation(textP),
                        (float) adaptation.setCanvasAdaptation(topProgressively) + y, mPaint);
                //绘制数据
                mCanvas.drawText(JiaUtil.deleteDecimal(XData[i]) + company,
                        (float) width +adaptation.setCanvasAdaptation(leftDistance)+ adaptation.setCanvasAdaptation(textP * 2 )+ textW,
                        (float) adaptation.setCanvasAdaptation(topProgressively )+ y, mPaint);
            } else {
                mCanvas.drawText(text, adaptation.setCanvasAdaptation(textP + leftDistance), (float) adaptation.setCanvasAdaptation(topProgressively) + y, mPaint);
                //绘制数据
                mCanvas.drawText(JiaUtil.deleteDecimal(XData[i]) + company, (float) width + adaptation.setCanvasAdaptation(textP )- dataW,
                        (float) adaptation.setCanvasAdaptation(topProgressively )+ y, mPaint);
            }
            topProgressively += (columnHeight + interval);
        }
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getxValueMax() {
        return xValueMax;
    }

    public void setxValueMax(int xValueMax) {
        this.xValueMax = xValueMax;
    }

    public int getColumnHeight() {
        return columnHeight;
    }

    public void setColumnHeight(int columnHeight) {
        this.columnHeight = columnHeight;
    }

    public boolean isDataText() {
        return dataText;
    }

    public void setDataText(boolean dataText) {
        this.dataText = dataText;
    }

    public int getDataTextSize() {
        return dataTextSize;
    }

    public void setDataTextSize(int dataTextSize) {
        this.dataTextSize = dataTextSize;
    }

    public String[] getXText() {
        return XText;
    }

    public void setXText(String[] XText) {
        this.XText = XText;
    }

    public double[] getXData() {
        return XData;
    }

    public void setXData(String[] XData) {
        double[] data = new double[XData.length];
        for (int i = 0; i < XData.length; i++) {
            data[i] = Double.parseDouble(XData[i]);
        }
        this.XData = data;
    }

    public void setXData(double[] XData) {
        this.XData = XData;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public int getRightDistance() {
        return rightDistance;
    }

    public void setRightDistance(int rightDistance) {
        this.rightDistance = rightDistance;
    }

    public int getLeftDistance() {
        return leftDistance;
    }

    public void setLeftDistance(int leftDistance) {
        this.leftDistance = leftDistance;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getColumnAlpha() {
        return columnAlpha;
    }

    public void setColumnAlpha(int columnAlpha) {
        this.columnAlpha = columnAlpha;
    }
}
