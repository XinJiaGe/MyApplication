package com.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Button;

import com.lixinjia.myapplication.R;

/**
 * 作者：李忻佳
 * 日期：2019/2/22
 * 说明：锁
 */

public class LockActivity extends BaseActivity {
    private Button mac;
    private Button search;

    @Override
    public int bindLayout() {
        return R.layout.act_lock;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("锁");

        mac = $(R.id.act_lock_mac);
        search = $(R.id.act_lock_search);

        mac.setOnClickListener(this);
        search.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        if(v == mac){
            startActivity(LockCabinetActivity.class);
        }
        if(v == search){
            startActivity(LockCabinetBroadcastActivity.class);
        }
    }
}
