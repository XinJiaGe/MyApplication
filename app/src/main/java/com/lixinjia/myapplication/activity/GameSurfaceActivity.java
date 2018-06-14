package com.lixinjia.myapplication.activity;

import android.os.Bundle;
import android.view.View;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.surface.GameSurfaceView;

/**
 * 作者：李忻佳
 * 时间：2017/5/5
 * 说明：GameSurfaceActivity
 */

public class GameSurfaceActivity extends BaseActivity {
    private GameSurfaceView surface;

    @Override
    public void initParms(Bundle parms) {
        super.initParms(parms);
        setNotTitle(true);
        setNotStatusBar(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.act_game_surface;
    }

    @Override
    public void initView(View view) {
        surface = $(R.id.act_game_surface);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness() {
    }
}
