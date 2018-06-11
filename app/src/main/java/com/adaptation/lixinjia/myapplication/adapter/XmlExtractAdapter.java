package com.adaptation.lixinjia.myapplication.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.model.BBBModel;
import com.adaptation.lixinjia.myapplication.utils.ViewHolder;
import com.adaptation.lixinjia.myapplication.view.ListViewLinearLayoutView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2018/6/5/005.
 * 说明：
 */

public class XmlExtractAdapter extends BaseAdapter<BBBModel> {

    private List<BBBModel.listData> mlists;

    public XmlExtractAdapter(List<BBBModel> listModel, Activity activity, List<BBBModel> lists) {
        super(listModel, activity);
        mlists = lists.get(0).getList();
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_xmlextract;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final BBBModel model) {
        TextView name = ViewHolder.get(R.id.item_xmlextract_name, convertView);
        final ImageView iamge = ViewHolder.get(R.id.item_xmlextract_iamge, convertView);
        final ListViewLinearLayoutView dataView = ViewHolder.get(R.id.item_xmlextract_dataListview, convertView);
        TextView zong = ViewHolder.get(R.id.item_xmlextract_zong, convertView);

        name.setText(model.getName());
        zong.setText("(" + model.getZong() + ")");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iamge.getRotation() == 270) {
                    iamge.setRotation(90);
                    dataView.setVisibility(View.VISIBLE);
                    XmlExtractItemAdapter adapter = new XmlExtractItemAdapter(model.getList(), mActivity, mlists);
                    dataView.setAdapter(adapter);
                } else {
                    iamge.setRotation(270);
                    dataView.setVisibility(View.GONE);
                    List<BBBModel.listData> lll = new ArrayList<>();
                    XmlExtractItemAdapter adapter = new XmlExtractItemAdapter(lll, mActivity, mlists);
                    dataView.setAdapter(adapter);
                }
            }
        });
    }
}
