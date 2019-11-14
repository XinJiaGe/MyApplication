package com.lixinjia.myapplication.activity;

import android.view.View;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.invocationhandler.WxServiceManager;

/**
 * 作者：忻佳
 * 日期：2019-10-25
 * 描述：动态代理
 */
public class InvocationHandlerActivity extends BaseActivity {

    @Override
    public int bindLayout() {
        return R.layout.act_invocationhandler;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("动态代理");
    }

    @Override
    public void doBusiness() {
    }

    public void bt(View view){
        WxServiceManager.getInstance().Device.Auth("111","222","333");
    }
}
