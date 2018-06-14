package com.lixinjia.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.utils.ViewUtil;

/**
 * Created by Administrator on 2018/1/22/022.
 */

public class MainSelectView extends LinearLayout {
    private int[] mImage;
    private String[] mText;

    public MainSelectView(Context context) {
        super(context);
        init(context);
    }

    public MainSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(LinearLayout.HORIZONTAL);

        setView(context);
    }
    private void setView(Context context){
        if(mText!=null){
            for (int i = 0; i < mText.length; i++) {
                View view = ViewUtil.getResId(context, R.layout.view_main_select);
                ImageView imageView = view.findViewById(R.id.view_main_select_image);
            }
        }
    }

    public void setImage(int[] image){
        this.mImage = image;
    }
    public void setText(String[] text){
        this.mText = text;
    }
}
