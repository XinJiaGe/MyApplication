package com.adaptation.lixinjia.myapplication.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 作者：李忻佳
 * 时间：2017/6/21
 * 说明：
 */

public class ViewUtil {
    public static View getResId(Activity activity,int resId){
        View contentView = LayoutInflater.from(activity).inflate(resId, null);
        return contentView;
    }
}
