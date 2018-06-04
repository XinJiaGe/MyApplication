package com.adaptation.lixinjia.myapplication.app;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.SpeechUtility;
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
        // android 7.0系统解决拍照的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        isDebug = true;
        /* 初始化计步模块 */
        StepDetector.getInstance().init(this).startStepService();
        /* 初始化LogUtil工具类 */
        initLogUtile();
        /*讯飞初始化*/
        xunfei();
    }

    /**
     * 初始化LogUtil工具类
     */
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

    /**
     * 讯飞初始化
     */
    private void xunfei(){
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(this, "appid=5a37988a");

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
    }
}
