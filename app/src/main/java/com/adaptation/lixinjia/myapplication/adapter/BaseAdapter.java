package com.adaptation.lixinjia.myapplication.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：BaseAdapter<T>
 */

public abstract class BaseAdapter<T> extends SDAdapter<T>{
    public BaseAdapter(List<T> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    protected View onGetView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layoutId = getLayoutId(position, convertView, parent);
            convertView = mInflater.inflate(layoutId, parent, false);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        bindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void bindData(int position, View convertView, ViewGroup parent, T model);
}
