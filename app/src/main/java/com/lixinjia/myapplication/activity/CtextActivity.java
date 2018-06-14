package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.utils.CUtile;

public class CtextActivity extends BaseActivity{
    private TextView ActCText2;
    private TextView ActCText1;

    @Override
    public int bindLayout() {
        return R.layout.activity_ctext;
    }

    @Override
    public void initView(View view) {
        ActCText2 = $(R.id.ActCText2);
        ActCText1 = $(R.id.ActCText1);
    }

    @Override
    public void doView() {
        mTitle.setCenterText("C/C++");
    }

    @Override
    public void addListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness() {
//        CUtile.getCJNI();
        ActCText1.setText(CUtile.getJavaJNI());
    }

    public static void javaLog() {
//        ActCText2.setText("C 已经调用 Java 方法");
//        ActCText1.setText(CUtile.getJavaJNI());
    }
}
