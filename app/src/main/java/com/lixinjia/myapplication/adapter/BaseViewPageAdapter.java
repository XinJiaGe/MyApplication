package com.lixinjia.myapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/6/21
 * 说明：BaseViewPageAdapter
 */

public class BaseViewPageAdapter extends PagerAdapter {
    private final List<View> mListView;

    public BaseViewPageAdapter(List<View> listView){
        this.mListView = listView;
    }

    @Override
    public int getCount() {
        return mListView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListView.get(position));
        return mListView.get(position);
    }
}
