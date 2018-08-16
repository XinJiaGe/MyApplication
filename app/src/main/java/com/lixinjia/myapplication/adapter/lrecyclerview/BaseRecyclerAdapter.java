package com.lixinjia.myapplication.adapter.lrecyclerview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixinjia.myapplication.utils.SuperViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装adapter（注意：仅供参考，根据需要选择使用demo中提供的封装adapter）
 * @param <T>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {
    protected Activity mActivity;
    private LayoutInflater mInflater;

    protected List<T> mDataList = new ArrayList<>();

    public BaseRecyclerAdapter(List<T> listModel,Activity activity) {
        mActivity = activity;
        mDataList = listModel;
        mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getLayoutId(), parent, false);
        return new SuperViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        onBindItemHolder(holder, position);
    }

    //局部刷新关键：带payload的这个onBindViewHolder方法必须实现
    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            onBindItemHolder(holder, position, payloads);
        }

    }

    public abstract int getLayoutId();

    public abstract void onBindItemHolder(SuperViewHolder holder, int position );

    public void onBindItemHolder(SuperViewHolder holder, int position, List<Object> payloads){

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if(position != (getDataList().size())){ // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position,this.mDataList.size()-position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}
