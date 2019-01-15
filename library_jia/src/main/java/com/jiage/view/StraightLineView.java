package com.jiage.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳.
 * 时间：2016/11/21.
 * 说明：折线图
 */

public class StraightLineView extends BaseDrawGridView {
    /**
     * 折线颜色
     */
    private int lineColor = Color.RED;
    /**
     * 折线宽度
     */
    private int lineWidth = 3;
    /**
     * 总目标值颜色
     */
    private int totalTargeValueColor = Color.GRAY;
    /**
     * 总目标值宽度
     */
    private int totalTargeValueWidth = 3;
    /**
     * 阶段目标值颜色
     */
    private int targeValueColor = Color.GRAY;
    /**
     * 阶段目标值宽度
     */
    private int targeValueWidth = 3;
    /**
     * 圆环颜色
     */
    private int ringColor = Color.GRAY;
    /**
     * 圆环半径
     */
    private int ringRadius = 17;
    /**
     * 圆环宽
     */
    private int ringWidth = 5;
    /**
     * title右边字体描述里右边距离
     */
    private int titleRightTextRightDistance = 20;
    /**
     * title右边字体描述离小折线距离
     */
    private int titleRightTextlineDistance = 10;
    /**
     * title右边小折线一小折的长度
     */
    private int titleRightLineMin = 20;
    /**
     * title右边字体之间相隔距离
     */
    private int titleRightTextBetweenDistance = 20;
    /**
     * title右边字体的颜色
     */
    private int titleRightTextColor = Color.GRAY;
    /**
     * title右边字体的大小
     */
    private int titleRightTextSize = 50;
    /**
     * 是否绘制右上角
     */
    private boolean isTitleRight = false;
    /**
     * 是否绘制圆环
     */
    private boolean isRing = false;
    /**
     * title右边小长方形的宽
     */
    private int titleRightRectangleWidth = 60;
    /**
     * title右边小长方形的颜色
     */
    private int titleRightRectangleWithinColor = Color.BLACK;
    /**
     * 是否动态加载
     */
    private boolean isDynamic = true;
    /**
     * 动态加载毫秒数
     */
    private int dynamicTime = 50;
    /**
     * 是否绘制柱体上方的数据text
     */
    private boolean dataText = false;
    /**
     * 柱体上方的数据text的颜色
     */
    private int dataTextColor = Color.parseColor("#000000");
    /**
     * 柱体上方的数据text的大小
     */
    private int dataTextSize = 30;
    /**
     * 是否显示圆与线之间的距离
     */
    private boolean isCircularLineDistance = false;
    /**
     * 圆与线之间的距离
     */
    private int circularLineDistance = 5;
    /**
     * 是否可以点击查看数据
     */
    private boolean isClick = false;
    /**
     * 点击后圆环增加的宽度
     */
    private int clickWidth = 10;

    /**
     * 是否可以滑动选择
     */
    private boolean isSlideSelect = false;
    /**
     * 滑动选择的时候线条的颜色
     */
    private int slideSelectYColor = Color.BLACK;
    /**
     * 滑动选择的时候线条的宽度
     */
    private int slideSelectYWidth = 1;
    /**
     * 是否显示最后一条数据的浮框
     */
    private boolean isTheLastDay = false;
    /**
     * 最后一条数据的浮框的背景颜色
     */
    private int theLastDayBgColor = Color.RED;
    /**
     * 最后一条数据的浮框的文字颜色
     */
    private int theLastDayTextColor = Color.WHITE;
    /**
     * 最后一条数据的浮框的文字大小
     */
    private int theLastDayTextSieze = 50;


    private String[] XText;
    private int[] DataXY;
    private Boolean[] IsBrokenX;
    private List<int[]> Xdata;
    private int[] XdataColor;
    private int[] totalTargetValue;
    private int[] totalTargetValueColor;
    private List<int[]> targetValue;
    private int[] targetValueColor;
    private int[] XDataRingColor;
    private List<int[]> gradientColor;
    private int[] gradientColorAlpha;
    private String[] dataTitleRightText;
    private String[] setTargetValueTitleRightText;
    private int titleRightXDistance;
    private String[] totalTargetTitleRightText;
    private int[] titleRightRectangleWithinColorList;
    private ArrayList<int[]> XdataYEndList;
    private ArrayList<double[]> XdataYEndListDouble;
    private int dataSize = 1;
    private List<double[]> XdataDouble;
    private double[] totalTargetValueDouble;
    private List<double[]> targetValueDouble;
    private int clicki = -1,clicki1 = -1;
    private int slideSelectIndex = -1;
    private int slideSelectIndex1 = -1;
    private List<String[]> XdataDetails;
    private float downX,downY;
    private onStraightLineViewListener mLineListener;
    private boolean touch = false;

    public StraightLineView(Context context) {
        super(context);
    }

    public StraightLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StraightLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        setGridType(GridType.REAL);
        setIsgridY(false);
        setDataXRightlength(adaptation.setCanvasAdaptation(0));
        setGridDownExceedX(adaptation.setCanvasAdaptation(5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setGridSpecification(IsBrokenX);
        if (XText != null) {
            DataXY = calculationXCoordinate(XText);
            if (!isDynamic) {
                dataSize = DataXY.length;
            }
            canvasStraightLine();
        }
    }

    private void canvasStraightLine() {
        //绘制渐变颜色
        if (DataXY != null) {
            if (Xdata != null) {
                if (gradientColor != null) {
                    for (int i = 0; i < Xdata.size(); i++) {
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        int[] color = new int[gradientColor.get(i).length];
                        for (int i1 = 0; i1 < gradientColor.get(i).length; i1++) {
                            color[i1] = gradientColor.get(i)[i1];
                        }
                        Shader mShader = new LinearGradient(X, adaptation.setCanvasAdaptation(getGridYFromTop()), X, Y, color, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
                        mPaint.setShader(mShader);
                        if(gradientColorAlpha!=null&&gradientColorAlpha.length == Xdata.size()){
                            mPaint.setAlpha(gradientColorAlpha[i]);
                        }
                        Path path = new Path();
                        path.moveTo(X + adaptation.setCanvasAdaptation(getXYWidth() / 2), Y );// 此点为多边形的起点
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if (i1 == 0) {
                                path.lineTo(DataXY[i1] + adaptation.setCanvasAdaptation(getXYWidth() / 2), getYValue(Xdata.get(i)[i1]));
                            } else if (i1 == dataSize - 1) {
                                path.lineTo(DataXY[i1] - adaptation.setCanvasAdaptation(getXYWidth() / 2), getYValue(Xdata.get(i)[i1]));
                            } else {
                                path.lineTo(DataXY[i1], getYValue(Xdata.get(i)[i1]));
                            }
                        }
                        path.lineTo(DataXY[dataSize - 1] - adaptation.setCanvasAdaptation(getXYWidth() / 2), Y);
                        path.close(); // 使这些点构成封闭的多边形
                        mCanvas.drawPath(path, mPaint);
                    }
                }
            } else {
                if (XdataDouble != null) {
                    if (gradientColor != null) {
                        for (int i = 0; i < XdataDouble.size(); i++) {
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            int[] color = new int[gradientColor.get(i).length];
                            for (int i1 = 0; i1 < gradientColor.get(i).length; i1++) {
                                color[i1] = gradientColor.get(i)[i1];
                            }
                            Shader mShader = new LinearGradient(X, adaptation.setCanvasAdaptation(getGridYFromTop()), X, Y, color, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
                            mPaint.setShader(mShader);
                            Path path = new Path();
                            path.moveTo(X + adaptation.setCanvasAdaptation(getXYWidth() / 2), Y);// 此点为多边形的起点
                            for (int i1 = 0; i1 < dataSize; i1++) {
                                if (i1 == 0) {
                                    path.lineTo(DataXY[i1] + adaptation.setCanvasAdaptation(getXYWidth() / 2), getYValue(XdataDouble.get(i)[i1]));
                                } else if (i1 == dataSize - 1) {
                                    path.lineTo(DataXY[i1] - adaptation.setCanvasAdaptation(getXYWidth() / 2), getYValue(XdataDouble.get(i)[i1]));
                                } else {
                                    path.lineTo(DataXY[i1], getYValue(XdataDouble.get(i)[i1]));
                                }
                            }
                            path.lineTo(DataXY[dataSize - 1] - adaptation.setCanvasAdaptation(getXYWidth() / 2), Y);
                            path.close(); // 使这些点构成封闭的多边形
                            mCanvas.drawPath(path, mPaint);
                        }
                    }
                }
            }
        }
        //绘制总目标值
        if (totalTargetValue != null) {
            for (int i = 0; i < totalTargetValue.length; i++) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(totalTargeValueWidth));
                if (totalTargetValueColor != null) {
                    if (totalTargetValue.length == totalTargetValueColor.length) {
                        mPaint.setColor(totalTargetValueColor[i]);
                    }
                } else {
                    mPaint.setColor(totalTargeValueColor);
                }
                mCanvas.drawLine(DataXY[0], getYValue(totalTargetValue[i]), getmWidth(), getYValue(totalTargetValue[i]), mPaint);// 画线
            }
        } else {
            if (totalTargetValueDouble != null) {
                for (int i = 0; i < totalTargetValueDouble.length; i++) {
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(totalTargeValueWidth));
                    if (totalTargetValueColor != null) {
                        if (totalTargetValueDouble.length == totalTargetValueColor.length) {
                            mPaint.setColor(totalTargetValueColor[i]);
                        }
                    } else {
                        mPaint.setColor(totalTargeValueColor);
                    }
                    mCanvas.drawLine(DataXY[0], getYValue(totalTargetValueDouble[i]), getmWidth(), getYValue(totalTargetValueDouble[i]), mPaint);// 画线
                }
            }
        }
        //绘制阶段目标值
        if (Xdata != null) {
            if (targetValue != null) {
                for (int i = 0; i < targetValue.size(); i++) {
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(targeValueWidth));
                    if (targetValueColor != null) {
                        if (targetValue.size() == targetValueColor.length) {
                            mPaint.setColor(targetValueColor[i]);
                        }
                    } else {
                        mPaint.setColor(targeValueColor);
                    }
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        if (i1 != 0) {
                            mCanvas.drawLine(DataXY[i1 - 1], getYValue(targetValue.get(i)[i1 - 1]), DataXY[i1], getYValue(targetValue.get(i)[i1]), mPaint);// 画线
                        }
                    }
                }
            }
        } else {
            if (XdataDouble != null) {
                if (targetValueDouble != null) {
                    for (int i = 0; i < targetValueDouble.size(); i++) {
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(targeValueWidth));
                        if (targetValueColor != null) {
                            if (targetValueDouble.size() == targetValueColor.length) {
                                mPaint.setColor(targetValueColor[i]);
                            }
                        } else {
                            mPaint.setColor(targeValueColor);
                        }
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if (i1 != 0) {
                                mCanvas.drawLine(DataXY[i1 - 1], getYValue(targetValueDouble.get(i)[i1 - 1]), DataXY[i1], getYValue(targetValueDouble.get(i)[i1]), mPaint);// 画线
                            }
                        }
                    }
                }
            }
        }
        //绘制折线
        if (Xdata != null) {
            for (int i = 0; i < Xdata.size(); i++) {
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                if (XdataColor != null) {
                    if (Xdata.size() == XdataColor.length) {
                        mPaint.setColor(XdataColor[i]);
                    }
                } else {
                    mPaint.setColor(lineColor);
                }
                for (int i1 = 0; i1 < dataSize; i1++) {
                    if (i1 != 0) {
                        mCanvas.drawLine(DataXY[i1 - 1], getYValue(Xdata.get(i)[i1 - 1]), DataXY[i1], getYValue(Xdata.get(i)[i1]), mPaint);// 画线
                    }
                    if (dataText) {
                        if (isRing) {
                            setDataText(DataXY[i1], getYValue(Xdata.get(i)[i1]), Xdata.get(i)[i1], adaptation.setCanvasAdaptation(ringRadius), dataTextColor, adaptation.setCanvasAdaptation(dataTextSize));
                        } else {
                            setDataText(DataXY[i1], getYValue(Xdata.get(i)[i1]), Xdata.get(i)[i1], 0, dataTextColor, adaptation.setCanvasAdaptation(dataTextSize));
                        }
                    }
                    if(isTheLastDay&&i1 == dataSize-1){
                        mPaint.setTextSize(adaptation.setCanvasAdaptation(theLastDayTextSieze));
                        Rect text = getTextWH(Xdata.get(i)[i1]+"", mPaint);
                        int yvalue = getYValue(Xdata.get(i)[i1]);
                        mPaint.setColor(theLastDayBgColor);
                        if(yvalue<mHeight/2){
                            RectF textRect = new RectF(DataXY[i1] - text.width(), yvalue +adaptation.setCanvasAdaptation(40),
                                    DataXY[i1],yvalue +adaptation.setCanvasAdaptation(40)+text.height());
                            RectF bgRect = new RectF(textRect.left-adaptation.setCanvasAdaptation(40),textRect.top,
                                    textRect.right,textRect.bottom+adaptation.setCanvasAdaptation(40));
                            Path path = new Path();
                            path.moveTo(DataXY[i1],yvalue+adaptation.setCanvasAdaptation(20));
                            path.lineTo(DataXY[i1],yvalue+adaptation.setCanvasAdaptation(60));
                            path.lineTo(DataXY[i1]-adaptation.setCanvasAdaptation(20),yvalue+adaptation.setCanvasAdaptation(60));
                            path.close();
                            mCanvas.drawPath(path,mPaint);
                            mCanvas.drawRoundRect(bgRect, 10, 10, mPaint);//第二个参数是x半径，第三个参数是y半径
                            mPaint.setTextAlign(Paint.Align.RIGHT);
                            mPaint.setColor(theLastDayTextColor);
                            mCanvas.drawText(Xdata.get(i)[i1]+"",DataXY[i1]-(textRect.left-bgRect.left)/2,textRect.bottom+adaptation.setCanvasAdaptation(20),mPaint);
                        }else{
                            RectF textRect = new RectF(DataXY[i1] - text.width(), yvalue -adaptation.setCanvasAdaptation(40)-text.height(),
                                    DataXY[i1],yvalue -adaptation.setCanvasAdaptation(40));
                            RectF bgRect = new RectF(textRect.left-adaptation.setCanvasAdaptation(40),textRect.top-adaptation.setCanvasAdaptation(40),
                                    textRect.right,textRect.bottom);
                            Path path = new Path();
                            path.moveTo(DataXY[i1],yvalue-adaptation.setCanvasAdaptation(20));
                            path.lineTo(DataXY[i1],yvalue-adaptation.setCanvasAdaptation(60));
                            path.lineTo(DataXY[i1]-adaptation.setCanvasAdaptation(20),yvalue-adaptation.setCanvasAdaptation(60));
                            path.close();
                            mCanvas.drawPath(path,mPaint);
                            mCanvas.drawRoundRect(bgRect, 10, 10, mPaint);//第二个参数是x半径，第三个参数是y半径
                            mPaint.setTextAlign(Paint.Align.RIGHT);
                            mPaint.setColor(theLastDayTextColor);
                            mCanvas.drawText(Xdata.get(i)[i1]+"",DataXY[i1]-(textRect.left-bgRect.left)/2,textRect.bottom-adaptation.setCanvasAdaptation(20),mPaint);
                        }
                    }
                }
            }
        } else {
            if (XdataDouble != null) {
                for (int i = 0; i < XdataDouble.size(); i++) {
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                    if (XdataColor != null) {
                        if (XdataDouble.size() == XdataColor.length) {
                            mPaint.setColor(XdataColor[i]);
                        }
                    } else {
                        mPaint.setColor(lineColor);
                    }
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        if (i1 != 0) {
                            mCanvas.drawLine(DataXY[i1 - 1], getYValue(XdataDouble.get(i)[i1 - 1]), DataXY[i1], getYValue(XdataDouble.get(i)[i1]), mPaint);// 画线
                        }
                        if (dataText) {
                            if (isRing) {
                                setDataText(DataXY[i1], getYValue(XdataDouble.get(i)[i1]), XdataDouble.get(i)[i1], adaptation.setCanvasAdaptation(ringRadius), dataTextColor, adaptation.setCanvasAdaptation(dataTextSize));
                            } else {
                                setDataText(DataXY[i1], getYValue(XdataDouble.get(i)[i1]), XdataDouble.get(i)[i1], 0, dataTextColor, adaptation.setCanvasAdaptation(dataTextSize));
                            }
                        }
                        if(isTheLastDay&&i1 == dataSize-1){
                            mPaint.setTextSize(adaptation.setCanvasAdaptation(theLastDayTextSieze));
                            Rect text = getTextWH(XdataDouble.get(i)[i1]+"", mPaint);
                            float yvalue = getYValue(XdataDouble.get(i)[i1]);
                            mPaint.setColor(theLastDayBgColor);
                            if(yvalue<mHeight/2){
                                RectF textRect = new RectF(DataXY[i1] - text.width(), yvalue +adaptation.setCanvasAdaptation(40),
                                        DataXY[i1],yvalue +adaptation.setCanvasAdaptation(40)+text.height());
                                RectF bgRect = new RectF(textRect.left-adaptation.setCanvasAdaptation(40),textRect.top,
                                        textRect.right,textRect.bottom+adaptation.setCanvasAdaptation(40));
                                Path path = new Path();
                                path.moveTo(DataXY[i1],yvalue+adaptation.setCanvasAdaptation(20));
                                path.lineTo(DataXY[i1],yvalue+adaptation.setCanvasAdaptation(60));
                                path.lineTo(DataXY[i1]-adaptation.setCanvasAdaptation(20),yvalue+adaptation.setCanvasAdaptation(60));
                                path.close();
                                mCanvas.drawPath(path,mPaint);
                                mCanvas.drawRoundRect(bgRect, 10, 10, mPaint);//第二个参数是x半径，第三个参数是y半径
                                mPaint.setTextAlign(Paint.Align.RIGHT);
                                mPaint.setColor(theLastDayTextColor);
                                mCanvas.drawText(XdataDouble.get(i)[i1]+"",DataXY[i1]-(textRect.left-bgRect.left)/2,textRect.bottom+adaptation.setCanvasAdaptation(20),mPaint);
                            }else{
                                RectF textRect = new RectF(DataXY[i1] - text.width(), yvalue -adaptation.setCanvasAdaptation(40)-text.height(),
                                        DataXY[i1],yvalue -adaptation.setCanvasAdaptation(40));
                                RectF bgRect = new RectF(textRect.left-adaptation.setCanvasAdaptation(40),textRect.top-adaptation.setCanvasAdaptation(40),
                                        textRect.right,textRect.bottom);
                                Path path = new Path();
                                path.moveTo(DataXY[i1],yvalue-adaptation.setCanvasAdaptation(20));
                                path.lineTo(DataXY[i1],yvalue-adaptation.setCanvasAdaptation(60));
                                path.lineTo(DataXY[i1]-adaptation.setCanvasAdaptation(20),yvalue-adaptation.setCanvasAdaptation(60));
                                path.close();
                                mCanvas.drawPath(path,mPaint);
                                mCanvas.drawRoundRect(bgRect, 10, 10, mPaint);//第二个参数是x半径，第三个参数是y半径
                                mPaint.setTextAlign(Paint.Align.RIGHT);
                                mPaint.setColor(theLastDayTextColor);
                                mCanvas.drawText(XdataDouble.get(i)[i1]+"",DataXY[i1]-(textRect.left-bgRect.left)/2,textRect.bottom-adaptation.setCanvasAdaptation(20),mPaint);
                            }
                        }
                    }
                }
            }
        }
        //绘制移动的时候显示圆点
        if(isSlideSelect&&touch){
            if (Xdata != null) {
                for (int i = 0; i < Xdata.size(); i++) {
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        if(i == slideSelectIndex&&i1 == slideSelectIndex1){
                            if(mLineListener!=null) mLineListener.onSlideSelectListener(slideSelectIndex1,slideSelectIndex,slideSelectIndex1,
                                    XText[slideSelectIndex1],Xdata.get(slideSelectIndex)[slideSelectIndex1]+"");
                            mPaint = new Paint();
                            //设置线条粗细
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(slideSelectYWidth));
                            //Y
                            mPaint.setColor(slideSelectYColor);
                            mCanvas.drawLine(DataXY[i1], Y, DataXY[i1], adaptation.setCanvasAdaptation(getGridYFromTop()) - adaptation.setCanvasAdaptation(getGridUpExceedgridYFromTop()), mPaint);

                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            if (XDataRingColor != null) {
                                mPaint.setColor(XDataRingColor[i]);
                            } else {
                                mPaint.setColor(ringColor);
                            }
                            mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius+clickWidth), mPaint);
                        }
                    }
                }
            }else{
                if (XdataDouble != null) {
                    for (int i = 0; i < XdataDouble.size(); i++) {
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if(i == slideSelectIndex&&i1 == slideSelectIndex1){
                                if(mLineListener!=null) mLineListener.onSlideSelectListener(slideSelectIndex1,slideSelectIndex,slideSelectIndex1,
                                        XText[slideSelectIndex1],XdataDouble.get(slideSelectIndex)[slideSelectIndex1]+"");
                                mPaint = new Paint();
                                //设置线条粗细
                                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(slideSelectYWidth));
                                //Y
                                mPaint.setColor(slideSelectYColor);
                                mCanvas.drawLine(DataXY[i1], Y, DataXY[i1], adaptation.setCanvasAdaptation(getGridYFromTop()) - adaptation.setCanvasAdaptation(getGridUpExceedgridYFromTop()), mPaint);

                                mPaint = new Paint();
                                mPaint.setAntiAlias(true);
                                if (XDataRingColor != null) {
                                    mPaint.setColor(XDataRingColor[i]);
                                } else {
                                    mPaint.setColor(ringColor);
                                }
                                mCanvas.drawCircle(DataXY[i1], getYValue(XdataDouble.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius+clickWidth), mPaint);
                            }
                        }
                    }
                }
            }
        }
        //绘制圆环
        if (isRing) {
            if (Xdata != null) {
                for (int i = 0; i < Xdata.size(); i++) {
                    if(isCircularLineDistance) {
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if(i == clicki&&i1 == clicki1){
                                mPaint.setColor(Color.WHITE);
                                mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius +circularLineDistance+clickWidth), mPaint);
                            }else{
                                mPaint.setColor(Color.WHITE);
                                mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius +circularLineDistance), mPaint);
                            }
                        }
                    }
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        if(i == clicki&&i1 == clicki1){
                            if (XDataRingColor != null) {
                                mPaint.setColor(XDataRingColor[i]);
                            } else {
                                mPaint.setColor(ringColor);
                            }
                            mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius+clickWidth), mPaint);
                            showDialog();
                        }else{
                            if (XDataRingColor != null) {
                                mPaint.setColor(XDataRingColor[i]);
                            } else {
                                mPaint.setColor(ringColor);
                            }
                            mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius), mPaint);
                        }
                    }
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);
                    mPaint.setColor(Color.WHITE);
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius - ringWidth), mPaint);
                    }
                }
            } else {
                if (XdataDouble != null) {
                    for (int i = 0; i < XdataDouble.size(); i++) {
                        if(isCircularLineDistance) {
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            for (int i1 = 0; i1 < dataSize; i1++) {
                                if(i == clicki&&i1 == clicki1){
                                    mPaint.setColor(Color.WHITE);
                                    mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius +circularLineDistance+clickWidth), mPaint);
                                }else{
                                    mPaint.setColor(Color.WHITE);
                                    mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius +circularLineDistance), mPaint);
                                }
                            }
                        }
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if(i == clicki&&i1 == clicki1){
                                if (XDataRingColor != null) {
                                    mPaint.setColor(XDataRingColor[i]);
                                } else {
                                    mPaint.setColor(ringColor);
                                }
                                mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius+clickWidth), mPaint);
                                showDialog();
                            }else{
                                if (XDataRingColor != null) {
                                    mPaint.setColor(XDataRingColor[i]);
                                } else {
                                    mPaint.setColor(ringColor);
                                }
                                mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius), mPaint);
                            }
                        }
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setColor(Color.WHITE);
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            mCanvas.drawCircle(DataXY[i1], getYValue(Xdata.get(i)[i1]), adaptation.setCanvasAdaptation(ringRadius - ringWidth), mPaint);
                        }
                    }
                }
            }
        }
        //绘制右上角
        if (Xdata != null) {
            if (isTitleRight) {
                titleRightXDistance = getmWidth() - adaptation.setCanvasAdaptation(titleRightTextRightDistance);
                //绘制数据折线的描述
                if (dataTitleRightText != null) {
                    for (int i = 0; i < Xdata.size(); i++) {
                        //绘制字
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setColor(titleRightTextColor);
                        mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                        mCanvas.drawText(dataTitleRightText[i], titleRightXDistance - getTextWH(dataTitleRightText[i], mPaint).width(),
                                adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                        titleRightXDistance -= getTextWH(dataTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                        //颜色渐变是否存在
                        if (gradientColor == null) {
                            //绘制右上角小折线
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                            if (XdataColor != null) {
                                if (Xdata.size() == XdataColor.length) {
                                    mPaint.setColor(XdataColor[i]);
                                }
                            } else {
                                mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                            }
                            mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                            mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                        } else {
                            //绘制右上角小长方形
                            Paint mPaint2 = new Paint();
                            mPaint2.setColor(titleRightTextColor);
                            mPaint2.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));

                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setStyle(Paint.Style.FILL);
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                            if (titleRightRectangleWithinColorList != null) {
                                mPaint.setColor(titleRightRectangleWithinColorList[i]);
                            } else {
                                mPaint.setColor(titleRightRectangleWithinColor);
                            }

                            mCanvas.drawRect(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightRectangleWidth),
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 - getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10),
                                    titleRightXDistance,
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10), mPaint);// 正方形
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setStyle(Paint.Style.STROKE);
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                            if (XdataColor != null) {
                                if (Xdata.size() == XdataColor.length) {
                                    mPaint.setColor(XdataColor[i]);
                                }
                            } else {
                                mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                            }
                            mCanvas.drawRect(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightRectangleWidth),
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 - getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10),
                                    titleRightXDistance,
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10), mPaint);// 正方形
                            titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightRectangleWidth) + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                        }
                    }
                }
                //绘制阶段目标值的描述
                if (setTargetValueTitleRightText != null) {
                    for (int i = 0; i < targetValue.size(); i++) {
                        //绘制字
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setColor(titleRightTextColor);
                        mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                        mCanvas.drawText(setTargetValueTitleRightText[i], titleRightXDistance - getTextWH(setTargetValueTitleRightText[i], mPaint).width(),
                                adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                        titleRightXDistance -= getTextWH(setTargetValueTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                        //绘制右上角小折线
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                        if (targetValueColor != null) {
                            if (targetValue.size() == targetValueColor.length) {
                                mPaint.setColor(targetValueColor[i]);
                            }
                        } else {
                            mPaint.setColor(targeValueColor);
                        }
                        mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                        mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                        mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                        titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                    }
                }
                //绘制总目标值的描述
                if (totalTargetTitleRightText != null) {
                    for (int i = 0; i < totalTargetValue.length; i++) {
                        //绘制字
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setColor(titleRightTextColor);
                        mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                        mCanvas.drawText(totalTargetTitleRightText[i], titleRightXDistance - getTextWH(totalTargetTitleRightText[i], mPaint).width(),
                                adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                        titleRightXDistance -= getTextWH(dataTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                        //绘制右上角小折线
                        mPaint = new Paint();
                        mPaint.setAntiAlias(true);
                        mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                        if (totalTargetValueColor != null) {
                            if (totalTargetValue.length == totalTargetValueColor.length) {
                                mPaint.setColor(totalTargetValueColor[i]);
                            }
                        } else {
                            mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                        }
                        mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                        mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                        mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                        titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                    }
                }
            }
        } else {
            if (XdataDouble != null) {
                if (isTitleRight) {
                    titleRightXDistance = getmWidth() - adaptation.setCanvasAdaptation(titleRightTextRightDistance);
                    //绘制数据折线的描述
                    if (dataTitleRightText != null) {
                        for (int i = 0; i < XdataDouble.size(); i++) {
                            //绘制字
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setColor(titleRightTextColor);
                            mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                            mCanvas.drawText(dataTitleRightText[i], titleRightXDistance - getTextWH(dataTitleRightText[i], mPaint).width(),
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                            titleRightXDistance -= getTextWH(dataTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                            //颜色渐变是否存在
                            if (gradientColor == null) {
                                //绘制右上角小折线
                                mPaint = new Paint();
                                mPaint.setAntiAlias(true);
                                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                                if (XdataColor != null) {
                                    if (XdataDouble.size() == XdataColor.length) {
                                        mPaint.setColor(XdataColor[i]);
                                    }
                                } else {
                                    mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                                }
                                mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                        titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                                mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                        titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                                mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                        titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                                titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                            } else {
                                //绘制右上角小长方形
                                Paint mPaint2 = new Paint();
                                mPaint2.setColor(titleRightTextColor);
                                mPaint2.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));

                                mPaint = new Paint();
                                mPaint.setAntiAlias(true);
                                mPaint.setStyle(Paint.Style.FILL);
                                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                                if (titleRightRectangleWithinColorList != null) {
                                    mPaint.setColor(titleRightRectangleWithinColorList[i]);
                                } else {
                                    mPaint.setColor(titleRightRectangleWithinColor);
                                }

                                mCanvas.drawRect(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightRectangleWidth),
                                        adaptation.setCanvasAdaptation(getTitleHeight()) / 2 - getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10),
                                        titleRightXDistance,
                                        adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10), mPaint);// 正方形
                                mPaint = new Paint();
                                mPaint.setAntiAlias(true);
                                mPaint.setStyle(Paint.Style.STROKE);
                                mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                                if (XdataColor != null) {
                                    if (XdataDouble.size() == XdataColor.length) {
                                        mPaint.setColor(XdataColor[i]);
                                    }
                                } else {
                                    mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                                }
                                mCanvas.drawRect(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightRectangleWidth),
                                        adaptation.setCanvasAdaptation(getTitleHeight()) / 2 - getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10),
                                        titleRightXDistance,
                                        adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + getTextWH(dataTitleRightText[i], mPaint2).height() / 2 - adaptation.setCanvasAdaptation(10), mPaint);// 正方形
                                titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightRectangleWidth) + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                            }
                        }
                    }
                    //绘制阶段目标值的描述
                    if (setTargetValueTitleRightText != null) {
                        for (int i = 0; i < targetValueDouble.size(); i++) {
                            //绘制字
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setColor(titleRightTextColor);
                            mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                            mCanvas.drawText(setTargetValueTitleRightText[i], titleRightXDistance - getTextWH(setTargetValueTitleRightText[i], mPaint).width(),
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                            titleRightXDistance -= getTextWH(setTargetValueTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                            //绘制右上角小折线
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                            if (targetValueColor != null) {
                                if (targetValueDouble.size() == targetValueColor.length) {
                                    mPaint.setColor(targetValueColor[i]);
                                }
                            } else {
                                mPaint.setColor(targeValueColor);
                            }
                            mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                            mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                        }
                    }
                    //绘制总目标值的描述
                    if (totalTargetTitleRightText != null) {
                        for (int i = 0; i < totalTargetValueDouble.length; i++) {
                            //绘制字
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setColor(titleRightTextColor);
                            mPaint.setTextSize(adaptation.setCanvasAdaptation(titleRightTextSize));
                            mCanvas.drawText(totalTargetTitleRightText[i], titleRightXDistance - getTextWH(totalTargetTitleRightText[i], mPaint).width(),
                                    adaptation.setCanvasAdaptation(getTitleHeight()) / 2 + adaptation.setCanvasAdaptation(5), mPaint);
                            titleRightXDistance -= getTextWH(dataTitleRightText[i], mPaint).width() + adaptation.setCanvasAdaptation(titleRightTextlineDistance);
                            //绘制右上角小折线
                            mPaint = new Paint();
                            mPaint.setAntiAlias(true);
                            mPaint.setStrokeWidth(adaptation.setCanvasAdaptation(lineWidth));
                            if (totalTargetValueColor != null) {
                                if (totalTargetValueDouble.length == totalTargetValueColor.length) {
                                    mPaint.setColor(totalTargetValueColor[i]);
                                }
                            } else {
                                mPaint.setColor(adaptation.setCanvasAdaptation(lineColor));
                            }
                            mCanvas.drawLine(titleRightXDistance, adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            mCanvas.drawLine(titleRightXDistance - adaptation.setCanvasAdaptation(titleRightLineMin), adaptation.setCanvasAdaptation(getTitleHeight()) / 2,
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10), mPaint);
                            mCanvas.drawLine(titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 2), adaptation.setCanvasAdaptation(getTitleHeight() / 2 - 10),
                                    titleRightXDistance - (adaptation.setCanvasAdaptation(titleRightLineMin) * 3), adaptation.setCanvasAdaptation(getTitleHeight()) / 2, mPaint);
                            titleRightXDistance -= adaptation.setCanvasAdaptation(titleRightLineMin) * 3 + adaptation.setCanvasAdaptation(titleRightTextBetweenDistance);
                        }
                    }
                }
            }
        }
        if (dataSize <= DataXY.length - 1) {
            dataSize++;
            postInvalidateDelayed(dynamicTime);
        }
    }

    /**
     * 弹出描述
     */
    private void showDialog(){
        if(XdataDetails!=null&&XdataDetails.size()==Xdata.size()){
            s:for (int i = 0; i < Xdata.size(); i++) {
                String[] dataDetails = XdataDetails.get(i);
                for (int i1 = 0; i1 < dataSize; i1++) {
                    if(i == clicki&&i1== clicki1){
                        mPaint.setTextAlign(Paint.Align.CENTER);
                        mPaint.setColor(Color.BLACK);
                        mPaint.setAlpha(180);
                        mPaint.setStyle(Paint.Style.FILL);
                        mPaint.setStrokeWidth(1);
                        mPaint.setTextSize(adaptation.setCanvasAdaptation(30));
                        Rect textWH = getTextWH(dataDetails[i1], mPaint);
                        mPaint.setStrokeWidth(5);
                        float w = textWH.width() + adaptation.setCanvasAdaptation(100);
                        float h = textWH.height() + adaptation.setCanvasAdaptation(60);
                        RectF rectF = new RectF(downX - w / 2, downY - h / 2, downX + w / 2, downY + h / 2);
                        mCanvas.drawRoundRect(rectF, 20, 20, mPaint);
                        mPaint.setStrokeWidth(1);
                        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;
                        mPaint.setColor(Color.WHITE);
                        mCanvas.drawText(dataDetails[i1], rectF.centerX(), baseline, mPaint);
                        break s;
                    }
                }
            }

        }
    }

    @Override
    protected void onTouchDown(float downX, float downY) {
        this.downX = downX;
        this.downY = downY;
        touch = true;
        if(isClick){
            for (int i = 0; i < Xdata.size(); i++) {
                for (int i1 = 0; i1 < dataSize; i1++) {
                    if(isClickCircularView(downX,downY,DataXY[i1],getYValue(Xdata.get(i)[i1]),adaptation.setCanvasAdaptation(60))){
                        clicki = i;
                        clicki1 = i1;
//                        Log.e("onTouchDown","点击到了 i:"+i+"  i1:"+i1);
                        invalidate();
                        break;
                    }else{
                        clicki = -1;
                        clicki1 = -1;
                        invalidate();
                    }
                }
            }
        }
    }

    @Override
    protected void onTouchMove(float moveX, float moveY) {
        if(isSlideSelect){
            if(Xdata!=null&&DataXY!=null) {
                for (int i = 0; i < Xdata.size(); i++) {
                    for (int i1 = 0; i1 < dataSize; i1++) {
                        if (isClickHistogramView(moveX, moveY, DataXY[i1] - adaptation.setCanvasAdaptation(5), 0, adaptation.setCanvasAdaptation(10), mHeight)) {
                            slideSelectIndex = i;
                            slideSelectIndex1 = i1;
//                        Log.e("onTouchMove","点击到了 i:"+i+"  i1:"+i1);
                            invalidate();
                            break;
                        }
                    }
                }
            }else{
                if(XdataDouble!=null&&DataXY!=null) {
                    for (int i = 0; i < XdataDouble.size(); i++) {
                        for (int i1 = 0; i1 < dataSize; i1++) {
                            if (isClickHistogramView(moveX, moveY, DataXY[i1] - adaptation.setCanvasAdaptation(5), 0, adaptation.setCanvasAdaptation(10), mHeight)) {
                                slideSelectIndex = i;
                                slideSelectIndex1 = i1;
//                        Log.e("onTouchMove","点击到了 i:"+i+"  i1:"+i1);
                                invalidate();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onTouchUp(float upX, float upY) {
        touch = false;
        if(isSlideSelect) invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (Xdata != null) {
            int[] XdataYEnd;
            XdataYEndList = new ArrayList<>();
            for (int i = 0; i < Xdata.size(); i++) {
                XdataYEnd = new int[Xdata.get(i).length];
                for (int i1 = 0; i1 < Xdata.get(i).length; i1++) {
                    XdataYEnd[i1] = Xdata.get(i)[i1];
                }
                XdataYEndList.add(XdataYEnd);
            }
        }
        if (XdataDouble != null) {
            double[] XdataYEnd;
            XdataYEndListDouble = new ArrayList<>();
            for (int i = 0; i < XdataDouble.size(); i++) {
                XdataYEnd = new double[XdataDouble.get(i).length];
                for (int i1 = 0; i1 < XdataDouble.get(i).length; i1++) {
                    XdataYEnd[i1] = XdataDouble.get(i)[i1];
                }
                XdataYEndListDouble.add(XdataYEnd);
            }
        }
    }

    public interface onStraightLineViewListener{
        void onSlideSelectListener(int xIndex,int listi,int datai,String xText,String dataText);
    }

    public void setOnStraightLineListener(onStraightLineViewListener lineListener){
        this.mLineListener = lineListener;
    }

    /**
     * 初始化点击选点
     */
    public void initClick (){
        clicki = -1;
        clicki1 = -1;
        invalidate();
    }

    /**
     * 设置右上角为长方形的时候的内颜色
     *
     * @param color
     */
    public void setTitleRightRectangleWithinColorList(int[] color) {
        titleRightRectangleWithinColorList = color;
    }

    /**
     * 设置阶段目标值右上角描述
     *
     * @param text
     */
    public void setTotalTargetTitleRightText(String[] text) {
        totalTargetTitleRightText = text;
    }

    /**
     * 设置阶段目标值右上角描述
     *
     * @param text
     */
    public void setTargetValueTitleRightText(String[] text) {
        setTargetValueTitleRightText = text;
    }

    /**
     * 设置数据对应右上角的字体描述
     *
     * @param text
     */
    public void setDataTitleRightText(String[] text) {
        dataTitleRightText = text;
    }

    /**
     * 设置数据渐变颜色
     *
     * @param color
     */
    public void setGradientColor(List<int[]> color) {
        gradientColor = color;
    }
    /**
     * 设置数据渐变颜色透明度 0-255
     *
     * @param alpha
     */
    public void setGradientAlphaColor(int[] alpha) {
        gradientColorAlpha = alpha;
    }

    /**
     * 设置每阶段目标值
     *
     * @param value
     */
    public void setTargetValue(List<int[]> value) {
        targetValue = value;
    }

    /**
     * 设置每阶段目标值
     *
     * @param value
     */
    public void setTargetValueDouble(List<double[]> value) {
        targetValueDouble = value;
    }

    /**
     * 设置每阶段目标值颜色
     *
     * @param color
     */
    public void setTargetValueColor(int[] color) {
        targetValueColor = color;
    }

    /**
     * 设置总目标值颜色
     *
     * @param color
     */
    public void settotalTargetValueColor(int[] color) {
        totalTargetValueColor = color;
    }

    /**
     * 设置总目标值
     *
     * @param value
     */
    public void settotalTargetValue(int[] value) {
        totalTargetValue = value;
    }

    /**
     * 设置总目标值
     *
     * @param value
     */
    public void settotalTargetValue(double[] value) {
        totalTargetValueDouble = value;
    }

    /**
     * 设置数据折线颜色
     *
     * @param color
     */
    public void setXDataColor(int[] color) {
        XdataColor = color;
    }

    /**
     * 设置X轴的数据
     *
     * @param data
     */
    public void setXData(List<int[]> data) {
        Xdata = data;
    }
    /**
     * 设置X轴的数据
     *
     * @param dataDetails
     */
    public void setXDataDetails(List<String[]> dataDetails) {
        XdataDetails = dataDetails;
    }

    /**
     * 设置X轴的数据
     *
     * @param data
     */
    public void setXDataDouble(List<double[]> data) {
        XdataDouble = data;
    }

    /**
     * 设置数据所对应的圆环颜色
     *
     * @param color
     */
    public void setXDataRingColor(int[] color) {
        XDataRingColor = color;
    }

    /**
     * 设置X轴的text
     *
     * @param text
     */
    public void setXText(String[] text) {
        XText = text;
    }

    /**
     * 是否显示X轴对应的网格
     *
     * @param isBrokenX
     */
    public void setIsBrokenX(Boolean[] isBrokenX) {
        this.IsBrokenX = isBrokenX;
    }

    public int getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getTotalTargeValueColor() {
        return totalTargeValueColor;
    }

    public void setTotalTargeValueColor(int totalTargeValueColor) {
        this.totalTargeValueColor = totalTargeValueColor;
    }

    public int getTotalTargeValueWidth() {
        return totalTargeValueWidth;
    }

    public void setTotalTargeValueWidth(int totalTargeValueWidth) {
        this.totalTargeValueWidth = totalTargeValueWidth;
    }

    public int getTargeValueColor() {
        return targeValueColor;
    }

    public void setTargeValueColor(int targeValueColor) {
        this.targeValueColor = targeValueColor;
    }

    public int getTargeValueWidth() {
        return targeValueWidth;
    }

    public void setTargeValueWidth(int targeValueWidth) {
        this.targeValueWidth = targeValueWidth;
    }

    public int getRingColor() {
        return ringColor;
    }

    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    public int getRingRadius() {
        return ringRadius;
    }

    public void setRingRadius(int ringRadius) {
        this.ringRadius = ringRadius;
    }

    public int getTitleRightTextRightDistance() {
        return titleRightTextRightDistance;
    }

    public void setTitleRightTextRightDistance(int titleRightTextRightDistance) {
        this.titleRightTextRightDistance = titleRightTextRightDistance;
    }

    public int getTitleRightTextlineDistance() {
        return titleRightTextlineDistance;
    }

    public void setTitleRightTextlineDistance(int titleRightTextlineDistance) {
        this.titleRightTextlineDistance = titleRightTextlineDistance;
    }

    public int getTitleRightLineMin() {
        return titleRightLineMin;
    }

    public void setTitleRightLineMin(int titleRightLineMin) {
        this.titleRightLineMin = titleRightLineMin;
    }

    public int getTitleRightTextBetweenDistance() {
        return titleRightTextBetweenDistance;
    }

    public void setTitleRightTextBetweenDistance(int titleRightTextBetweenDistance) {
        this.titleRightTextBetweenDistance = titleRightTextBetweenDistance;
    }

    public int getTitleRightTextColor() {
        return titleRightTextColor;
    }

    public void setTitleRightTextColor(int titleRightTextColor) {
        this.titleRightTextColor = titleRightTextColor;
    }

    public int getTitleRightTextSize() {
        return titleRightTextSize;
    }

    public void setTitleRightTextSize(int titleRightTextSize) {
        this.titleRightTextSize = titleRightTextSize;
    }

    public boolean isTitleRight() {
        return isTitleRight;
    }

    public void setTitleRight(boolean titleRight) {
        isTitleRight = titleRight;
    }

    public boolean isRing() {
        return isRing;
    }

    public void setRing(boolean ring) {
        isRing = ring;
    }

    public int getTitleRightRectangleWidth() {
        return titleRightRectangleWidth;
    }

    public void setTitleRightRectangleWidth(int titleRightRectangleWidth) {
        this.titleRightRectangleWidth = titleRightRectangleWidth;
    }

    public int getTitleRightRectangleWithinColor() {
        return titleRightRectangleWithinColor;
    }

    public void setTitleRightRectangleWithinColor(int titleRightRectangleWithinColor) {
        this.titleRightRectangleWithinColor = titleRightRectangleWithinColor;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public int getDynamicTime() {
        return dynamicTime;
    }

    public void setDynamicTime(int dynamicTime) {
        this.dynamicTime = dynamicTime;
    }

    public boolean isDataText() {
        return dataText;
    }

    public void setDataText(boolean dataText) {
        this.dataText = dataText;
    }

    public int getDataTextColor() {
        return dataTextColor;
    }

    public void setDataTextColor(int dataTextColor) {
        this.dataTextColor = dataTextColor;
    }

    public int getDataTextSize() {
        return dataTextSize;
    }

    public void setDataTextSize(int dataTextSize) {
        this.dataTextSize = dataTextSize;
    }

    public boolean isCircularLineDistance() {
        return isCircularLineDistance;
    }

    public void setCircularLineDistance(boolean circularLineDistance) {
        isCircularLineDistance = circularLineDistance;
    }

    public int getCircularLineDistance() {
        return circularLineDistance;
    }

    public void setCircularLineDistance(int circularLineDistance) {
        this.circularLineDistance = circularLineDistance;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getClickWidth() {
        return clickWidth;
    }

    public void setClickWidth(int clickWidth) {
        this.clickWidth = clickWidth;
    }

    public boolean isSlideSelect() {
        return isSlideSelect;
    }

    public void setSlideSelect(boolean slideSelect) {
        isSlideSelect = slideSelect;
    }

    public int getSlideSelectYColor() {
        return slideSelectYColor;
    }

    public void setSlideSelectYColor(int slideSelectYColor) {
        this.slideSelectYColor = slideSelectYColor;
    }

    public int getSlideSelectYWidth() {
        return slideSelectYWidth;
    }

    public void setSlideSelectYWidth(int slideSelectYWidth) {
        this.slideSelectYWidth = slideSelectYWidth;
    }

    public boolean isTheLastDay() {
        return isTheLastDay;
    }

    public void setTheLastDay(boolean theLastDay) {
        isTheLastDay = theLastDay;
    }

    public int getTheLastDayBgColor() {
        return theLastDayBgColor;
    }

    public void setTheLastDayBgColor(int theLastDayBgColor) {
        this.theLastDayBgColor = theLastDayBgColor;
    }

    public int getTheLastDayTextColor() {
        return theLastDayTextColor;
    }

    public void setTheLastDayTextColor(int theLastDayTextColor) {
        this.theLastDayTextColor = theLastDayTextColor;
    }

    public int getTheLastDayTextSieze() {
        return theLastDayTextSieze;
    }

    public void setTheLastDayTextSieze(int theLastDayTextSieze) {
        this.theLastDayTextSieze = theLastDayTextSieze;
    }
}
