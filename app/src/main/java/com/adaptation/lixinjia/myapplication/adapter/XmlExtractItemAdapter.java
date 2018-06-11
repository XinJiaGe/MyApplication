package com.adaptation.lixinjia.myapplication.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.model.BBBModel;
import com.adaptation.lixinjia.myapplication.utils.ViewHolder;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2018/6/5/005.
 * 说明：
 */

public class XmlExtractItemAdapter extends BaseAdapter<BBBModel.listData> {
    private List<BBBModel.listData> mLlls;

    public XmlExtractItemAdapter(List<BBBModel.listData> listModel, Activity activity, List<BBBModel.listData> list) {
        super(listModel, activity);
        mLlls = list;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_xmlextractitem;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, BBBModel.listData model) {
        TextView name = ViewHolder.get(R.id.item_xmlitem_name, convertView);
        TextView time = ViewHolder.get(R.id.item_xmlitem_time, convertView);
        TextView lastname = ViewHolder.get(R.id.item_xmlitem_lastname, convertView);
        TextView jiaban = ViewHolder.get(R.id.item_xmlitem_jiaban, convertView);

        time.setText(mLlls.get(position).getDatas());
        if (model.getDatas().trim().length() > 0) {
            name.setText(model.getDatas());
            lastname.setVisibility(View.VISIBLE);
            jiaban.setVisibility(View.VISIBLE);
            lastname.setText("最晚时间：" + model.getLastData());
            jiaban.setText("加班时间：" + model.getSurplus());
        } else {
            name.setText("没上班");
            lastname.setVisibility(View.GONE);
            jiaban.setVisibility(View.GONE);
        }
    }
}
