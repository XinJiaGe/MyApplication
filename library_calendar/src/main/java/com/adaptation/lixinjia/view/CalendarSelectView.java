package com.adaptation.lixinjia.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.adaptation.lixinjia.library_calendar.R;
import com.adaptation.lixinjia.util.Util;

import java.util.Calendar;
import java.util.Date;


/**
 * 作者：李忻佳
 * 时间：2017/7/13
 * 说明：日历
 */

public class CalendarSelectView extends LinearLayout implements View.OnClickListener {
    private String[] weekday = new String[] { "日", "一", "二", "三", "四", "五", "六" };
    private Context mContext;
    private View mView;
    private String mData;
    private LinearLayout xqll;
    private LinearLayout rqll;
    private int dataTimeXq = 0;
    private int screenWidth;
    private int screenHeight;
    private TextView tvTime;
    private LinearLayout llRight;
    private LinearLayout llLeft;
    private LinearLayout llDown;
    private PopCalendar popCalendar;

    public CalendarSelectView(Context context) {
        super(context);
        init(context);
    }

    public CalendarSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        mData = Util.getNow_yyyyMMdd();
        initView();
        removeAllViews();
        addClick();
        addXq();
        addRq();
        addView(mView);
    }
    private void initView(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_galendar, null);
        mView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        xqll = (LinearLayout)mView.findViewById(R.id.view_galendar_xqll);
        rqll = (LinearLayout)mView.findViewById(R.id.view_galendar_rqll);
        llDown = (LinearLayout)mView.findViewById(R.id.view_galendar_ll_down);
        tvTime = (TextView)mView.findViewById(R.id.view_galendar_tv_time);
        llLeft = (LinearLayout)mView.findViewById(R.id.view_galendar_ll_left);
        llRight = (LinearLayout)mView.findViewById(R.id.view_galendar_ll_right);
    }
    private void addClick(){
        llDown.setOnClickListener(this);
        llLeft.setOnClickListener(this);
        llRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == llDown){
            if(llDown.getRotation() != 0){
                llDown.setRotation(0);
                addRq();
                popCalendar.dismiss();
            }else{
                if(rqll!=null) {
                    llDown.setRotation(180);
                    rqll.removeAllViews();
                    if(popCalendar == null) {
                        popCalendar = new PopCalendar(mContext,weekday,mData,screenWidth,llLeft,llRight);
                        popCalendar.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                llDown.setRotation(0);
                                addRq();
                            }
                        });
                    }
                    popCalendar.showAsDropDown(xqll);
                }
            }
        }
        if(v == llLeft){
            popCalendar.lastMonth();
        }
        if(v == llRight){
            popCalendar.nextMonth();
        }
    }
    /**
     * 添加星期
     */
    private void addXq(){
        if(xqll!=null){
            xqll.removeAllViews();
            for (int i = 0; i < weekday.length; i++) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/7, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                TextView xqview = new TextView(getContext());
                xqview.setGravity(Gravity.CENTER);
                xqview.setText(weekday[i]);
                xqview.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                xqview.setTextColor(Color.parseColor("#333333"));
                xqview.setLayoutParams(new LinearLayout.LayoutParams(screenWidth/14,screenWidth/14));
                linearLayout.addView(xqview);
                xqll.addView(linearLayout);
            }
        }
        tvTime.setText(mData);
    }

    /**
     * 添加一行日期
     */
    private void addRq(){
        if(rqll!=null) {
            rqll.removeAllViews();
            Date dataTime = Util.yyyyMMddData(mData);
            if (dataTime != null) {
                dataTimeXq = Util.getWeekOfDate(dataTime);
            }
            Calendar rightNow = Calendar.getInstance();
            for (int i = 0; i < weekday.length; i++) {
                LinearLayout linearLayout = new LinearLayout(mContext);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 7, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                rightNow.setTime(dataTime);
                rightNow.add(Calendar.DAY_OF_YEAR, i - dataTimeXq);//日期加1天
                Date dt1 = rightNow.getTime();
                TextView riview = new TextView(getContext());
                riview.setGravity(Gravity.CENTER);
                riview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                riview.setText(Util.mil2dd(dt1.getTime()));
                if (i == dataTimeXq) {
                    riview.setBackgroundResource(R.drawable.calendar_date_focused);
                    riview.setTextColor(Color.WHITE);
                } else {
                    riview.setTextColor(Color.parseColor("#333333"));
                }
                riview.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 10, screenWidth / 10));
                linearLayout.addView(riview);
                rqll.addView(linearLayout);
            }
        }
    }
}
