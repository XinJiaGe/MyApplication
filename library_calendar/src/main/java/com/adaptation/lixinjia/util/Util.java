package com.adaptation.lixinjia.util;

import android.graphics.Rect;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：李忻佳
 * 时间：2017/7/13
 * 说明：
 */

public class Util {
    /**
     * yyyy-MM-dd转Data
     * @param time
     * @return
     */
    public static Date yyyyMMddData(String time){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 毫秒转 yyyy-MM-dd
     *
     * @param mil
     * @return
     */
    public static String mil2yyyyMMdd(long mil)
    {
        Date date = new Date(mil);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    /**
     *
     * @return 返回当前时间的yyyy-MM-dd字符串
     */
    public static String getNow_yyyyMMdd()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    /**
     * 毫秒转 dd
     *
     * @param mil
     * @return
     */
    public static String mil2dd(long mil)
    {
        Date date = new Date(mil);
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    /**
     * 星期几
     *
     * @param date
     * Date 日期
     * @return 星期一到星期日
     *
     * */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }

    /**
     * 获取控件的高度
     */
    public static int getViewMeasuredHeight(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度
     */
    public static int getViewMeasuredWidth(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     */
    private static void calculateViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);
    }
    /**
     * 判断点击的点是否在控件的区域内
     * @param view
     * @param x
     * @param y
     * @return
     */
    public static boolean isTouchView(View view, int x, int y)
    {
        boolean result = false;
        Rect r = getViewRect(view);
        if (r != null)
        {
            result = r.contains(x, y);
        }
        return result;
    }
    /**
     * 获取控件在屏幕上的区域
     * @param view
     * @return
     */
    public static Rect getViewRect(View view)
    {
        Rect r = new Rect();
        if (view != null && view.getVisibility() == View.VISIBLE)
        {
            int[] location = getViewLocationOnScreen(view);
            r.left = location[0];
            r.right = r.left + getViewWidth(view);
            r.top = location[1];
            r.bottom = r.top + getViewHeight(view);
        }
        return r;
    }
    /**
     * 获取控件的高度
     * @param view
     * @return
     */
    public static int getViewHeight(View view)
    {
        int height = 0;
        height = view.getHeight();
        if (height <= 0)
        {
            calculateViewMeasure(view);
            height = view.getMeasuredHeight();
        }
        return height;
    }
    /**
     * 获取控件的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view)
    {
        int width = 0;
        width = view.getWidth();
        if (width <= 0)
        {
            calculateViewMeasure(view);
            width = view.getMeasuredWidth();
        }
        return width;
    }
    /**
     * 获取屏幕上的控件位置
     * @param view
     * @return
     */
    public static int[] getViewLocationOnScreen(View view)
    {
        int[] location = new int[2];
        if (view != null)
        {
            view.getLocationOnScreen(location);
        }
        return location;
    }
}
