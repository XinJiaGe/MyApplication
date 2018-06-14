package com.lixinjia.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.popupwindow.PWindowBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：李忻佳
 * 时间：2017/6/23
 * 说明：更多view ...
 */

public class MoreView extends ImageView{
    private Context mContext;
    private PWindowBase pWindowBase;
    private List<String> mList;
    private View mShowAsDropView;
    private onItemClickListener mClickListener;

    public MoreView(Context context) {
        super(context);
        init(context);
    }

    public MoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void setItemList(List<String> list){
        mList = list;
    }
    public void setShowAsDropView(View view){
        this.mShowAsDropView = view;
    }
    public interface onItemClickListener{
        public void onClickListener(View view, int position, long id);
    }
    public void setmItemClickListener(onItemClickListener clickListener){
        this.mClickListener = clickListener;
    }
    private void init(Context context){
        this.mContext = context;
        setBackgroundResource(R.mipmap.icon_more);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initPop();
            }
        });
    }
    private void initPop(){
        if(pWindowBase == null&&mList != null){
            ListView listView = new ListView(mContext);
            listView.setLayoutParams(new ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
            listView.setBackgroundColor(Color.WHITE);
            List<HashMap<String ,String>> list = new ArrayList<>();
            for (String str : mList) {
                HashMap<String ,String> map = new HashMap<>();
                map.put("name",str);
                list.add(map);
            }
            SimpleAdapter adapter = new SimpleAdapter(mContext,list,R.layout.item_pop_more,new String[]{"name"},new int[]{R.id.item_pop_more_text});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(mClickListener!=null){
                        mClickListener.onClickListener(view,position,id);
                    }
                }
            });
            pWindowBase = new PWindowBase();
            pWindowBase.setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
            pWindowBase.setContentView(listView);
        }
        if(mShowAsDropView == null){
            pWindowBase.showAtLocation(this,Gravity.RIGHT,0,0);
        }else{
            pWindowBase.showAsDropDown(mShowAsDropView,Gravity.RIGHT,0,0);
        }
    }
}
