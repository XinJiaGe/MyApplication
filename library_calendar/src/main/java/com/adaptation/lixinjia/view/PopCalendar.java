package com.adaptation.lixinjia.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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
 * 说明：日历弹窗
 */

public class PopCalendar extends PopupWindow {
    private LinearLayout mLlLeft;
    private LinearLayout mLlRight;
    private int mWidth;
    private String mData;
    private String[] mWeekday;
    private PopCalendar ins;
    private Context mContext;
    private View mView;
    private int fromYDelta;
    private LinearLayout llDesmiss;
    private LinearLayout mLL;
    private int dataXq;
    private Calendar rightNow;
    private Date dataTimeOne;
    private String riqione;
    private Calendar timeCalend;
    private int endId = 0;
    private int[] startHangIndex = {0};
    private int[] startId = {0};

    public PopCalendar(Context context, String[] weekday, String data, int screenWidth, LinearLayout llLeft, LinearLayout llRight){
        this.mContext = context;
        this.mWeekday = weekday;
        this.mWidth = screenWidth;
        this.mData = data;
        this.mLlLeft = llLeft;
        this.mLlRight = llRight;
        this.ins = this;
        initView();
        addCalendar();
    }
    private void addCalendar(){
        mLL.removeAllViews();
        Date dataTime = Util.yyyyMMddData(mData);
        if (dataTime != null) {
            dataXq = Util.getWeekOfDate(dataTime);
        }
        timeCalend = Calendar.getInstance();
        timeCalend.setTime(dataTime);
        int year = timeCalend.get(Calendar.YEAR);
        int dd = timeCalend.get(Calendar.MONTH)+1;
        riqione = year +"-"+ dd + "-01";
        dataTimeOne = Util.yyyyMMddData(riqione);
        rightNow = Calendar.getInstance();
        addri(0);
    }
    private void addri(int index){
        boolean isend = false;
        final LinearLayout linearLayouts = new LinearLayout(mContext);
        linearLayouts.setOrientation(LinearLayout.HORIZONTAL);
        linearLayouts.setGravity(Gravity.CENTER);
        linearLayouts.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        for (int i = 0; i < mWeekday.length; i++) {
            final LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(mWidth / 7, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            final TextView riview = new TextView(mContext);
            riview.setId(Integer.parseInt((index+i)+""));
            riview.setGravity(Gravity.CENTER);
            riview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            rightNow.setTime(dataTimeOne);
            rightNow.add(Calendar.DAY_OF_YEAR, i - dataXq-1 +index*mWeekday.length);//日期加1天
            Date dt1 = rightNow.getTime();
            if(index<1 && rightNow.get(Calendar.DAY_OF_MONTH)>15){
                riview.setText("");
            }else if(index>3 && rightNow.get(Calendar.DAY_OF_MONTH)<15){
                riview.setText("");
                isend = true;
            }else{
                riview.setText(Util.mil2dd(dt1.getTime()));
                String indexTime = rightNow.get(Calendar.YEAR)+"-"+((rightNow.get(Calendar.MONTH)+1)<10?"0"+(rightNow.get(Calendar.MONTH)+1):(rightNow.get(Calendar.MONTH)+1))+"-"+rightNow.get(Calendar.DAY_OF_MONTH);
//                if(){
//
//                }
                if (indexTime.equals(mData)) {
                    startId[0] = i;
                    startHangIndex[0] = index;
                    riview.setBackgroundResource(R.drawable.calendar_date_focused);
                    riview.setTextColor(Color.WHITE);
                } else {
                    riview.setTextColor(Color.parseColor("#333333"));
                }
                final String toasTime = Util.mil2yyyyMMdd(rightNow.getTime().getTime());
                endId = riview.getId();
                final int hangIndex = index;
                final int finalI = i;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout hanll = (LinearLayout) mLL.getChildAt(startHangIndex[0]);
                        LinearLayout ll = (LinearLayout) hanll.getChildAt(startId[0]);
                        TextView tv = (TextView) ll.getChildAt(0);
                        LinearLayout ll2 = (LinearLayout) linearLayouts.getChildAt(finalI);
                        TextView tv2 = (TextView) ll2.getChildAt(0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            tv.setBackground(null);
                        }
                        tv.setTextColor(Color.parseColor("#333333"));
                        tv2.setBackgroundResource(R.drawable.calendar_date_focused);
                        tv2.setTextColor(Color.WHITE);

                        startId[0] = finalI;
                        startHangIndex[0] = hangIndex;
                    }
                });
            }
            linearLayout.addView(riview);
            linearLayouts.addView(linearLayout);
            riview.setLayoutParams(new LinearLayout.LayoutParams(mWidth / 10, mWidth / 10));
        }
        mLL.addView(linearLayouts);
        if(!isend){
            addri(++index);
        }
    }

    /**
     * 上一个月
     */
    public void lastMonth(){
        addCalendar();
    }

    /**
     * 下一个月
     */
    public void nextMonth(){
        addCalendar();
    }
    private void initView(){
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_pop_calendar, null);
        mLL = (LinearLayout) mView.findViewById(R.id.view_pop_calendar_ll);
        llDesmiss = (LinearLayout)mView.findViewById(R.id.view_pop_calendar_desmiss);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(mView);

        llDesmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
