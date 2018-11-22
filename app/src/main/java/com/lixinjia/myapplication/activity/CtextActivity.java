package com.lixinjia.myapplication.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.utils.CUtile;
import com.lixinjia.myapplication.utils.SDToast;

public class CtextActivity extends BaseActivity{
    private static TextView ActCText2;
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
        ActCText1.setText(CUtile.getJavaJNI());
//        CUtile.getCJNI();
    }

    public static void javaLog() {
//        SDToast.showToast("C 调用Java 成功");
    }
}
