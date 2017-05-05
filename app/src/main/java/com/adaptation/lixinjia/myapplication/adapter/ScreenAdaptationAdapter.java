package com.adaptation.lixinjia.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：屏幕适配Adapter
 */

public class ScreenAdaptationAdapter extends BaseAdapter<String> {
    public ScreenAdaptationAdapter(List<String> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_screen_adaptation;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, String model) {
        TextView jine = (TextView) convertView.findViewById(R.id.item_screen_adaptation_jine);
        jine.setText(model);
    }
}
