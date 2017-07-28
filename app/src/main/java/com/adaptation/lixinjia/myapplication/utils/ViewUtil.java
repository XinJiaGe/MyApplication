package com.adaptation.lixinjia.myapplication.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 作者：李忻佳
 * 时间：2017/6/21
 * 说明：
 */

public class ViewUtil {
    public static View getResId(Context mContext, int resId){
        View contentView = LayoutInflater.from(mContext).inflate(resId, null);
        return contentView;
    }
}
