package com.lixinjia.myapplication.adapter;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixinjia.myapplication.R;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：MainAdapter
 */

public class MainAdapter extends BaseAdapter<String> {
    public MainAdapter(List<String> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_main;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, String model) {
        TextView text = (TextView) convertView.findViewById(R.id.item_main_text);
        text.setText(model);
    }
}
