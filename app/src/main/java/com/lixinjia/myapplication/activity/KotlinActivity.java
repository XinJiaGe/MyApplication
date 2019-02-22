package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Button;

import com.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 日期：2019/2/22
 * 说明：
 */

public class KotlinActivity extends BaseActivity {
    private Button jiben;
    private Button DSL;
    @Override
    public int bindLayout() {
        return R.layout.act_kotlin;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("kotlin");
        jiben = $(R.id.act_kotlin_jiben);
        DSL = $(R.id.act_kotlin_DSL);

        jiben.setOnClickListener(this);
        DSL.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == jiben){
            startActivity(KotlinTvActivity.class);
        }
        if(v == DSL){
            startActivity(KotlinDSLActivity.class);
        }
    }
}
