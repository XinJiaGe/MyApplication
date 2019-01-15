package com.lixinjia.myapplication.utils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 作者：李忻佳
 * 日期：2018/12/8
 * 说明：
 */

public class MyACRCloud implements IXposedHookLoadPackage{
    String className = "com.lixinjia.myapplication.activity.ACRCloudActivity";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        Class clazz = loadPackageParam.classLoader.loadClass(className);
        /**
         * 第一个参数是目标类
         * 第二个参数是目标方法
         * 第三个是回调，可用这个替换目标方法，还有个带两个方法的回调，before,after那个
         */
        XposedHelpers.findAndHookMethod(clazz, "MyImei", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return "Hook Imei";
            }
        });
    }
}
