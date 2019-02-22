package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 日期：2019/2/22
 * 说明：指纹识别
 */

public class FingerprintActivity extends BaseActivity {
    private Button yuansheng;
    private Button meizu;
    @Override
    public int bindLayout() {
        return R.layout.act_fingerprint;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("指纹识别");

        yuansheng = $(R.id.act_fingerprint_yuansheng);
        meizu = $(R.id.act_fingerprint_meizu);

        yuansheng.setOnClickListener(this);
        meizu.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == yuansheng){
            startActivity(NativeFingerprintIdentificationActivity.class);
        }
        if(v == meizu){
            startActivity(MeiZuFingerprintIdentificationActivity.class);
        }
    }
}
