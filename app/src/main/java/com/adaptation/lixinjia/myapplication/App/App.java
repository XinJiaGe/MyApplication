package com.adaptation.lixinjia.myapplication.app;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.sunday.eventbus.SDEventManager;
import com.xinjiage.stepdetector.StepDetector;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：Application
 */

public class App extends Application{
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

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
        /* 初始化计步模块 */
        StepDetector.getInstance().init(this).startStepService();
        /* 初始化LogUtil工具类 */
        initLogUtile();
    }

    private void initLogUtile(){
        Utils.init(this);
        LogUtils.Builder lBuilder = new LogUtils.Builder();
        lBuilder.setLogSwitch(true)// 设置log总开关，默认开
            .setGlobalTag("")// 设置log全局标签，默认为空
            // 当全局标签不为空时，我们输出的log全部为该tag，
            // 为空时，如果传入的tag为空那就显示类名，否则显示tag
            .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
            .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
            .setConsoleFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }

    @Override
    public void onTerminate() {
        SDEventManager.unregister(this);
        super.onTerminate();
    }
}
