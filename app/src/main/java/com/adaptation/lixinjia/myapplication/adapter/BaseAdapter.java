package com.adaptation.lixinjia.myapplication.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/4
 * 说明：BaseAdapter<T>
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Activity mActivity;
    private List<T> mListModel;

    public BaseAdapter(List<T> listModel, Activity activity){
        this.mListModel = listModel;
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mListModel.size();
    }

    @Override
    public T getItem(int position) {
        if (isPositionLegal(position)) {
            return mListModel.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int layoutId = getLayoutId(position, convertView, parent);
            convertView = mActivity.getLayoutInflater().inflate(layoutId, null);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        bindData(position, convertView, parent, getItem(position));
        return convertView;
    }

    public boolean isPositionLegal(int position) {
        if (mListModel != null && !mListModel.isEmpty() && position >= 0 && position < mListModel.size()) {
            return true;
        } else {
            return false;
        }
    }

    public abstract int getLayoutId(int position, View convertView, ViewGroup parent);

    public abstract void bindData(int position, View convertView, ViewGroup parent, T model);
}
