package com.adaptation.lixinjia.myapplication.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.adaptation.lixinjia.myapplication.R;
import com.adaptation.lixinjia.myapplication.adapter.XmlExtractAdapter;
import com.adaptation.lixinjia.myapplication.model.BBBModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2018/6/5/005.
 * 说明：提取xml内容
 */

public class XmlExtractActivity extends BaseWheelActivity {
    private ListView listview;

    @Override
    public int bindLayout() {
        return R.layout.act_xmlextract;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("xml提取");
        listview = $(R.id.act_xmlextract_listview);
    }

    @Override
    public void addListener() {
        List<BBBModel> list = initProvinceDatas();
        List<BBBModel> lists = new ArrayList<>();
        lists.addAll(list);
        if(list.size()>0) {
            list.remove(0);
            XmlExtractAdapter adapter = new XmlExtractAdapter(list, mActivity,lists);
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void doBusiness() {

    }
}
