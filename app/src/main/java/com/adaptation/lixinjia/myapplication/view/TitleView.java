package com.adaptation.lixinjia.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：TitleView
 */

public class TitleView extends LinearLayout{
    private LinearLayout back;
    private LinearLayout center;
    private LinearLayout right;
    private Context mContext;
    private int centerTextSize = 17;
    private int centerTextColor = Color.BLACK;
    private OnBackClickListener mOnBackClickListener;

    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 初始
     * @param context
     */
    private void initView(Context context){
        this.mContext = context;
        View titleView = LayoutInflater.from(context).inflate(R.layout.view_title, null);
        back = (LinearLayout) titleView.findViewById(R.id.view_title_back);
        center = (LinearLayout) titleView.findViewById(R.id.view_title_center);
        right = (LinearLayout) titleView.findViewById(R.id.view_title_right);
        addView(titleView);
    }

    /**
     * 设置title 中间的标题 text
     * @param text
     */
    public void setCenterText(String text){
        center.removeAllViews();
        TextView titleView = new TextView(mContext);
        titleView.setText(text);
        titleView.setTextColor(centerTextColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,centerTextSize);
        center.addView(titleView);
    }

    /**
     * 设置title 中间的Veiw
     * @param view
     */
    private void setCenterView(View view){
        center.removeAllViews();
        center.addView(view);
    }

    /**
     * 设置标题点击回调
     * @param listener
     */
    public void setCenterOnClickListener(final OnCenterClickListener listener){
        center.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCenterListener();
            }
        });
    }

    /**
     * 设置返回键点击回调
     * @param listener
     */
    public void setBackOnClickListener(OnBackClickListener listener){
        this.mOnBackClickListener = listener;
    }

    public OnBackClickListener getmOnBackClickListener() {
        return mOnBackClickListener;
    }

    public interface OnBackClickListener{
        public void onBackListener();
    }
    public interface OnCenterClickListener{
        public void onCenterListener();
    }
    public LinearLayout getBack() {
        return back;
    }

    public TitleView setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
        return this;
    }

    public TitleView setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        return this;
    }
}
