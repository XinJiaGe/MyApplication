package com.adaptation.lixinjia.myapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.adaptation.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 日期：2017/12/18/018.
 * 说明：讯飞服务
 */

public class XunFeiActivity extends BaseActivity {
    private Button synthesis;
    private Button dictation;

    @Override
    public int bindLayout() {
        return R.layout.act_xunfei;
    }

    @Override
    public void initView(View view) {
        synthesis = $(R.id.act_xunfei_synthesis);
        dictation = $(R.id.act_xunfei_dictation);

        mTitle.setCenterText("讯飞服务");
    }

    @Override
    public void addListener() {
        synthesis.setOnClickListener(this);
        dictation.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if (v == synthesis) {
            startActivity(XunFeiSynthesisActivity.class);
        }
        if (v == dictation) {
            startActivity(XunFeiSynthesisActivity.class);
        }
    }

    @Override
    public void doBusiness() {
        requestPermissions();
    }

    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.LOCATION_HARDWARE, Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_CONTACTS}, 0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
