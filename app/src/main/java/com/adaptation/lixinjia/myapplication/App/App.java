package com.adaptation.lixinjia.myapplication.App;

import android.app.Application;

import com.sunday.eventbus.SDEventManager;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：Application
 */

public class App extends Application{
    public static boolean isDebug = true;
    private static App mApp;

    /**
     * 初始化Application
     * @return
     */
    public static App getApplication() {
        if(mApp == null){
            mApp = new App();
        }
        return mApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        isDebug = true;
    }

    @Override
    public void onTerminate() {
        SDEventManager.unregister(this);
        super.onTerminate();
    }
}
