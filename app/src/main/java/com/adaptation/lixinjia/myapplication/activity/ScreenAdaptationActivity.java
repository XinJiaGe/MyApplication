package com.adaptation.lixinjia.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.adaptation.lixinjia.myapplication.adapter.ScreenAdaptationAdapter;
import com.adaptation.lixinjia.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/5/2
 * 说明：适配屏幕Activity
 */
public class ScreenAdaptationActivity extends BaseActivity {

    private ImageView img;
    private ListView listview;

    @Override
    public void widgetClick(View v) {
        if(v == img){
            showToast("点击了图片");
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.act_screen_adaptation;
    }

    @Override
    public void initView(View view) {
        img = $(R.id.act_screen_adaptation_img);
        listview = $(R.id.act_screen_adaptation_listview);
    }

    @Override
    public void doView() {
        mTitle.setCenterText("适配屏幕");
    }

    @Override
    public void addListener() {
        img.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("点击了第"+position+"行数据");
            }
        });
    }

    @Override
    public void doBusiness() {
        List<String> list = new ArrayList<>();
        list.add("0.215");
        list.add("25.215");
        list.add("666.215");
        list.add("4454.215");
        list.add("625.215");
        list.add("644.215");
        ScreenAdaptationAdapter adapter = new ScreenAdaptationAdapter(list,this);
        listview.setAdapter(adapter);
    }
}
