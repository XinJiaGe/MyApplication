package com.lixinjia.myapplication.adapter.lrecyclerview;

import android.app.Activity;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.model.LRecyclerViewModel;
import com.lixinjia.myapplication.utils.SuperViewHolder;

import java.util.List;

/**
 * 作者：李忻佳
 * 日期：2018/6/21
 * 说明：
 */

public class MyLRecyclerviewAdapter extends BaseRecyclerAdapter<LRecyclerViewModel> {
    public MyLRecyclerviewAdapter(List<LRecyclerViewModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lrecyclerview;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        LRecyclerViewModel item = mDataList.get(position);

        TextView textView = holder.getView(R.id.item_lrecyclerview_text);

        textView.setText(item.getName());
    }
}
