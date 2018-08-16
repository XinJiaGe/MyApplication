package com.lixinjia.myapplication.activity.lrecyclerview;

import android.view.View;
import android.widget.Button;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.activity.BaseActivity;

/**
 * 作者：李忻佳
 * 日期：2018/6/21
 * 说明：LRecyclerview
 */

public class MyLRecyclerviewActivity extends BaseActivity {
    private Button button;
    private Button textrecycler;

    @Override
    public int bindLayout() {
        return R.layout.act_mylrecyclerview;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("LRecyclerview");

        button = $(R.id.recycler_recycler);
        textrecycler = $(R.id.recycler_textrecycler);
    }

    @Override
    public void addListener() {
        button.setOnClickListener(this);
        textrecycler.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == button){
            startActivity(LRecyclerviewActivity.class);
        }
        if(v == textrecycler){
            startActivity(TextLRecyclerviewActivity.class);
        }
    }

    @Override
    public void doBusiness() {

    }
}
