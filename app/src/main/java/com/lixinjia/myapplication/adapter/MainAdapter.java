package com.lixinjia.myapplication.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.model.MainEntity;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：MainAdapter
 */

public class MainAdapter extends BaseAdapter<MainEntity> {
    public MainAdapter(List<MainEntity> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_main;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final MainEntity model) {
        TextView text = convertView.findViewById(R.id.item_main_text);
        text.setText(model.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, model.getClz());
                mActivity.startActivity(intent);
            }
        });
    }
}
