package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 作者：李忻佳.
 * 时间：2016/12/20.
 * 说明：饼图
 */

public class PieChartView extends BaseDrawCakeView {
    /**
     * 最大值
     */
    private float maxValue = 0;
    /**
     * 半径
     */
    private int radius = 300;
    /**
     * 环宽
     */
    private int ringWidth = 250;
    /**
     * 环之间距离间隔
     */
    private int ringDistance = 0;
    /**
     * 点击时环宽
     */
    private int clickRingWidth = 300;
    /**
     * 起始角度
     */
    private float startAngle = 0;
    /**
     * 字体颜色
     */
    private int textColor = Color.WHITE;
    /**
     * 字体大小
     */
    private int textSize = 40;
    /**
     * 是否绘制title右边区域
     */
    private boolean titleRight = false;
    /**
     * 是否绘制数据描述
     */
    private boolean dataText = true;
    /**
     * 设置title右边字体的大小
     */
    private int titleRightTextSize = 45;
    /**
     * 设置title右边字体的颜色
     */
    private int titleRightTextColor = Color.GRAY;
    /**
     * 设置title右边离屏幕右边距离
     */
    private int titleRightRightDistance = 30;
    /**
     * 设置title右边字体离圆的距离
     */
    private int titleRightTextRadiusDistance = 30;
    /**
     * 左右缩小多少距离
     */
    private int leftRightDistace = 20;
    /**
     * 边距
     */
    private int panding = 50;
    /**
     * 是否点击中圆环
     */
    private Boolean[] clicks;
    /**
     * 点击中圆环时增加的宽度
     */
    private int clickWidth = 20;
    /**
     * 是否可以点击
     */
    private boolean isClick = false;
    private String[] details;
    private int x, y;
    private float downX, downY;
    private String[] mData;
    private float[] dataAngle;
    private int[] mDataColor;
    private String[] mDataText;
    private Region[] mRegion;
    private float left, right, top, bottom;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        if (getOriginX() > getOriginY()) {
            radius = getOriginY() - adaptation.setCanvasAdaptation(leftRightDistace) - getTitleHeights() / 2;
        } else {
            radius = getOriginX() - adaptation.setCanvasAdaptation(leftRightDistace) - getTitleHeights() / 2;
        }
        x = getOriginX();
        y = getOriginY() + getTitleHeights() / 2;
        if (mData != null) {
            startAngle = 0;
            mRegion = new Region[mData.length];
            // 设置个新的长方形，扫描测量
            RectF oval = new RectF(x - radius + adaptation.setCanvasAdaptation(panding), y - radius + adaptation.setCanvasAdaptation(panding),
                    x + radius - adaptation.setCanvasAdaptation(panding), y + radius - adaptation.setCanvasAdaptation(panding));
            for (int i = 0; i < mData.length; i++) {
                if (clicks != null && clicks[i] != null && clicks[i]) {
                    left = oval.left;
                    right = oval.right;
                    top = oval.top;
                    bottom = oval.bottom;

                    if (mDataColor != null) {
                        if (i < mDataColor.length) {
                            mPaint.setColor(mDataColor[i]);
                        }
                    }
                    mCanvas.drawArc(new RectF(left - adaptation.setCanvasAdaptation(clickWidth), top - adaptation.setCanvasAdaptation(clickWidth),
                                    right + adaptation.setCanvasAdaptation(clickWidth), bottom + adaptation.setCanvasAdaptation(clickWidth)), startAngle,
                            dataAngle[i] - adaptation.setCanvasAdaptation(ringDistance), true, mPaint);

                    mPaint.setColor(Color.WHITE);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(5);
                    canvas.drawArc(new RectF(left - adaptation.setCanvasAdaptation(clickWidth - 5), top - adaptation.setCanvasAdaptation(clickWidth - 5),
                                    right + adaptation.setCanvasAdaptation(clickWidth - 5), bottom + adaptation.setCanvasAdaptation(clickWidth - 5)),
                            startAngle, dataAngle[i] - adaptation.setCanvasAdaptation(ringDistance), false, mPaint);
                    canvas.drawArc(new RectF(x - (radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding) + 5),
                                    y - (radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding) + 5),
                                    x + (radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding) + 5),
                                    y + (radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding) + 5)),
                            startAngle, dataAngle[i] - adaptation.setCanvasAdaptation(ringDistance), false, mPaint);
                } else {
                    mPaint.setStyle(Paint.Style.FILL);
                    if (mDataColor != null) {
                        if (i < mDataColor.length) {
                            mPaint.setColor(mDataColor[i]);
                        }
                    }
                    // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
                    mCanvas.drawArc(oval, startAngle, dataAngle[i] - adaptation.setCanvasAdaptation(ringDistance), true, mPaint);
                }
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextAlign(Paint.Align.CENTER);
                mPaint.setTextSize(adaptation.setCanvasAdaptation(textSize));
                if (Float.parseFloat(mData[i]) > 10 && dataText) {
                    String text = null;
                    try {
                        text = (Float.parseFloat(mData[i]) == Integer.parseInt(mData[i]) ? Integer.parseInt(mData[i]) : mData[i]) + "%";
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        text = mData[i] + "%";
                    }
                    setCircleCoordinates(text, x, y, startAngle - 90 + dataAngle[i] / 2, radius - adaptation.setCanvasAdaptation(ringWidth) / 2, mCanvas, mPaint);
                }
                Region region = new Region();
                Path ovalPath = new Path();
                ovalPath.moveTo(x, y);
                ovalPath.lineTo((float) getCircleCoordinatesX(x, sss(startAngle), radius - adaptation.setCanvasAdaptation(panding)),
                        (float) getCircleCoordinatesY(y, sss(startAngle), radius - adaptation.setCanvasAdaptation(panding)));
                ovalPath.addArc(oval, startAngle, dataAngle[i]);
                ovalPath.lineTo(x, y);
                Log.e("onTouchDown", "x:" + x + " y:" + y + " tox:" + getCircleCoordinatesX(x, startAngle, radius) + " toy:" + getCircleCoordinatesY(y, startAngle, radius));
                RectF r = new RectF();
                ovalPath.computeBounds(r, true);
                region.setPath(ovalPath, new Region((int) r.left, (int) r.top, (int) r.right, (int) r.bottom));
                mRegion[i] = region;
                startAngle += dataAngle[i];
            }
            //画中间白色的圆
            mPaint.setColor(Color.WHITE);
            mCanvas.drawCircle(x, y, radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding), mPaint);// 小圆

            if (mDataText != null) {
                mPaint.setTextAlign(Paint.Align.RIGHT);
                int titleRightRightDistanceStart = getWidth() - adaptation.setCanvasAdaptation(titleRightRightDistance);
                for (int i = 0; i < mDataText.length; i++) {
                    mPaint.setColor(titleRightTextColor);
                    mCanvas.drawText(mDataText[i], titleRightRightDistanceStart, getTitleHeights() / 2 + adaptation.setCanvasAdaptation(10), mPaint);
                    titleRightRightDistanceStart -= getTextWH(mDataText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextRadiusDistance);
                    if (mDataColor != null) {
                        if (i < mDataColor.length) {
                            mPaint.setColor(mDataColor[i]);
                        }
                    }
                    mCanvas.drawCircle(titleRightRightDistanceStart, getTitleHeights() / 2, adaptation.setCanvasAdaptation(15), mPaint);// 圆
                    titleRightRightDistanceStart -= adaptation.setCanvasAdaptation(20) + adaptation.setCanvasAdaptation(titleRightTextRadiusDistance);
                }
            }
            //显示弹窗
            if (clicks != null && details != null) {
                for (int i = 0; i < clicks.length; i++) {
                    if (clicks[i] != null && clicks[i] && clicks.length == details.length) {
                        mPaint.setColor(Color.BLACK);
                        mPaint.setAlpha(180);
                        mPaint.setStyle(Paint.Style.FILL);
                        mPaint.setStrokeWidth(1);
                        Rect textWH = getTextWH(details[i], mPaint);
                        mPaint.setStrokeWidth(5);
                        float w = textWH.width() + adaptation.setCanvasAdaptation(100);
                        float h = textWH.height() + adaptation.setCanvasAdaptation(60);
                        RectF rectF = new RectF(downX - w / 2, downY - h / 2, downX + w / 2, downY + h / 2);
                        mCanvas.drawRoundRect(rectF, 20, 20, mPaint);
                        mPaint.setStrokeWidth(1);
                        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
                        mPaint.setColor(Color.WHITE);
                        canvas.drawText(details[i], rectF.centerX(), baseline, mPaint);
                        break;
                    }
                }
            }
        }
    }

    private float sss(float angle) {
        if (angle >= 0 && angle <= 90) {
            return 90 - angle;
        } else {
            return 450 - angle;
        }
    }

    @Override
    protected void onTouchDown(float downX, float downY) {
        this.downX = downX;
        this.downY = downY;
        if (isClick) {
            for (int i = 0; i < mRegion.length; i++) {
                Region region = mRegion[i];
                if (region != null) {
                    boolean b = region.contains((int) downX, (int) downY);
                    if (b) {
                        boolean xiao = isClickCircularView(downX, downY, x, y, radius - adaptation.setCanvasAdaptation(ringWidth) - adaptation.setCanvasAdaptation(panding));
                        if (!xiao) {
                            clicks = new Boolean[mData.length];
                            for (int i1 = 0; i1 < clicks.length; i1++) {
                                if (i == i1) {
                                    clicks[i1] = true;
                                } else {
                                    clicks[i1] = false;
                                }
                            }
                            invalidate();
                            Log.e("onTouchDown", "onTouchEvent: b: " + b + " x: " + downX + "  y: " + downY + "  " + mData[i]);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(String[] data) {
        if (data != null) {
            mData = data;
            dataAngle = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                maxValue += Float.parseFloat(data[i]);
            }
            for (int i = 0; i < data.length; i++) {
                dataAngle[i] = (360 * Float.parseFloat(data[i]) / maxValue);
            }

        }
    }

    /**
     * 设置数据颜色
     *
     * @param color
     */
    public void setDataColor(int[] color) {
        mDataColor = color;
    }

    /**
     * 设置数据描述
     *
     * @param text
     */
    public void setDataText(String[] text) {
        mDataText = text;
    }

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public boolean isTitleRight() {
        return titleRight;
    }

    public void setTitleRight(boolean titleRight) {
        this.titleRight = titleRight;
    }

    public int getTitleRightTextSize() {
        return titleRightTextSize;
    }

    public void setTitleRightTextSize(int titleRightTextSize) {
        this.titleRightTextSize = titleRightTextSize;
    }

    public int getTitleRightTextColor() {
        return titleRightTextColor;
    }

    public void setTitleRightTextColor(int titleRightTextColor) {
        this.titleRightTextColor = titleRightTextColor;
    }

    public int getTitleRightRightDistance() {
        return titleRightRightDistance;
    }

    public void setTitleRightRightDistance(int titleRightRightDistance) {
        this.titleRightRightDistance = titleRightRightDistance;
    }

    public int getTitleRightTextRadiusDistance() {
        return titleRightTextRadiusDistance;
    }

    public void setTitleRightTextRadiusDistance(int titleRightTextRadiusDistance) {
        this.titleRightTextRadiusDistance = titleRightTextRadiusDistance;
    }

    public int getLeftRightDistace() {
        return leftRightDistace;
    }

    public void setLeftRightDistace(int leftRightDistace) {
        this.leftRightDistace = leftRightDistace;
    }

    public boolean isDataText() {
        return dataText;
    }

    public void setDataText(boolean dataText) {
        this.dataText = dataText;
    }

    public int getClickRingWidth() {
        return clickRingWidth;
    }

    public void setClickRingWidth(int clickRingWidth) {
        this.clickRingWidth = clickRingWidth;
    }

    public int getClickWidth() {
        return clickWidth;
    }

    public void setClickWidth(int clickWidth) {
        this.clickWidth = clickWidth;
    }

    public int getRingDistance() {
        return ringDistance;
    }

    public void setRingDistance(int ringDistance) {
        this.ringDistance = ringDistance;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String[] getDetails() {
        return details;
    }

    /**
     * 点击后弹出描述
     */
    public void setDetails(String[] details) {
        this.details = details;
    }
}
