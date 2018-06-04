package com.adaptation.lixinjia.myapplication.activity;

import android.view.View;
import android.widget.Button;

import com.adaptation.lixinjia.myapplication.R;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * 作者：李忻佳
 * 日期：2017/12/15/015.
 * 说明：消息红点
 */

public class MessageRedPointActivity extends BaseActivity {
    private BGABadgeTextView point1;
    private Button bt;

    @Override
    public int bindLayout() {
        return R.layout.act_message_red_point;
    }

    @Override
    public void initView(View view) {
        point1 = $(R.id.act_message_red_point1);
        bt = $(R.id.act_message_red_point_bt);

        mTitle.setCenterText("消息红点");
    }

    @Override
    public void addListener() {
        bt.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        if(v == bt){
            if(point1.isShowBadge())
                point1.hiddenBadge();
            else
                point1.showTextBadge("100");
        }
    }

    @Override
    public void doBusiness() {
        point1.showTextBadge("100");
    }
}
