package com.adaptation.lixinjia.myapplication.utils;

import android.content.Context;
import android.content.Intent;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：
 */

public class FingerprintUtil {
    private static final String ACTION_SETTING = "android.settings.SETTINGS";

    public static void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent(ACTION_SETTING);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
