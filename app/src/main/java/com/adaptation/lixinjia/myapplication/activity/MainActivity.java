package com.adaptation.lixinjia.myapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.adapter.MainAdapter;
import com.adaptation.lixinjia.myapplication.view.AdaptiveHorizontalLayoutView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：MainActivity
 */

public class MainActivity extends BaseActivity {
    private AdaptiveHorizontalLayoutView horizontal;

    @Override
    public int bindLayout() {
        return R.layout.act_main;
    }

    @Override
    public void initView(View view) {
        horizontal = $(R.id.act_main_horizontal);
    }

    @Override
    public void doView() {
        mTitle.setCenterText("我的");
    }
    @Override
    public void addListener() {
        horizontal.setOnMyClickListener(new AdaptiveHorizontalLayoutView.onMyClickListener() {
            @Override
            public void onClick(int index, View view) {
                switch (index) {
                    case 0:
                        startActivity(ScreenAdaptationActivity.class);
                        break;
                    case 1:
                        startActivity(MeiZuFingerprintIdentificationActivity.class);
                        break;
                    case 2:
                        startActivity(NativeFingerprintIdentificationActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }



    @Override
    public void doBusiness() {
        List<String> list = new ArrayList<>();
        list.add("适配屏幕");
        list.add("魅族指纹识别");
        list.add("原生指纹识别");
        MainAdapter adapter = new MainAdapter(list,mActivity);
        horizontal.setALine(false);
        horizontal.setAdapter(adapter);
        horizontal.startCanvase();
    }
}
